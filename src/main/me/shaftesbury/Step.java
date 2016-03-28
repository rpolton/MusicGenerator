package me.shaftesbury;

public enum Step {
    Half, Whole, AugSecond;

    public int value()
    {
        switch(this)
        {
            case Half: return 1;
            case Whole: return 2;
            case AugSecond: return 3;
            default: throw new IllegalStateException();
        }
    }
}
