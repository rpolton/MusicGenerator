module Sound

open System.IO

let pack (d:int16[]) = 
    let stream = new MemoryStream();
    let writer = new BinaryWriter(stream, System.Text.Encoding.ASCII);
    let dataLength = Array.length d * 2

    // RIFF
    writer.Write(System.Text.Encoding.ASCII.GetBytes("RIFF"))
    writer.Write(Array.length d)
    writer.Write(System.Text.Encoding.ASCII.GetBytes("WAVE"))

    // fmt
    writer.Write(System.Text.Encoding.ASCII.GetBytes("fmt "))
    writer.Write(16)
    writer.Write(1s)        // PCM
    writer.Write(1s)        // mono
    writer.Write(44100)     // sample rate
    writer.Write(44100 * 16 / 8)     // byte rate
    writer.Write(2s)        // bytes per sample
    writer.Write(16s)       // bits per sample

    // data
    writer.Write(System.Text.Encoding.ASCII.GetBytes("data"))
    writer.Write(dataLength)
    let data:byte[] = Array.zeroCreate dataLength
    System.Buffer.BlockCopy(d, 0, data, 0, data.Length)
    writer.Write(data)
    stream

let requiredSamples = seq { 1.0..(seconds * sampleRate) }

let frequency = 440.
let samples = Seq.map 
                (fun x -> x 
                |> (*) (2. * System.Math.PI * frequency / 44100.)
                |> sin 
                |> (*) 32767 
                |> int16) 
                requiredSamples

let write (ms:MemoryStream) =
    use fs = new FileStream(Path.Combine(__SOURCE_DIRECTORY__,"test.wav"), FileMode.Create)
    ms.WriteTo(fs)

samples |> pack |> write