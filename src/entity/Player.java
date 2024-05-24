package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Bullet;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class Player extends Entity{

    private static Player instance = null;
    KeyHandler keyH;

    public int reloadCounter = 0;
    public boolean reloadDone = false;
    public boolean reloadProgress = false;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;


    private Player(GamePanel gp, KeyHandler keyH)
    {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = gp.tileSize - 23;
        solidArea.height = gp.tileSize - 23;

        attackArea.width = 36;
        attackArea.height = 36;


        setDefaultValues();
        setDefaultPosition();
        getPlayerImage();
    }

    public static Player createPlayer(GamePanel gp, KeyHandler keyH)
    {
        if(instance == null)
        {
            instance = new Player(gp,keyH);
        }
        return instance;
    }
    public void setDefaultValues()
    {
        worldX = gp.tileSize * 14; //23
        worldY = gp.tileSize * 10; //21
        speed = 4;
        direction  = "down";
        reloadProgress = false;
        maxLife = 4;
        life = maxLife;
        MaxMagazine = 16;
        Clip = 4;


        projectile = new OBJ_Bullet(gp);

    }

    public void setDefaultPosition()
    {
        if(gp.currentMap == 0) {
            worldX = gp.tileSize * 10; //23
            worldY = gp.tileSize * 8; //21
        }
        else if(gp.currentMap == 1)
        {
            worldX = gp.tileSize * 13;
            worldY = gp.tileSize * 9;
        }
        else if(gp.currentMap == 2)
        {
            worldX  = gp.tileSize *30;
            worldY = gp.tileSize * 40;
        }
        direction = "down";
    }

    public void restoreLifeandAmmo()
    {
        life = maxLife;
        Clip = 4;
        godmode = false;

    }

    public void getPlayerImage()
    {

        if(gp.currentMap == 0)
        {
            up1 = setup("player/RussianSoldierback2",gp.tileSize,gp.tileSize);
            up2 = setup("player/RussianSoldierback1",gp.tileSize,gp.tileSize);
            down1 = setup("player/RussianSoldierfront1",gp.tileSize,gp.tileSize);
            down2 = setup("player/RussianSoldierfront2",gp.tileSize,gp.tileSize);
            left1 = setup("player/RussianSoldierleft1",gp.tileSize,gp.tileSize);
            left2 = setup("player/RussianSoldierleft2",gp.tileSize,gp.tileSize);
            right1 = setup("player/RussianSoldierright1",gp.tileSize,gp.tileSize);
            right2 = setup("player/RussianSoldierright2",gp.tileSize,gp.tileSize);
        }
        else if(gp.currentMap == 1)
        {
            up1 = setup("player/britishback1",gp.tileSize,gp.tileSize);
            up2 = setup("player/britishback2",gp.tileSize,gp.tileSize);
            down1 = setup("player/britishfront1",gp.tileSize,gp.tileSize);
            down2 = setup("player/britishfront2",gp.tileSize,gp.tileSize);
            left1 = setup("player/britishleft1",gp.tileSize,gp.tileSize);
            left2 = setup("player/britishleft2",gp.tileSize,gp.tileSize);
            right1 = setup("player/britishright1",gp.tileSize,gp.tileSize);
            right2 = setup("player/britishright2",gp.tileSize,gp.tileSize);
        }
        else if(gp.currentMap == 2)
        {
            up1 = setup("player/ussoldierback1",gp.tileSize,gp.tileSize);
            up2 = setup("player/ussoldierback2",gp.tileSize,gp.tileSize);
            down1 = setup("player/ussoldierfront1",gp.tileSize,gp.tileSize);
            down2 = setup("player/ussoldierfront2",gp.tileSize,gp.tileSize);
            left1 = setup("player/ussoldierleft1",gp.tileSize,gp.tileSize);
            left2 = setup("player/ussoldierleft2",gp.tileSize,gp.tileSize);
            right1 = setup("player/ussoldierright1",gp.tileSize,gp.tileSize);
            right2 = setup("player/ussoldierright2",gp.tileSize,gp.tileSize);
        }



    }

   /* public BufferedImage setup(String imageName)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/" + imageName + ".png"));
            image = uTool.scaleImage(image,gp.tileSize,gp.tileSize);

        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }
*/

    public void update()
    {


        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.spacePressed == true)
        {
            if(keyH.upPressed == true)
            {
                direction = "up";
            }
            else if(keyH.downPressed == true) {
                direction = "down";
            }
            else if(keyH.leftPressed == true)
            {
                direction = "left";
            }
            else if(keyH.rightPressed == true)
            {
                direction = "right";
            }
            //collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check
            int objIndex = gp.cChecker.checkObject(this,true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            int enemyIndex = gp.cChecker.checkEntity(this,gp.enemies);
            contactEnemy(enemyIndex);




            if(collisionOn == false && keyH.spacePressed == false)
            {
                switch(direction){
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            gp.keyH.spacePressed = false;

            spriteCounter++;
            if(spriteCounter > 15)
            {
                if(spriteNum == 1)
                {
                    spriteNum = 2;
                }
                else if (spriteNum == 2)
                {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if(godmode == true)
        {
            godmodeCounter++;
            if(godmodeCounter > 60)
            {
                godmode = false;
                godmodeCounter = 0;
            }
        }

        if(life <= 0)
        {
            gp.stopMusic();
            gp.gameState = gp.gameOverState;
            gp.playSE(14);
        }
        if(gp.gameState == gp.victoryState)
        {
            gp.stopMusic();
            gp.playMusic(15);
        }
        if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 70 && projectile.haveResource(this) == true && reloadProgress == false)
        {
            gp.playSE(11);
            shotAvailableCounter = 0;
            projectile.set(worldX,worldY,direction,true,this);

            projectile.subtractResource(this);

            gp.projectileList.add(projectile);

        }

        if(Clip < 4 && MaxMagazine > 0) {
            if (gp.keyH.reloadKeyPressed == true) {
                if (reloadDone == false) {
                    reloadProgress = true;
                }
                if (reloadCounter == 0 && Clip != 4) {
                    gp.playSE(12);
                }
            }
            if (reloadProgress == true) {
                reloadCounter++;
            }

            if (reloadCounter == 120) {
                reloadProgress = false;
                reloadDone = true;
            }

            if (reloadDone == true) {
                while (Clip != 4 && MaxMagazine > 0) {
                    MaxMagazine--;
                    Clip++;
                }
                reloadDone = false;
                reloadProgress = false;
                reloadCounter = 0;
            }
        }
        if(shotAvailableCounter < 70)
        {
            shotAvailableCounter++;
        }
    }


    public void pickUpObject(int i)
    {
        if(i != 999)
        {
            String objectName = gp.obj[gp.currentMap][i].name;

            switch(objectName)
            {
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[gp.currentMap][i] = null;
                    gp.ui.showMessage("Key Taken!");
                    break;
                case "Door":
                    if(hasKey > 0)
                    {
                        gp.obj[gp.currentMap][i] = null;
                        gp.playSE(2);
                        hasKey--;
                    }
                    break;
                case "Chest":
                    gp.gameState = gp.victoryState;
                    gp.stopMusic();
                    break;
                case "Ammo":
                    gp.playSE(13);
                    gp.player.MaxMagazine += 8;
                    gp.obj[gp.currentMap][i] = null;
                    break;
            }
        }
    }

    public void interactNPC(int i)
    {
        if(gp.keyH.spacePressed == true)
        {
            if(i != 999)
            {
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }

    public void contactEnemy(int i)
    {
        if(i != 999)
        {
            if(godmode == false && gp.enemies[gp.currentMap][i].dying == false) {
                life -= 1;
                godmode = true;
            }

        }
    }

    public void damageEnemy(int i,int attack)
    {
        if(i != 999)
        {
            if(gp.enemies[gp.currentMap][i].godmode == false)
            {
                gp.enemies[gp.currentMap][i].life -= attack;
                gp.playSE(10);
                gp.enemies[gp.currentMap][i].godmode = true;
                gp.enemies[gp.currentMap][i].damageReaction();
                if(gp.enemies[gp.currentMap][i].life <= 0)
                {
                    gp.enemies[gp.currentMap][i].dying = true;
                }
            }

        }
        else{
            System.out.println("MISS");
        }

    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;



        switch(direction){
            case "up":
                if(attacking == false){
                    if(spriteNum == 1)
                    {
                        image = up1;
                    }
                    if(spriteNum == 2)
                    {
                        image = up2;
                    }
                }
                else if(attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if(spriteNum == 1)
                    {
                        image = attackUp1;
                    }
                    if(spriteNum == 2)
                    {
                        image = attackUp2;
                    }
                }

                break;
            case "down":
                if(attacking == false){
                    if(spriteNum == 1)
                    {
                        image = down1;
                    }
                    if(spriteNum == 2)
                    {
                        image = down2;
                    }
                }
                else if(attacking == true) {
                    if(spriteNum == 1)
                    {
                        image = attackDown1;
                    }
                    if(spriteNum == 2)
                    {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if(attacking == false){
                    if(spriteNum == 1)
                    {
                        image = left1;
                    }
                    if(spriteNum == 2)
                    {
                        image = left2;
                    }
                }
                else if(attacking == true) {
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1)
                    {
                        image = attackLeft1;
                    }
                    if(spriteNum == 2)
                    {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if(attacking == false){
                    if(spriteNum == 1)
                    {
                        image = right1;
                    }
                    if(spriteNum == 2)
                    {
                        image = right2;
                    }
                }
                else if(attacking == true) {
                    if(spriteNum == 1)
                    {
                        image = attackRight1;
                    }
                    if(spriteNum == 2)
                    {
                        image = attackRight2;
                    }
                }
                break;
        }

        if(godmode == true)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY,null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
