
public enum Precepts 
{
	NONE(0), BREEZY(1), SMELLY(2), GLITTERY(4);
	private final int value;
    private Precepts(int value) 
    {
        this.value = value;
    }

    public int getValue() 
    {
        return value;
    }
}
