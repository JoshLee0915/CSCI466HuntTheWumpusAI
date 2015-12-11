
public class WeightedCell 
{
	private int visited = 0, x, y;
	private double weight = 1.0;
	
	public WeightedCell(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public double getWeight()
	{
		return weight+0.1*visited;
	}
	
	public void visit()
	{
		visited++;
	}
	
	public void overrideWeight(double weight)
	{
		this.weight = weight;
	}
	
	public void adjustWeight(BeliefCell cell)
	{
		// check if the prob of gold is higher then the prob of any danger
		if(cell.getGoldProb() > cell.getPitProb() && cell.getGoldProb() > cell.getWumpProb())
			weight = -4;	// assume it has the gold
		else if(cell.getPitProb() >= 1 || cell.getWumpProb() >= 1)
			weight = Double.MAX_VALUE;		// seal it off it is a Hazzard
		else
			weight = 1 + 4*cell.getPitProb() + 4*cell.getWumpProb() - 4*cell.getGoldProb();
	}
}
