import java.util.ArrayList;
import java.util.Scanner;

public class Puzzle 
{
	private int[][] grid;
	private int moves;
	private int puzzleSize;
	
	
	public Puzzle(int size) throws IllegalArgumentException
	{
		if (size < 3 || size > 9)
			throw new IllegalArgumentException("I'm too lazy to make it work with sizes above 9");
		puzzleSize = size;
        moves = 0;
		setGrid();
	}

	private int[] randomGrid()
	{
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = 0; i < puzzleSize * puzzleSize; i++) nums.add(i);

		int[] result = new int[puzzleSize * puzzleSize];
		for (int i = 0; i < puzzleSize * puzzleSize; i++)
			result[i] = nums.remove((int)(Math.random()*nums.size()));
		
		return result;
	}

	private boolean isSolvable(int[] grid)
	{
		int inv_count = 0;
		for (int i = 0; i < puzzleSize * puzzleSize - 1; i++)
			for (int j = i+1; j < puzzleSize * puzzleSize; j++)
				 if (grid[i] != 0 && grid[j] != 0 && grid[i] > grid[j])
					  inv_count++;
		return inv_count % 2 == 0;
	}

	public void setGrid()
	{
		int[] oneDimensionalGrid = randomGrid();
		while (!isSolvable(oneDimensionalGrid))
			oneDimensionalGrid = randomGrid();
		
		grid = new int[puzzleSize][puzzleSize];
		for (int i = 0; i < puzzleSize * puzzleSize; i++)
			grid[i/puzzleSize][i%puzzleSize] = oneDimensionalGrid[i];
	}
	
	public int[][] getGrid()
	{
		return grid;
	}	
	
	//Given row and column, move value if possible
	public void moveTile(int r, int c)
	{
		boolean moved = false;
		
		if(r-1>=0 && grid[r-1][c]==0) // Move up
		{
			grid[r-1][c] = grid[r][c];
			moved = true;
		}
		else if(r+1<puzzleSize && grid[r+1][c]==0) // Move down
		{
			grid[r+1][c] = grid[r][c]; 
			moved = true;
		}
		else if(c-1>=0 && grid[r][c-1]==0) // Move left
		{
			grid[r][c-1] = grid[r][c]; 
			moved = true;
		}
		else if(c+1<puzzleSize && grid[r][c+1]==0) // Move right
		{
			grid[r][c+1] = grid[r][c];
			moved = true;
		}

		if (moved)
		{
			grid[r][c] = 0;
			moves++;
		}
	}	

	public boolean win()
	{
		for (int i = 0; i < puzzleSize * puzzleSize - 1; i++)
			if (grid[i/puzzleSize][i%puzzleSize] != i+1) return false;
		return true;
	}	
	
	public int getNumMoves()
	{ 
		return moves; 
	}

    public void setNumMoves(int n)
    {
        moves = n;
    }

	public int getSize()
	{
		return puzzleSize;
	}

}

