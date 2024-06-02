public class ConsoleView 
{
	Puzzle p;
	String[][] labels;
	int puzzleSize;
	
	public ConsoleView(Puzzle p)
	{
		this.p = p;
		this.puzzleSize = p.getSize();
		this.labels = new String[puzzleSize][puzzleSize];
		updateLabels();
	}
	
	public void updateLabels()
	{
		//Converts the 2d array of numbers 0-8 into strings 1-8 with a space instead of 0
		for(int r=0; r<puzzleSize; r++) 
		{
			for(int c=0; c<puzzleSize; c++)
			{
				if(p.getGrid()[r][c]==0)
					labels[r][c] = " ";
				else 
					labels[r][c] = "" + p.getGrid()[r][c];
			}
		}
	}
	
	//For testing without GUI
	public void printGrid()
	{
		for (int i = 0; i < puzzleSize; i++)
		{
			for (int j = 0; j < puzzleSize; j++)
				System.out.print("*-----");
			System.out.println("*");
			for (int j = 0; j < puzzleSize; j++)
			{
				String curLabel = labels[i][j];
				if (curLabel.equals(" ") || Integer.parseInt(curLabel) < 10) // double digit
					System.out.print("|  " + curLabel + "  ");
				else
					System.out.print("|  " + curLabel + " ");
			}
			System.out.println("|");
		}
		for (int j = 0; j < puzzleSize; j++)
			System.out.print("*-----");
		System.out.println("*");
	}
	
	//The user enters the number of the tile that they want to move
	//This method searches the array of labels looking for that number
	//and then figures out the row/col of that tile in the grid
	public int[] convertToPosition(String userNum)
	{
		// Wow this algorithm is horrid, should have a dictionary 
		int[] pos = {-1, -1};
		for(int r = 0; r < p.getGrid().length; r++)
		{
			for(int c = 0; c < p.getGrid()[r].length; c++)
			{
				if(labels[r][c].equals(userNum))
				{
					pos[0] = r;
					pos[1] = c;
				}
			}
		}
		return pos;
	}
}

