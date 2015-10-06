module SMPCFunctions

module Dissonance =

    let intervalBetween (freq1:float) (freq2:float) =
        let fmax = max freq1 freq2
        let fmin = min freq1 freq2
        39.8631 * System.Math.Log10 (fmax / fmin) // this is the log2 to log10 conversion factor

    (*
    *
    * @param f1 is a pair containing the fundamental frequency and an int which indicates which overtone we are using from the fundamental
    * @param f2 is a pair containing the fundamental frequency and an int which indicates which overtone we are using from the fundamental
    * @return
    *)
    let dissonanceBetweenComponents (freq1,whichOvertone1) (freq2,whichOvertone2) =
        let i,j = whichOvertone1, whichOvertone2
        let b1=0.8
        let b2=1.6
        let b3=4.0
        let gamma=1.25
        let x = intervalBetween freq1 freq2
        let x2g = x ** gamma
        (pown 0.88 (i+j)) * b3 * (System.Math.Exp(-b1 * x2g) - System.Math.Exp(-b2 * x2g))

    (**
        * Calculate the total dissonance, D, for a pair of tones with fundamental frequencies f1 and f2
        * taking into account N upper partials
        * @param calculateDissonanceFrom
        * @param calculateDissonanceTo
        * @param howManyPartials
        * @return
        *)
    let dissonance' calcFrom calcTo howManyPartials =
        let dissonances = [for i in 0 .. howManyPartials do
                            for j in 0 .. howManyPartials do
                              let d1 = dissonanceBetweenComponents (calcFrom * (pown 2. i), i) (calcFrom * (pown 2. j), j)
                              let d2 = dissonanceBetweenComponents (calcTo * (pown 2. i), i) (calcTo * (pown 2. j), j)
                              let d3 = dissonanceBetweenComponents (calcFrom * (pown 2. i), i) (calcTo * (pown 2. j), j)
                              yield 0.5 * (d1+d2) + d3
                           ]
        let dissonanceFromRoot = List.sum dissonances
        dissonanceFromRoot

    let dissonance calcFrom calcTo = dissonance' calcFrom calcTo 7
