package snake;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FontFormatException;
import java.io.IOException;

public class Snake 
{
	private static JFrame frame;
	private static MyPanel game;
	public static void main(String[] args) throws IOException, FontFormatException
	{		
		frame = new JFrame("Snake");
		StatePanel statePanel = new StatePanel();
		game = new MyPanel(statePanel);
		frame.add(statePanel, BorderLayout.NORTH);	
		frame.setVisible(true);
		frame.setResizable(false);
		System.out.println(frame.getLayout().toString());
		Snake.setPanel(250,20,"null","");
	}
	public static void setPanel(int Speed, int Width, String key, String diff)
	{
		game.setPanel(Speed, Width, diff);
		game.setCurrentPanel(key);
		frame.add(game);
		
		System.out.println(game.getLayout().toString());

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/*public InputStream setFontInputStream(String fileName)
	{
		
	}*/
}