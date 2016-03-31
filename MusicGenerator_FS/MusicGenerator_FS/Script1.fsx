#r @"..\packages\FsCheck.2.0.7\lib\Net45\FsCheck.dll"
#load @"SMPCFunctions.fs"
open FsCheck

module GenScript =
    type Interval = unison = 0 | minorSecond = 1 | second = 2 | minorThird = 3 | third = 4 | fourth = 5 | augmentedFourth = 6 | 
                        diminishedFifth = 6 | fifth = 7 | minorSixth = 8 | sixth = 9 | minorSeventh = 10 | seventh = 11 | octave = 12

    let randomIntervalGenerator = Gen.frequency [ 
                                    (1, gen { return Interval.unison}); (2, gen{ return Interval.minorSecond}); 
                                    (1, gen { return Interval.second}); (1, gen { return Interval.minorThird}); 
                                    (1, gen { return Interval.third}); (1, gen { return Interval.fourth}); 
                                    (1, gen { return Interval.augmentedFourth}); (1, gen { return Interval.fifth}); 
                                    (1, gen { return Interval.minorSixth}); (1, gen { return Interval.sixth}); 
                                    (1, gen { return Interval.minorSeventh}); (1, gen { return Interval.seventh}); 
                                    (1, gen { return Interval.octave}); ]

    let randomInterval = randomIntervalGenerator.Sample(10,5)


    let intervals : Interval list = [ 0 .. 12 ] |> List.map (fun i -> enum i)

    let randomListOfIntervalsGenerator = Gen.listOfLength 5 randomIntervalGenerator
    let randomListOfIntervals = randomListOfIntervalsGenerator.Sample(10,5)

module S =
    let randomFloats = (Arb.generate<float> |> Gen.suchThat ((<) 0.)).Sample(10,5)
    let randomIntervals = (Arb.generate<GenScript.Interval list>).Sample(10,5)


module WithPath =
    open GenScript

    let pathDepMemoizeWithTfm f tfm =
        let path = System.Collections.Generic.List()
        fun x ->
            let result = f (tfm x) (List.ofSeq path)
            if result then path.Insert(0, tfm x) ; true else false

    let pathDepMemoize f =
        pathDepMemoizeWithTfm f id

    // strictly increasing
    let pathfn testval path =
        match path with 
        | [] -> true
        | Interval.octave :: tail -> true
        | head :: tail -> int testval > int head

    let randomIntervalPath = (randomIntervalGenerator |> Gen.suchThat (pathDepMemoize pathfn)).Sample(10,5)

    type Goal = { average : float; dev : float }
    let fn goal testval (path:Interval list) =
        let newAv = List.averageBy (int >> float) (testval::path)
        printfn "Goal: %A %f %A" goal newAv (testval::path)
        let result = abs (newAv - goal.average) < goal.dev
        printfn "%A" result
        result

    let randomIntervalPathWithGoal = (randomIntervalGenerator |> Gen.suchThat (pathDepMemoize (fn {average=5.0; dev=2.5}))).Sample(10,50)

    type Note = A = 0 | As = 1 | Bb = 1 | B = 2 | Cb = 2 | C = 3 | Cs = 4 | Db = 4 | D = 5 | Ds = 6 | Eb = 6 | E = 7 | Fb = 7 | Es = 8 | F = 8 | Fs = 9 | Gb = 9 | G = 10 | Gs = 11 | Ab = 11
    type Pitch = {n:Note;o:int}
    let addNote (n:Note) (i:Interval) : Note = enum ((int n + (int i)) % 12)
    let add (p:Pitch) (i:Interval) : Pitch = 
        { n = addNote p.n i ; o = p.o + (((if int p.n >= 3 then (int p.n) - 3 else (int p.n) + 9) + (int i)) / 12)}
    let subtractNote (n:Note) (i:Interval) : Note = enum ((((int n - (int i)) % 12) + 12) % 12)
    let subtract (p:Pitch) (i:Interval) : Pitch =
        { n = subtractNote p.n i ; o = p.o + (((if int p.n >= 3 then (int p.n) - 3 else (int p.n) + 9) - (int i) - 12) / 12)}

    let sequence = randomIntervalPathWithGoal |> List.rev |> List.fold (fun state elem -> (add (List.head state) elem) :: state) [{n=Note.A;o=4}] |> List.rev

module TreeScript =
    open GenScript

    type Score = Interval of Interval | Rest of (Interval * Score)

    let rec catamorphism fInterval fRest score =
        let recurse = catamorphism fInterval fRest
        match score with
        | Interval interval -> fInterval interval
        | Rest (interval, score) -> fRest interval (recurse score)

    let value = catamorphism int (int >> (+))

    let score1 = Interval Interval.third
    let count1 = value score1

    let score2 = Rest (Interval.third, Interval Interval.fifth)
    let count2 = value score2

    let score3 = Rest (Interval.sixth, score2)
    let count3 = value score3

module Notes =
    open GenScript
    open WithPath
    open SMPCFunctions.Dissonance

    let calcFreqFromNote (p:Pitch) = 440.0 * (2.0 ** (float ((int p.n) + 12 * (p.o - 4)) / 12.0))

    let fn () =
        let random = new System.Random()
        fun r seed goal testval (path:Interval list) ->
            // the frequencies corresponding to the interval path, in reverse order
            let freqs = ((calcFreqFromNote seed) :: 
                            (List.rev (testval::path) |> 
                                List.mapFold (fun state interval -> 
                                            let newPitch = add state interval 
                                            newPitch |> calcFreqFromNote, newPitch) seed |> fst)) |> List.rev
            printfn "Freqs: %A" freqs
            let calculatedDissonance = 
                freqs |> (
                    List.pairwise >> List.mapi (fun pos elem -> pos,elem) >>
                    List.fold (fun state (pos, (freq1, freq2)) -> state + System.Math.Exp (-1. * (float pos) * r) * dissonance freq1 freq2) 0.0)
            printfn "Goal: %A %f %A" goal calculatedDissonance (testval::path)
            let result = abs (calculatedDissonance - goal.average) < goal.dev
            printfn "%A" result
            result
            
    let startingNote = {n=Note.A;o=4}
    let decay = 1.0
    let goal = {average=2.5; dev=2.5}
    let randomIntervalPathWithGoal = (randomIntervalGenerator |> Gen.suchThat (pathDepMemoize (fn() decay startingNote goal))).Sample(10,5)

    let sequence = randomIntervalPathWithGoal |> List.rev |> List.fold (fun state elem -> (add (List.head state) elem) :: state) [startingNote] |> List.rev

    // Introduce a distance function that considers both the inter-interval dissonance and the dissoance of the whole path
    let fn2 ()=
        let random = new System.Random()
        fun r seed docGoal intGoal testval (path:Interval list) ->
        // the frequencies corresponding to the interval path, in reverse order
            let freqs = ((calcFreqFromNote seed) :: 
                            (List.rev (testval::path) |> 
                                List.mapFold (fun state interval -> 
                                            let newPitch = add state interval 
                                            newPitch |> calcFreqFromNote, newPitch) seed |> fst)) |> List.rev
            printfn "Freqs: %A" freqs
            let calculatedDissonanceDoc = 
                freqs |> (
                    List.pairwise >> List.mapi (fun pos elem -> pos,elem) >>
                    List.fold (fun state (pos, (freq1, freq2)) -> state + System.Math.Exp (-1. * (float pos) * r) * dissonance freq1 freq2) 0.0)
            printfn "DocGoal: %A %f %A" docGoal calculatedDissonanceDoc (testval::path)
            let docResult = abs (calculatedDissonanceDoc - docGoal.average) < docGoal.dev
            printfn "DocResult: %A" docResult

            let intResult = 
                match freqs with
                | f2::f1::tail ->
                    let calculatedDissonanceInterval = dissonance f1 f2
                    abs (calculatedDissonanceInterval - intGoal.average) < intGoal.dev
                | _ -> true
            printfn "IntResult: %A" intResult

            docResult && intResult

    let randomIntervalPathWithDocAndIntGoals = (randomIntervalGenerator |> Gen.suchThatOption (pathDepMemoize (fn2() 1.0 startingNote {average=1.5; dev=1.0} {average=2.5; dev=2.5}))).Sample(10,50)

    let sequence2 = randomIntervalPathWithDocAndIntGoals |> List.rev |> List.choose id |> List.fold (fun state elem -> (add (List.head state) elem) :: state) [startingNote] |> List.rev

// But we want to store the frequencies and we don't want to recalculate them every time a new random interval is produced. Also we don't
// want to recalculate the frequencies when creating the final sequence (as we have already determined them).
//let newPitch = (if random.Next(2) = 0 then add else subtract) state interval 

    