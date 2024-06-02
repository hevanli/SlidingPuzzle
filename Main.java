import java.util.Scanner;


public class Main {
    private static Puzzle createPuzzle(Scanner scan)
    {
        System.out.print("How large would you like your puzzle? (n x n) >> ");
        int puzzleSize = Integer.parseInt(scan.nextLine());
        return new Puzzle(puzzleSize);
    }

  	public static void main(String[] args) 
	{
		Scanner scanner = new Scanner(System.in);

		System.out.print("Would you like to play the <Console> or <Graphic> version? >> ");
		String version = scanner.nextLine();
		if (version.equals("Console")) // Console Version
		{
            Puzzle p = createPuzzle(scanner);
			ConsoleView cv = new ConsoleView(p);
			
			cv.printGrid();
			
			String num = scanner.nextLine();
			
			//Until the user types quit, move the tile the user indicated, updateLabels, print, and ask for a new number
			while(!num.toLowerCase().equals("quit"))
			{
                if (num.equals("")) continue;

                System.out.print("\033[H\033[2J");  
                System.out.flush();
				int[] pos = cv.convertToPosition(num);
				p.moveTile(pos[0], pos[1]);
	      		cv.updateLabels();
				cv.printGrid();
                
                if (p.win()) break;

				num = scanner.nextLine();
			}
	
			if (p.win())
				System.out.println("GOOD JOB YOU WIN");
		  	else
				System.out.println("QUITTER");
		}
		else // GUI Version
		{
			Puzzle p = createPuzzle(scanner);
			GraphicalView gv = new GraphicalView(p);
		}
		scanner.close();
  	}
}
