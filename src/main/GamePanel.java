package main;

import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	//Screen Settings
	final int originalTileSize = 16; //16x16 tile
	final int scale =3;
	
	public final int tileSize = originalTileSize*scale; //48x48 tile
	public final int maxScreenCol =16;
	public final int maxScreenRow =12;
	public final int screenWidth =tileSize * maxScreenCol; //768 pixels
	public final int screenHeight =tileSize * maxScreenRow; //576 pixels
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50; //our new world map which is 50x50
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//FPS
	int FPS =60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(); //creating an object of KeyHandler to use it in our game
	Thread gameThread; //this is for creating timesense/fps in our game
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this); //pass this class through the constructor of AssetSetter
	public Player player =new Player(this,keyH); 	//Set Player's default position
	public SuperObject obj[] = new SuperObject[10]; //this 10 means we can display upto 10 objects same time 
	//displaying too many objects can slow down the game
	

	

	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);  //this is used for better rendering per
		this.addKeyListener(keyH); //so it can listen to the key inputs
		this.setFocusable(true);
	}
	
	public void setupGame() { //we want our objects placed before the game starts so we add this object in the main class before the game starts
		
		aSetter.setObject(); //called the method of AssetSetter class
	}
	
	
	public void startGameThread() {
		
		gameThread = new Thread(this); //this is passing the gamePanel class in this method
		gameThread.start();
	}
	
	
	@Override
	//this is the delta game looping method
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount =0;
				timer =0;
			}
			
		}
	}
	
//this is another game looping method(sleep method or smth)
//	public void run() {
//		
//		double drawInterval = 1000000000/FPS;  //using nanoSec as calc unit we draw the screen every 0.0166s to generate 60 fps
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		
//		while(gameThread!=null) {//This is an infinite loop that will keep updating our character positions and update screen
//			
//			// 1 Update: update information such as chracterposition
//			update();
//			// 2 Draw: draw the screen with the updated information
//			//but we need a interval between this so that it doesnt get infinitey times upadated; we use fps
//			repaint();
//			//might be confusing but this is how we call the paintcomponent method
//		
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime =remainingTime/1000000;   //because our sleep method catches only miliseconds
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;   //dont sleep when we dont have time le
//				}
//				
//				Thread.sleep((long) remainingTime);
//			
//				nextDrawTime += drawInterval;
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
	

	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {       //this graphics is my pencil that ill use to draw
		//built in java method to draw things on jPanel
		super.paintComponent(g);
		
		Graphics2D g2 =(Graphics2D)g; 
		//Tile
		tileM.draw(g2);  //we draw tile first and then player
		
		//Object
		for(int i = 0; i < obj.length; i++) {  //scanning our superObject array
			if(obj[i]!=null) {  //if there is any object inside the array we draw it
				obj[i].draw(g2, this);
			}
		}
		
		//Player
		player.draw(g2);
		
		g2.dispose(); //this is a good practice to save memory
		
	}
}
