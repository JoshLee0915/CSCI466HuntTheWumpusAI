
public class BeliefCell 
{
	private int precepts;
	private double pitProb;
	private double wumpProb;
	private double goldProb;
	private boolean clear;
	
	public BeliefCell()
	{
		this.pitProb = 0;
		this.wumpProb = 0;
		this.goldProb = 0;
		this.precepts = 0;
		this.clear = false;
	}
	
	public void setClear()
	{
		clear = true;
		this.pitProb = 0;
		this.wumpProb = 0;
		this.goldProb = 0;
	}
	
	public void setPrecept(Precepts precept)
	{
		this.precepts |= precept.getValue();
	}
	
	public void removePrecept(Precepts precept)
	{
		this.precepts &= ~precept.getValue();
	}
	
	public boolean isBreezy()
	{
		return (precepts&Precepts.BREEZY.getValue()) > 0 ? true : false;
	}
	
	public boolean isSmelly()
	{
		return (precepts&Precepts.SMELLY.getValue()) > 0 ? true : false;
	}
	
	public boolean isGlittery()
	{
		return (precepts&Precepts.GLITTERY.getValue()) > 0 ? true : false;
	}
	
	public boolean isClear()
	{
		return clear;
	}
	
	public void setProb(double pitProb, double wumpProb, double goldProb)
	{
		if(!clear)
		{
			this.pitProb = pitProb;
			this.wumpProb = wumpProb;
			this.goldProb = goldProb;
		}
	}
	
	public double getPitProb()
	{
		return pitProb;
	}
	
	public double getWumpProb()
	{
		return wumpProb;
	}
	
	public double getGoldProb()
	{
		return goldProb;
	}
}
