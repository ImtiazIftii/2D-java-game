package main;

import java.awt.Color;
import java.awt.Dimension;

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
	
	Thread gameThread; //this is for creating timesense/fps in our game
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);  //this is used for better rendering per
		
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this); //this is passing the gamePanel class in this method
		gameThread.start();
	}
	
	
	@Override
	public void run() {
		
	}
}
