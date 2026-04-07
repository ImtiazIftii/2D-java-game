package entity;

import main.KeyHandler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX; //this indicates where we draw the player; we also want our background to move with player as our map is bigger
	public final int screenY; //final variables mean these variables do not change throughout the game
	int hasKey = 0; //indicates how many keys the player currently has
	
	
	
	
	public Player(GamePanel gp,KeyHandler keyH) {
		
		this.gp =gp;
		this.keyH =keyH;
		
		screenX = gp.screenWidth/2 -(gp.tileSize/2);
		screenY = gp.screenHeight/2 -(gp.tileSize/2); //character is displayed at the center of the screen
		
		solidArea = new Rectangle(); //we are creating a rectangle with x,y,height and width values
		solidArea.x = 8;         //instead of 0,0 we use this values which causes the collision to be a bit smaller
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;  //this is players position on the world map
		worldY = gp.tileSize * 21;
		speed = 4;
		direction ="down";  //doesn't matter what i use here; this is the default direction
	
	}
	
	public void getPlayerImage() {
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void update() { 
		
	if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {	//so the player moves only when a key is pressed	
		if(keyH.upPressed == true) {
			direction ="up";
			  //the top left corner is x0 y0 so to increase y or go down we need to plus y; in this case we are upping y ; so we minus the speed
		}                             //here it moves by 4 pixels;
		else if(keyH.downPressed == true) {
			direction ="down";
			
		}
		else if(keyH.leftPressed == true) {
			direction ="left";
			
		}
		else if(keyH.rightPressed == true) {
			direction ="right";
			
		}
		
		//Check tile Collision
		collisionOn = false;
		gp.cChecker.checkTile(this); //"this" inside the method means passing the Player class it will see player class as entity
		
		//Check Object Collsion
		int objIndex =gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		
		
		
		//If collision is false, PLAYER CAN MOVE ; only when the tiles are not solid
		if(collisionOn == false) {
			
			switch(direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
		
		spriteCounter++;        //this variable is defined in the entity class
		if(spriteCounter > 12) {   //this means player image changes every 12 frames
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	  }
	}
	
	
	public void pickUpObject(int i) {
		
		if(i != 999) {     //this means we have touched an object *wink*
			
			String objectName = gp.obj[i].name; //imported objects name
			
			switch(objectName) {
			case"Key":
				hasKey++;
				gp.obj[i] = null;
				System.out.println("Key: "+hasKey);
				break;
			case"Door":
				if(hasKey > 0) {
					gp.obj[i] = null;
					hasKey --;
				}
				System.out.println("Key: "+hasKey);
				break;
			
			
			}
		}
	}
	
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			else if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			else if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			else if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			else if(spriteNum == 2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);  //basically creates an image with the passed paramters
		
//		g2.setColor(Color.white);		
//		g2.fillRect(x , y,gp.tileSize, gp.tileSize);  //draw a rectangle on screen with a specific color; for the time being we are using this a player character
	}
}
