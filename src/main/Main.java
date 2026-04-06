package main;

import javax.swing.JFrame;

public class Main {
	public static void main(String[]args) {
		
		JFrame window =new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Life Of Phi");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);  //adding jpanel to this window
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}
