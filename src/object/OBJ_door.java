package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_door extends Entity {

    GamePanel gp;

    public OBJ_door(GamePanel gp)
    {
        super(gp);
        name = "Door";
        down1 = setup("objects/door",gp.tileSize,gp.tileSize);
        collision = true;
    }

}