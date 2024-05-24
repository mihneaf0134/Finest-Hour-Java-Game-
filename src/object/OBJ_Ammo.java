package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Ammo extends Entity {

    GamePanel gp;
    public OBJ_Ammo(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = type_pickup0nly;
        name = "Ammo";
        value = 8;
        down1 = setup("objects/ammo",gp.tileSize,gp.tileSize);
    }
    public void use(Entity entity)
    {
        gp.player.MaxMagazine += value;
    }
}
