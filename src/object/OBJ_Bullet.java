package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Bullet extends Projectile {

    GamePanel gp;

    public OBJ_Bullet(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Bullet";
        speed = 10;
        maxLife = 70;
        life = maxLife;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {

        up1 = setup("projectile/bullet_up", gp.tileSize, gp.tileSize);
        down1 = setup("projectile/bullet_down", gp.tileSize, gp.tileSize);
        left1 = setup("projectile/bullet_left", gp.tileSize, gp.tileSize);
        right1 = setup("projectile/bullet_right", gp.tileSize, gp.tileSize);
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.Clip >= useCost)
        {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user)
    {
        user.Clip -= useCost;
    }
}