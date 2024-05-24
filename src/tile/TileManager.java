package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];

    public TileManager(GamePanel gp)
    {
        this.gp = gp;
        tile = new Tile[100];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadmap("maps/map01.txt",0);
        loadmap("maps/map02.txt",1);
        loadmap("maps/map03.txt",2);
    }

    public void getTileImage()
    {

        setup(0,"1snow",false);
        setup(1,"2wall",true);
        setup(2,"3tree",true);
        setup(3,"4ice",true);
        setup(4,"5dirt",false);
        setup(5,"6floor",false);
        setup(6,"7sand",false);
        setup(7,"8sand",false);
        setup(8,"9desertrock",true);
        setup(9,"a_1desertrock",true);
        setup(10,"a_2cactus",true);
        setup(11,"a_3ddaysand",false);
        setup(12,"a_4waterdday",true);
        setup(13,"a_5waterend",false);
        setup(14,"a_6czechedgehog",true);
        setup(15,"a_7flag_soviet",true);
        setup(16,"a_8bunker",false);



    }

    public void setup(int index, String imageName, boolean collision){

        UtilityTool uTool = new UtilityTool();

        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    public void loadmap(String filePath,int map)
    {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow)
            {
                String line = br.readLine();

                while(col < gp.maxWorldCol)
                {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol)
                {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch(Exception e){

        }
    }

    public void draw(Graphics2D g2)
    {
        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow)
        {
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
            {
                g2.drawImage(tile[tileNum].image, screenX ,screenY , null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol)
            {
                worldCol = 0;
                worldRow ++;

            }
        }
    }

}
