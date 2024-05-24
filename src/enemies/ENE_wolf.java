package enemies;
import entity.Entity;
import main.GamePanel;
import object.ENE_OBJ_Bullet;
import object.OBJ_Ammo;

import java.util.Random;

public class ENE_wolf extends Entity{

    GamePanel gp;
    public ENE_wolf(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Wolf";
        speed = 3;
        maxLife = 4;
        life = maxLife;
        type = 2;
        projectile = new ENE_OBJ_Bullet(gp);

        /*solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        CUSTOM COLLISION AREA

        */
        getImage();
    }

    public void getImage()
    {
        up1 = setup("enemies/wolfback1",64,64);
        up2 = setup("enemies/wolfback2",64,64);
        down1 = setup("enemies/wolffront1",64,64);
        down2 = setup("enemies/wolffront2",64,64);
        left1 = setup("enemies/wolfleft1",64,64);
        left2 = setup("enemies/wolfleft2",64,64);
        right1 = setup("enemies/wolfright1",64,64);
        right2 = setup("enemies/wolfright2",64,64);
    }

    public void setAction()
    {
        actionLockCounter++;

        if(actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
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
