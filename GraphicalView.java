import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Image;

public class GraphicalView extends JFrame implements ActionListener
{
	private Puzzle p;
	private JButton[][] buttons; 
    private JButton nextButton;
	private JLabel directions,movesReport;
	private int puzzleSize;
    private final int buttonSize = 100;
    private final int sidePadding = 10;
    private BufferedImage img;
    private Icon imgs[];
	
	public GraphicalView(Puzzle p)
	{
        // init
		super("Sliding Puzzle");
		this.p = p;
		puzzleSize = p.getSize();

        try
        { img = ImageIO.read(new File("./raccoon.jpeg")); } 
        catch (IOException e) 
        { e.printStackTrace(); return; }

        imgs = new ImageIcon[puzzleSize * puzzleSize + 1]; // index by 1
        int sliceSize = Math.min(img.getWidth(), img.getHeight()) / puzzleSize;
        // fill up <imgs> row by row
        for (int c = 0; c < puzzleSize; c++)
        {
            for (int r = 0; r < puzzleSize; r++)
            {
                int x = r * sliceSize;
                int y = c * sliceSize;
                BufferedImage subImage = img.getSubimage(x, y, sliceSize, sliceSize);
                ImageIcon icon = new ImageIcon(subImage.getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH));
                imgs[c*puzzleSize+r+1] = icon;
            }
        }

        // Set the size of the window
		setSize(sidePadding*2+buttonSize*puzzleSize, puzzleSize*buttonSize+160); 
		
		// Print directions
		directions = new JLabel("Order the numbers");
		directions.setBounds(sidePadding, 5,400,20);  
		add(directions);

		//Make new buttons with blank labels
		JButton[][] buttonArr = new JButton[puzzleSize][puzzleSize];
		for (int i = 0; i < puzzleSize; i++)
		{
			for (int j = 0; j < puzzleSize; j++)
            {
                buttonArr[i][j] = new JButton("");
            }
		}
		this.buttons = buttonArr;

		
		//Set positions and sizes of buttons in the window
        for (int i = 0; i < buttons.length; i++)
        {
            for (int j = 0; j < buttons[i].length; j++)
            {
                buttons[i][j].setBounds(sidePadding+buttonSize*j, 30+buttonSize*i, buttonSize, buttonSize);
                buttons[i][j].setFocusPainted(false);
                //buttons[i][j].setBorderPainted(false);
                buttons[i][j].setContentAreaFilled(false);
                add(buttons[i][j]); // add button to JFrame
                buttons[i][j].addActionListener(this); // event listener
            }
        }

		// Number of moves
		movesReport = new JLabel("Moves: " + p.getNumMoves());
		movesReport.setBounds(sidePadding,30+buttonSize*puzzleSize,100,40);  
		add(movesReport);

		// "New Game" button
		nextButton=new JButton("New Game");
		nextButton.setBounds(sidePadding,80+buttonSize*puzzleSize,100,40);
		nextButton.addActionListener(this);
		nextButton.setBackground(Color.black);
		nextButton.setForeground(Color.green);
		add(nextButton);
		 
        // Make things show up!
		updateLabels();  
		setLayout(null);
		setVisible(true);   //it's set up, so now you can make it visible!
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //hit X to close the window
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==nextButton) // New Game
		{
            p.setNumMoves(0);
            p.setGrid();
		}
        else if(p.win()) // Win
            return;
        else // Move Tile
        {
            for (int i = 0; i < buttons.length; i++)
            {
                for (int j = 0; j < buttons[i].length; j++)
                {
                    if (e.getSource() == buttons[i][j])
                        p.moveTile(i, j);
                }
            }
        }

		updateLabels();
        if (p.win())
        {
            directions.setText("CONGRATS BRUV YOU WON");
            buttons[puzzleSize-1][puzzleSize-1].setIcon(imgs[imgs.length-1]);
        }
	}
	
	public void updateLabels()
	{
		for(int r=0; r<puzzleSize; r++)
		{
			for(int c=0; c<puzzleSize; c++)
			{
				if(p.getGrid()[r][c] == 0)
                {
                    //buttons[r][c].setIcon(null);
                    buttons[r][c].setText(" ");
                }
				else
                {
                    //buttons[r][c].setIcon(imgs[p.getGrid()[r][c]]);
                    buttons[r][c].setText(""+p.getGrid()[r][c]);
                }
			}
		}
		
		movesReport.setText("Moves: "+p.getNumMoves());
	}
}


