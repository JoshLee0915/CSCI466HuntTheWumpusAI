public class BeliefMap 
{
	private BeliefCell[][] map;
	public BeliefMap(int length, int width)
	{
		map = new BeliefCell[length][width];
		
		for(int x=0; x<map.length; x++)
			for(int y=0; y<map[x].length; y++)
				map[x][y] = new BeliefCell();
	}
	
	public void setClear(int x, int y)
	{
		if(isInBounds(x,y))
			map[x][y].setClear();
	}
	
	public void setPrecepts(int x, int y, Precepts[] precepts)
	{
		if(isInBounds(x,y))
		{
			// set the precept(s)
			for(Precepts precept : precepts)
				map[x][y].setPrecept(precept);
			
			// update the cells around the precepts
			updateMap(x-1, y);
			updateMap(x+1, y);
			updateMap(x, y-1);
			updateMap(x, y+1);
		}
	}
	
	public void removePrecept(int x, int y, Precepts precept)
	{
		if(isInBounds(x,y))
			map[x][y].removePrecept(precept);
	}
	
	public BeliefCell[][] getMap()
	{	
		return map;
	}
	
	// update the whole map
	public void updateMap()
	{		
		for(int x=0; x<map.length; x++)
			for(int y=0; y<map[x].length; y++)
				updateMap(x, y);
	}

	// update around a specific x,y
	public void updateMap(int x, int y) 
	{
		int totalChecked = 0, wind = 0, smell = 0, glitter = 0;
		int[] check = {x-1, x+1, y-1, y+1};
		
		// check if the cell is in bounds or clear
		if(!isInBounds(x, y) || map[x][y].isClear())
			return;
		
		for(int index=0; index<4; index++)
		{
			int newX = x;
			int newY = y;
			
			if(index < 2)
				newX = check[index];
			else
				newY = check[index];
			
			if(isInBounds(newX, newY))
			{
				totalChecked++;
				
				// check the precepts
				if(map[newX][newY].isBreezy())
					wind++;
				if(map[newX][newY].isSmelly())
					smell++;
				if(map[newX][newY].isGlittery())
					glitter++;
			}
		}
		
		// update the probability
		map[x][y].setProb((double)wind/(double)totalChecked, (double)smell/(double)totalChecked, 
							(double)glitter/(double)totalChecked);
	}
	
	private boolean isInBounds(int x, int y)
	{
		if(x > -1 && x < map.length && y > -1 && y < map[0].length)
			return true;
		
		return false;
	}
}
