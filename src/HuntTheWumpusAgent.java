import java.util.PriorityQueue;

public class HuntTheWumpusAgent 
{
	int currX, currY, mapLength, mapHeight;
	WeightedMap map;
	
	public HuntTheWumpusAgent(int startX, int startY, int mapLength, int mapHeight)
	{
		this.currX = startX;
		this.currY = startY;
		this.mapLength = mapLength;
		this.mapHeight = mapHeight;
		
		map = new WeightedMap(mapLength, mapHeight);
	}
	
	public int[] moveTo()
	{
		int[] edgePos = {currX+1, currX-1, currY+1, currY-1};
		PriorityQueue<WeightedCell> queue = new PriorityQueue<WeightedCell>(new WeightedCellComparator());
		
		for(int index=0; index<edgePos.length; index++)
		{
			int tmpX = currX;
			int tmpY = currY;
			WeightedCell tmpCell;
			
			if(index < 2)
				tmpX = edgePos[index];
			else
				tmpY = edgePos[index];
			
			tmpCell = map.getCell(tmpX, tmpY);
			if(tmpCell != null)
				queue.add(tmpCell);
		}
		
		currX = queue.peek().getX();
		currY = queue.peek().getY();
		
		return new int[]{currX, currY};
	}
	
	public void observeEnviroment(Precepts[] precepts)
	{
		map.setPrecepts(currX, currY, precepts);
		map.setVisited(currX, currY);
		map.adjustMap();
	}
}
