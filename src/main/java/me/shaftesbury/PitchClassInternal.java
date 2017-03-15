package me.shaftesbury;

public enum PitchClassInternal {
    AFLAT,
    A,
    ASHARP,
    BFLAT,
    B,
    BSHARP,
    CFLAT,
    C,
    CSHARP,
    DFLAT,
    D,
    DSHARP,
    EFLAT,
    E,
    ESHARP,
    FFLAT,
    F,
    FSHARP,
    GFLAT,
    G,
    GSHARP;

    @Override
    public String toString()
    {
        switch(this)
        {
            case AFLAT: return "Ab";
            case A: return "A";
            case ASHARP: return "A#";
            case BFLAT: return "Bb";
            case B: return "B";
            case BSHARP: return "B#";
            case CFLAT: return "Cb";
            case C: return "C";
            case CSHARP: return "C#";
            case DFLAT: return "Db";
            case D: return "D";
            case DSHARP: return "D#";
            case EFLAT: return "Eb";
            case E: return "E";
            case ESHARP: return "E#";
            case FFLAT: return "Fb";
            case F: return "F";
            case FSHARP: return "F#";
            case GFLAT: return "Gb";
            case G: return "G";
            case GSHARP: return "G#";
            default: throw new IllegalStateException();
        }
    }

    public static PitchClassInternal fromString(final String name)
    {
        if(name.equals("Ab")) return AFLAT;
        if(name.equals("A")) return A;
        if(name.equals("A#")) return ASHARP;
        if(name.equals("Bb")) return BFLAT;
        if(name.equals("B")) return B;
        if(name.equals("B#")) return BSHARP;
        if(name.equals("Cb")) return CFLAT;
        if(name.equals("C")) return C;
        if(name.equals("C#")) return CSHARP;
        if(name.equals("Db")) return DFLAT;
        if(name.equals("D")) return D;
        if(name.equals("D#")) return DSHARP;
        if(name.equals("Eb")) return EFLAT;
        if(name.equals("E")) return E;
        if(name.equals("E#")) return ESHARP;
        if(name.equals("Fb")) return FFLAT;
        if(name.equals("F")) return F;
        if(name.equals("F#")) return FSHARP;
        if(name.equals("Gb")) return GFLAT;
        if(name.equals("G")) return G;
        if(name.equals("G#")) return GSHARP;

        throw new IllegalStateException();
    }

    public boolean isEnharmonicWith(final PitchClassInternal right)
    {
        if(this==PitchClassInternal.AFLAT && right==PitchClassInternal.GSHARP) return true;
        if(this==PitchClassInternal.ASHARP && right==PitchClassInternal.BFLAT) return true;
        if(this==PitchClassInternal.BFLAT && right==PitchClassInternal.ASHARP) return true;
        if(this==PitchClassInternal.B && right==PitchClassInternal.CFLAT) return true;
        if(this==PitchClassInternal.BSHARP && right==PitchClassInternal.C) return true;
        if(this==PitchClassInternal.CFLAT && right==PitchClassInternal.B) return true;
        if(this==PitchClassInternal.C && right==PitchClassInternal.BSHARP) return true;
        if(this==PitchClassInternal.CSHARP && right==PitchClassInternal.DFLAT) return true;
        if(this==PitchClassInternal.DFLAT && right==PitchClassInternal.CSHARP) return true;
        if(this==PitchClassInternal.DSHARP && right==PitchClassInternal.EFLAT) return true;
        if(this==PitchClassInternal.EFLAT && right==PitchClassInternal.DSHARP) return true;
        if(this==PitchClassInternal.E && right==PitchClassInternal.FFLAT) return true;
        if(this==PitchClassInternal.FFLAT && right==PitchClassInternal.E) return true;
        if(this==PitchClassInternal.ESHARP && right==PitchClassInternal.F) return true;
        if(this==PitchClassInternal.F && right==PitchClassInternal.ESHARP) return true;
        if(this==PitchClassInternal.FSHARP && right==PitchClassInternal.GFLAT) return true;
        if(this==PitchClassInternal.GFLAT && right==PitchClassInternal.FSHARP) return true;
        if(this==PitchClassInternal.GSHARP && right==PitchClassInternal.AFLAT) return true;

        return false;
    }
}
