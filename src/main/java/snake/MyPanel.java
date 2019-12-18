package snake;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MyPanel extends JPanel 
{
	private  Rectangle2D food;
	private  double foodX;
	private  double foodY;
	private  int index=3;
	private  double width;
	private  Rectangle2D[] snake;
	private  int[] xSnake;
	private  int[] ySnake;
	private  Listener listener;
	private  Timer t;
	private  boolean loss;
	private  Game canvas;
	private  boolean running;
	private  boolean s;
	private  char keep;
	private  String diff;
	private  JPanel LevelPanel;
	private  JPanel PersPanel; 
	private  JPanel GameOverPanel;
	//private InputStream fontStream;
	private StatePanel statePanel;
	public MyPanel(StatePanel statePanel/*InputStream fontStream*/) throws IOException, FontFormatException
	{
		this.statePanel=statePanel;
		//this.fontStream=fontStream;
		food = new Rectangle2D.Double(-20, -20, 20, 20);
		snake = new Rectangle2D[1000];
		xSnake= new int[1000];
		ySnake= new int[1000];
		listener = new Listener();
		t = new Timer(0,listener);
		loss= false;
		canvas = new Game();
		running=false;
		s=true;
		keep= 'd';
		diff="";
		canvas.setBackground(Color.black);
		canvas.setPreferredSize(new Dimension(600,600));
		canvas.setFocusable(true);
		canvas.addKeyListener(listener);
		try 
		{
			LevelPanel = setLevelPanel();
			GameOverPanel = setGameOverPanel();
			GameOverPanel.setBackground(new Color(0,0,0,65));
		} catch (IOException | FontFormatException e) {
			System.out.println(e);
		}
		LevelPanel.setPreferredSize(new Dimension(600,600));
		GameOverPanel.setPreferredSize(new Dimension(600,600));
		add(LevelPanel);
		setBackground(Color.black);
	}
	private  JPanel setLevelPanel() throws IOException, FontFormatException
	{
		Font arcadeBold = FontManager.getFont("BOLD", 80);
		Font arcadePlain = FontManager.getFont("PLAIN", 40);
		Font GMSFont = FontManager.getFont("PLAIN", 35);
		
		JPanel Level= new JPanel();
		Level.setBackground(Color.black);
		
		JButton level = new JButton("Levels");
		level.setRequestFocusEnabled(false);
		level.setContentAreaFilled(false);
		level.setBorderPainted(false);
		level.setBackground(Color.black);
		level.setForeground(Color.white);
		level.setFont(arcadeBold);
		
		JButton slug = new JButton("Slug");
		slug.setRequestFocusEnabled(false);
		slug.setContentAreaFilled(false);
		slug.setBorderPainted(false);
		slug.setForeground(Color.white);
		slug.setFont(arcadePlain);
		slug.addActionListener(listener);
		
		JButton worm = new JButton("Worm");
		worm.setRequestFocusEnabled(false);
		worm.setContentAreaFilled(false);
		worm.setBorderPainted(false);
		worm.setForeground(Color.white);
		worm.setFont(arcadePlain);
		worm.addActionListener(listener);
		
		JButton python = new JButton("Python");
		python.setRequestFocusEnabled(false);
		python.setContentAreaFilled(false);
		python.setBorderPainted(false);
		python.setForeground(Color.white);
		python.setFont(arcadePlain);
		python.addActionListener(listener);
		
		JButton blackMamba = new JButton("Black Mamba");
		blackMamba.setRequestFocusEnabled(false);
		blackMamba.setContentAreaFilled(false);
		blackMamba.setBorderPainted(false);
		blackMamba.setForeground(Color.white);
		blackMamba.setFont(GMSFont);
		blackMamba.addActionListener(listener);
		
		Level.setLayout(new GridLayout(5,1));
		Level.add(level);
		Level.add(slug);
		Level.add(worm);
		Level.add(python);
		Level.add(blackMamba);
		
		return Level;
	}
	private JPanel setGameOverPanel() throws IOException, FontFormatException
	{
		Font arcadeBold = FontManager.getFont("BOLD", 75);
		Font arcadePlain = FontManager.getFont("PLAIN", 40);

		JPanel GameOverPanel= new JPanel();
		GameOverPanel.setBackground(new Color(255,255,255,65));
		
		JButton GameOver = new JButton("Game Over");
		GameOver.setFont(arcadeBold);
		GameOver.setRequestFocusEnabled(false);
		GameOver.setFocusPainted(false);
		GameOver.setContentAreaFilled(false);
		GameOver.setBorderPainted(false);
		GameOver.setBackground(Color.black);
		GameOver.setForeground(Color.white);
		
		JPanel restart = new JPanel();
		restart.setBackground(Color.black);
		
		JButton retry = new JButton("Retry");
		retry.setRequestFocusEnabled(false);
		retry.setFocusPainted(false);
		retry.setContentAreaFilled(false);
		retry.setBorderPainted(false);
		retry.setForeground(Color.white);
		retry.setFont(arcadePlain);
		retry.addActionListener(listener);
		
		JButton levels = new JButton("Levels");
		levels.setRequestFocusEnabled(false);
		levels.setContentAreaFilled(false);
		levels.setBorderPainted(false);
		levels.setForeground(Color.white);
		levels.setFont(arcadePlain);
		levels.addActionListener(listener);
		
		restart.add(retry);
		restart.add(levels);
		
		GameOverPanel.setLayout(new GridLayout(2,1));
		GameOverPanel.add(GameOver);
		GameOverPanel.add(restart);
		
		return GameOverPanel;
	}
	public void setPanel(double Speed, double Width, String diff) 
	{
		this.diff=diff;
		width=Width;
		if(loss==false)
		{
			setGame(Width);
			canvas.repaint();
		}
		t.setDelay((int)(Width/(Speed/1000)));
	}
	public void setCurrentPanel(String key)
	{
		switch(key)
		{
			case "levels_to_game": 
			{
				remove(LevelPanel);
				revalidate();
				repaint();
				add(canvas);
				canvas.requestFocus();
				canvas.setFocusable(true);
			}break;
			case "levels_to_pers":
			{
				remove(LevelPanel);
				revalidate();
				repaint();
				add(PersPanel);
			}break;
			case "game_to_GameOver":
			{
				setLayout(new CardLayout());
				
				add(GameOverPanel);
				add(canvas);
				canvas.requestFocus();
			}break;
			case "GameOver_to_game":
			{
				statePanel.resetFoodEaten();
				remove(GameOverPanel);
				revalidate();
				repaint();
				canvas.requestFocus();
				canvas.setFocusable(true);
			}break;
			case "GameOver_to_levels":
			{
				statePanel.resetFoodEaten();
				remove(GameOverPanel);
				revalidate();
				repaint();
				remove(canvas);
				revalidate();
				repaint();
				add(LevelPanel);
			}break;
		}
	}
	public void setGame(double Width)
	{
		width=Width;
		for(int i=0;i<1000;i++)
		{
			snake[i] = new Rectangle2D.Double(-width,-width,width,width);
		}
		xSnake[0]=300;
		ySnake[0]=300;
		xSnake[1]=xSnake[0]-(int)width;
		ySnake[1]=ySnake[0];
		xSnake[2]=xSnake[1]-(int)width;
		ySnake[2]=ySnake[1];
		foodX=500;
		foodY=ySnake[0];
	}
	public class Game extends Canvas
	{	
		public void paint(Graphics g)
		{
			Graphics2D g2D = (Graphics2D)g;
			for(int i=0;i<index;i++)
			{
				g2D.setColor(Color.white);
				snake[i].setRect(xSnake[i], ySnake[i], width, width);
				g2D.fill(snake[i]);
			}		
			int j=1;
			while(loss==false && j<index)
			{
				if(((xSnake[0]==xSnake[j]) && (ySnake[0]==ySnake[j])) || (xSnake[0]<0 || xSnake[0]>(600- width) || ySnake[0]<0 || ySnake[0]>(600 - width)))
				{
					t.stop();
					loss=true;
					Snake.setPanel((int) ((width*1000)/t.getDelay()), (int) width, "game_to_GameOver",diff);
					try {statePanel.setFileRecord(diff+".txt");} catch (IOException e) {e.printStackTrace();}
					running=false;
				}
				j++;
			}
			if((int)foodX==xSnake[0] && (int)foodY==ySnake[0])
			{
				int y=0;
				foodX= Math.random()*(600-width);
				foodY= Math.random()*(600-width);
				for(int i=1;i<=index;i++)
				{
					y=i-1;
					while(y>0)
					{
						if((int)foodX==xSnake[y] && ((int)foodY==ySnake[y]) || ((int)foodX%width!=0 || (int)foodY%width!=0))
						{
							foodX= Math.random()*(600-width);
							foodY= Math.random()*(600-width);
							y=i-1;
						}
						else
						{
							y--;
						}
					}
				}
				index++;
				snake[index]=food;
				statePanel.setLabels();
			}
			g2D.setColor(Color.white);
			food.setRect((int)foodX, (int)foodY, width, width);
			g2D.fill(food);		
			for(int i=0;i<index;i++)
			{
				snake[i].setRect(xSnake[i], ySnake[i], width, width);
				g2D.fill(snake[i]);
			}
		}
	}
	private class Listener implements KeyListener, ActionListener
	{
		private char key= ' ';
		@Override
		public void keyPressed(KeyEvent event) 
		{
			canvas.requestFocusInWindow();
			if(event.getKeyCode()==KeyEvent.VK_UP || event.getKeyChar()=='w')
			key='w';
					
			if(event.getKeyCode()==KeyEvent.VK_DOWN || event.getKeyChar()=='s')
			key='s';
					
			if(event.getKeyCode()==KeyEvent.VK_LEFT || event.getKeyChar()=='a')
			key='a';
					
			if(event.getKeyCode()==KeyEvent.VK_RIGHT || event.getKeyChar()=='d')
			key='d';
					
			t.start();
			canvas.repaint();
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{			
			if(running)
			{
				for(int i=index;i>0;i--)
				{
					xSnake[i]=xSnake[i-1];
					ySnake[i]=ySnake[i-1];
				}
				switch(key)
				{
				case 'w':
					{
						if(keep!='s')
						{
							ySnake[0]=ySnake[0]-(int)width;
							keep='w';
						}
						else
						{
							ySnake[0]=ySnake[0]+(int)width;
							key='s';
							keep='s';
						}
						s=false;
					}break;
				case 's':
					{
						if(keep!='w')
						{
							ySnake[0]=ySnake[0]+(int)width;
							keep='s';
						}
						else
						{
							ySnake[0]=ySnake[0]-(int)width;
							key='w';
							keep='w';
						}
						s=false;
					}break;
				case 'a':
					{
						if(keep!='d')
						{
							xSnake[0]=xSnake[0]-(int)width;
							keep='a';
						}
						else
						{
							xSnake[0]=xSnake[0]+(int)width;
							key='d';
							keep='d';
						}	
						s=false;
					}break;
				case 'd':
					{
						if(keep!='a')
						{
							xSnake[0]=xSnake[0]+(int)width;
							keep='d';
						}
						else
						{
							xSnake[0]=xSnake[0]-(int)width;
							key='a';
							keep='a';
						}
						s=false;
					}break;
					default:
					{
						if(s)
						{
							xSnake[0]=xSnake[0]+(int)width;
							key='d';
							keep='d';
						}
						
					}
				}
				canvas.repaint();
			}
			else
			{
				switch(e.getActionCommand())
				{
					case "Slug": 
						{
							keep='d';
							index=3;
							running=true;
							s=true;
							loss=false;
							Snake.setPanel(100, 20, "levels_to_game","Slug");
							try {setInitRecord("Slug.txt");} catch (IOException e1) {e1.printStackTrace();}
						}break;
					case "Worm": 
						{
							keep='d';
							index=3;
							s=true;
							running=true;
							loss=false;
							Snake.setPanel(250, 20, "levels_to_game","Worm");
							try {setInitRecord("Worm.txt");} catch (IOException e1) {e1.printStackTrace();}
						}break;
					case "Python":
						{
							keep='d';
							index=3;
							s=true;
							running=true;
							loss=false;
							Snake.setPanel(500, 20, "levels_to_game","Python");
							try {setInitRecord("Python.txt");} catch (IOException e1) {e1.printStackTrace();}
						}break;
					case "Black Mamba": 
						{
							keep='d';
							index=3;
							s=true;
							running=true;
							loss=false;
							Snake.setPanel(875, 20, "levels_to_game","Black Mamba");
							try {setInitRecord("Black Mamba.txt");} catch (IOException e1) {e1.printStackTrace();}
						}break;
					case "Retry": 
						{
							keep='d';
							index=3;
							s=true;
							running=true;
							loss=false;
							Snake.setPanel((int) ((width*1000)/t.getDelay()), (int) width, "GameOver_to_game",diff);
						}break;
					case "Levels": 
						{
							running=false;
							loss=false;
							Snake.setPanel(600, 20, "GameOver_to_levels","");
						}break;
				}
			}
		}
		public void setInitRecord(String fileName) throws IOException
		{
			//InputStream inputStream; //= new FileInputStream(new File(fileName));
			fileName= "/"+fileName;
			URL url = MyPanel.class.getResource(fileName);
			InputStream inputStream = MyPanel.class.getResourceAsStream(fileName);

			BufferedReader testReader = new BufferedReader(new InputStreamReader(inputStream));
			String record= testReader.readLine();
			testReader.close();
			statePanel.setLabelRecord(record);
		}
		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
	}
}