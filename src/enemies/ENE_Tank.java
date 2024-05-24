package enemies;
import entity.Entity;
import main.GamePanel;
import object.ENE_OBJ_Bullet;
import object.OBJ_Ammo;

import java.util.Random;

public class ENE_Tank extends Entity{

    GamePanel gp;
    public ENE_Tank(GamePanel gp) {
        super(gp);
        this.gp = gp;
        scaleX = 1;
        scaleY = 2;
        name = "Tank";
        speed = 4;
        maxLife = 10;
        life = maxLife;
        type = 2;
        projectile = new ENE_OBJ_Bullet(gp);

        solidArea.x = 48;
        solidArea.y = 48;
        solidArea.width = 16;
        solidArea.height = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage()
    {
        int i = 3;

        up1 = setup("enemies/tank1",gp.tileSize * i,gp.tileSize * i*2);
        up2 = setup("enemies/tank1",gp.tileSize * i,gp.tileSize * i*2);
        down1 = setup("enemies/tank2",gp.tileSize *i,gp.tileSize * i*2);
        down2 = setup("enemies/tank2",gp.tileSize * i,gp.tileSize * i*2);
        left1 = setup("enemies/tankleft",gp.tileSize * i*2,gp.tileSize * i);
        left2 = setup("enemies/tankleft",gp.tileSize * i*2,gp.tileSize * i);
        right1 = setup("enemies/tankright",gp.tileSize * i*2,gp.tileSize * i);
        right2 = setup("enemies/tankright",gp.tileSize * i*2,gp.tileSize * i);
    }

    public void setAction()
    {
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
                scaleX = 1;
                scaleY = 2;
            }
            if (i > 25 && i <= 50) {
                direction = "down";
                scaleX = 1;
                scaleY = 2;
            }
            if (i > 50 && i <= 75) {
                direction = "left";
                scaleX = 2;
                scaleY = 1;
            }
            if (i > 75 && i <= 100) {
                direction = "right";
                scaleX = 2;
                scaleY = 1;
            }
            actionLockCounter = 0;


        }
        int i = new Random().nextInt(100) + 1;
        if(i > 99 && projectile.alive == false && shotAvailableCounter == 70)
        {
            projectile.set(worldX,worldY,direction,true,this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;

        }


    }

    public void damageReaction()
    {
        actionLockCounter = 0;
    }

    public void checkDrop()
    {
        //CAST A DIE
        int i = new Random().nextInt(100) + 1;

        if(i < 75)
        {
            dropItem(new OBJ_Ammo(gp));
        }


    }

}
