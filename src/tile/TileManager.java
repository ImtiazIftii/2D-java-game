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
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		
		getTileImage();
		loadMap("/maps/map01.txt");
	}
	
	public void getTileImage() {
		
		try {
			
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")); //we are sending the grass tile image to our buffered image of our Tile class
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			
			
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
			
			while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
				
				String line = br.readLine();  //this will read each line(collumn)
				
				while(col < gp.maxScreenCol) {
					
					String numbers[] = line.split(" "); //store the line in a number array (the array type is string that we need to change to int later)
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] =num; //this will get the col num from the map.txt; and see which tile we are using
					col++;
					
				}
				if(col == gp.maxScreenCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			
			if(col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}
		
	}
	
}
