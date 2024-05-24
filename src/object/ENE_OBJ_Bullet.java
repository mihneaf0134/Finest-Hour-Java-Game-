package object;

import entity.Projectile;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ENE_OBJ_Bullet extends Projectile {

    GamePanel gp;

    public ENE_OBJ_Bullet(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Enemy_Bullet";
        speed = 10;
        maxLife = 70;
        life = maxLife;
        useCost = 0;
        alive = false;
        getImage();
    }

    public void getImage() {

        up1 = setup("projectile/bullet_up", gp.tileSize, gp.tileSize);
        down1 = setup("projectile/bullet_down", gp.tileSize, gp.tileSize);
        left1 = setup("projectile/bullet_left", gp.tileSize, gp.tileSize);
        right1 = setup("projectile/bullet_right", gp.tileSize, gp.tileSize);
    }
}