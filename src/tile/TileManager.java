package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp =gp;
		
		tile = new Tile[10];  //well create 10 types of tiles
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	public void getTileImage() {
		
		try {
			
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")); //we are sending the grass tile image to our buffered image of our Tile class
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String filePath) {
		
		try {
			InputStream is =getClass().getResourceAsStream(filePath);
			BufferedReader br  =new BufferedReader(new InputStreamReader(is));  //bufferedReader will read the above text line in a line
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();  //this will read each line(collumn)
				
				while(col < gp.maxWorldCol) {
					
					String numbers[] = line.split(" "); //store the line in a number array (the array type is string that we need to change to int later)
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] =num; //this will get the col num from the map.txt; and see which tile we are using
					col++;
					
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			//if its a col =0 row=0 tile;then x and y*48 =0 ; else if its x=1 and y=1 then it would be 48 and 48 
			// we are implementing the camera here; meaning it will show me the next 48 tiles or smth i guess
			//worldX is the x position of the world and worldY is the y position of the world
			//screenX and screenY is which part of the world we are at currently
			//if the player is 500 tiles x and y away from the world; this means; 
			//the screen position should be -500,-500
			//now if we are at the corner of the world like 0,0
			//then we would not want to show the black bars 
			//thats why we add the offset to mitigate that black bar
			//offset is: using the +gp.player.screenX and gp.player.screenY
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			
			//inside this if statement we are only drawing the tiles that are inside this boundary
			if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
				worldX - gp.tileSize< gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize< gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			
			worldCol++;

			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
		
	}
	
}
