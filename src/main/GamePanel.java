package main;

import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	//Screen Settings
	final int originalTileSize = 16; //16x16 tile
	final int scale =3;
	
	final int tileSize = originalTileSize*scale; //48x48 tile
	final int maxScreenCol =16;
	final int maxScreenRow =12;
	final int screenWidth =tileSize * maxScreenCol; //768 pixels
	final int screenHeight =tileSize * maxScreenRow; //576 pixels
	
	//FPS
	int FPS =60;
	
	KeyHandler keyH = new KeyHandler(); //creating an object of KeyHandler to use it in our game
	Thread gameThread; //this is for creating timesense/fps in our game
	
	//Set Player's default position
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;

	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);  //this is used for better rendering per
		this.addKeyListener(keyH); //so it can listen to the key inputs
		this.setFocusable(true);
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
	
	//this is one game looping method(sleep method or smth)
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
		if(keyH.upPressed == true) {
			playerY -= playerSpeed;  //the top left corner is x0 y0 so to increase y or go down we need to plus y; in this case we are upping y ; so we minus the speed
		}                             //here it moves by 4 pixels;
		else if(keyH.downPressed == true) {
			playerY += playerSpeed;
		}
		else if(keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
		else if(keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
	}
	
	public void paintComponent(Graphics g) {       //this graphics is my pencil that ill use to draw
		//built in java method to draw things on jPanel
		super.paintComponent(g);
		
		Graphics2D g2 =(Graphics2D)g; 
		
		g2.setColor(Color.white);
		
		g2.fillRect(playerX,playerY,tileSize,tileSize);  //draw a rectangle on screen with a specific color; for the time being we are using this a player character
		
		g2.dispose(); //this is a good practice to save memory
		
	}
}
