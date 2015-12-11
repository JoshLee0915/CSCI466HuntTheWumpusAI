import java.util.Comparator;

public class WeightedCellComparator implements Comparator<WeightedCell> 
{
	@Override
	public int compare(WeightedCell o1, WeightedCell o2) 
	{
		if(o1.getWeight() < o2.getWeight())
			return 1;
		if(o1.getWeight() > o2.getWeight())
			return -1;
		
		return 0;
	}

}
