package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Clip extends Entity {

    GamePanel gp;

    public OBJ_Clip(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Clip";
        image = setup("objects/uigun",gp.tileSize*2,gp.tileSize*2);
    }
}
