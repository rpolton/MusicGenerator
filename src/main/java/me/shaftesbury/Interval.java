package me.shaftesbury;

public enum Interval {
    Unison, MinorSecond, Second, MinorThird, Third, Fourth, AugmentedFourth,
    Fifth, MinorSixth, Sixth, MinorSeventh, Seventh, Octave;

    public int value()
    {
        switch(this)
        {
            case Unison: return 0;
            case MinorSecond: return 1;
            case Second: return 2;
            case MinorThird: return 3;
            case Third: return 4;
            case Fourth: return 5;
            case AugmentedFourth: return 6;
            case Fifth: return 7;
            case MinorSixth: return 8;
            case Sixth: return 9;
            case MinorSeventh: return 10;
            case Seventh: return 11;
            case Octave: return 12;
            default: throw new IllegalStateException();
        }
    }
}
