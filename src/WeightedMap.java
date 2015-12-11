import java.util.ArrayList;

public class WeightedMap 
{
	private BeliefMap probMap;
	private WeightedCell[][] map;
	
	public WeightedMap(int length, int width)
	{
		probMap = new BeliefMap(length, width);
		map = new WeightedCell[length][width];
		
		for(int x=0; x<map.length; x++)
			for(int y=0; y<map[x].length; y++)
				map[x][y] = new WeightedCell(x,y);
	}
	
	public void setVisited(int x, int y)
	{
		if(isInBounds(x,y))
		{
			map[x][y].visit();
			probMap.setClear(x, y);
		}
	}
	
	public WeightedCell getCell(int x, int y)
	{
		if(isInBounds(x,y))
			return map[x][y];
		
		return null;
	}
	
	public void adjustMap()
	{
		overlayBeliefMap();
		propagateNegativeWeights();
	}
	
	public void setPrecepts(int x, int y, Precepts[] precepts)
	{
		if(isInBounds(x,y))
			probMap.setPrecepts(x, y, precepts);
	}
	
	private boolean isInBounds(int x, int y)
	{
		if(x > -1 && x < map.length && y > -1 && y < map[0].length)
			return true;
		
		return false;
	}
	
	private void overlayBeliefMap()
	{
		for(int x=0; x<map.length; x++)
		{
			for(int y=0; y<map[x].length; y++)
			{
				map[x][y].adjustWeight(probMap.getMap()[x][y]);
				
				// check if gold was found
				if(map[x][y].getWeight() <= -4)
					return;
			}
		}
	}
	
	private void propagateNegativeWeights()
	{
		ArrayList<WeightedCell> pos = new ArrayList<WeightedCell>();
		
		// find cells that should be propagated
		for(int x=0; x<map.length; x++)
			for(int y=0; y<map[x].length; y++)
				if(map[x][y].getWeight() < 0)
					pos.add(map[x][y]);
		
		// propagate
		for(WeightedCell negCell : pos)
		{
			int x = negCell.getX();
			int y = negCell.getY();
			propagateVertical(x, y+1, map[x][y].getWeight(), 1);
			propagateVertical(x, y-1, map[x][y].getWeight(), -1);
			propagateHorizontal(x+1, y, map[x][y].getWeight(), 1);
			propagateHorizontal(x-1, y, map[x][y].getWeight(), -1);
		}
	}
	
	private void propagateVertical(int x, int y, double weight, int direction)
	{
		if(!isInBounds(x,y) || weight >= 0)
			return;	// propagation finished
		
		// check if it is a possible hazard and if it his skip it
		if(probMap.getMap()[x][y].getPitProb() <= 0 && probMap.getMap()[x][y].getWumpProb() <= 0)
			map[x][y].overrideWeight(map[x][y].getWeight()+weight);

		propagateVertical(x, y+(1*direction), weight-1, direction);
	}
	
	private void propagateHorizontal(int x, int y, double weight, int direction)
	{
		if(!isInBounds(x,y) || weight >= 0)
			return;	// propagation finished
		
		// check if it is a possible hazard and if it his skip it
		if(probMap.getMap()[x][y].getPitProb() <= 0 && probMap.getMap()[x][y].getWumpProb() <= 0)
			map[x][y].overrideWeight(map[x][y].getWeight()+weight);
		
		propagateVertical(x, y+1, weight-1, 1);
		propagateVertical(x, y-1, weight-1, -1);
		propagateHorizontal(x+(1*direction), y, weight-1, direction);
	}
}
