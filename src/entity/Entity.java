package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {

    GamePanel gp;

    public int worldX, worldY;
    public int speed;

    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";

    public int scaleX = 1;
    public int scaleY  = 1;

    public int shotAvailableCounter = 0;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0,0,60,60);
    public int solidAreaDefaultX, solidAreaDefaultY;

    public Rectangle attackArea = new Rectangle(0,0,0,0);

    public boolean collisionOn = false;
    public int type; // 0 - player 1 - npc 2 - enemy
    public int actionLockCounter = 0;
    public boolean godmode = false;

    int dyingCounter = 0;
    public boolean alive = true;
    public boolean dying = false;

    public boolean hpBar = false;
    public int godmodeCounter = 0;
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    int hpBarCounter = 0;
    //Character Status

    boolean attacking = false;
    public int value = 0;
    public final int type_pickup0nly = 7;
    public int maxLife;
    public int life;




    public Projectile projectile;
    public int MaxMagazine;
    public int Clip;
    public int useCost;


    public Entity (GamePanel gp)
    {
        this.gp = gp;
    }


    public void setAction()
    {

    }


    public void damageReaction()
    {

    }

    public void checkDrop()
    {

    }

    public void dropItem(Entity droppedItem)
    {
            for(int i = 0; i < gp.obj[1].length; i++)
            {
                if(gp.obj[gp.currentMap][i] == null)
                {
                    gp.obj[gp.currentMap][i] = droppedItem;
                    gp.obj[gp.currentMap][i].worldX = worldX;
                    gp.obj[gp.currentMap][i].worldY = worldY;
                    break;
                }
            }
    }

    public void speak(){

        if(dialogues[dialogueIndex] == null)
        {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction)
        {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }

    }

    public void update()
    {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this,gp.npc);
        gp.cChecker.checkEntity(this,gp.enemies);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true)
        {
            damagePlayer(1);
        }

        if(collisionOn == false)
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
        if(godmode == true)
        {
            godmodeCounter++;
            if(godmodeCounter > 40)
            {
                godmode = false;
                godmodeCounter = 0;
            }
        }
        if(shotAvailableCounter < 70)
        {
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack)
    {
        if(gp.player.godmode == false)
        {
            gp.player.life -= attack;
            gp.player.godmode = true;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY)
        {
            switch(direction){
                case "up":
                    if(name == "Tank")
                    {
                        tempScreenY = screenY - gp.tileSize * scaleY;
                        if(spriteNum == 1)
                        {
                            image = up1;
                        }
                        if(spriteNum == 2)
                        {
                            image = up2;
                        }
                    }
                    if(spriteNum == 1)
                    {
                        image = up1;
                    }
                    if(spriteNum == 2)
                    {
                        image = up2;
                    }
                    break;
                case "down":

                    if(spriteNum == 1)
                    {
                        image = down1;
                    }
                    if(spriteNum == 2)
                    {
                        image = down2;
                    }
                    break;
                case "left":
                    if(name == "Tank") {
                        tempScreenX = screenX - gp.tileSize * scaleX;
                        if (spriteNum == 1) {
                            image = left1;
                        }
                        if (spriteNum == 2) {
                            image = left2;
                        }
                    }
                    if(spriteNum == 1)
                    {
                        image = left1;
                    }
                    if(spriteNum == 2)
                    {
                        image = left2;
                    }
                    break;
                case "right":
                    if(spriteNum == 1)
                    {
                        image = right1;
                    }
                    if(spriteNum == 2)
                    {
                        image = right2;
                    }
                    break;
            }

            //HP bar

            if(type == 2 && hpBar == true)
            {
                double oneScale = (double) gp.tileSize/maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX - 1 ,screenY - 16,gp.tileSize+2,12);
                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY - 15 , (int) hpBarValue, 10);

                hpBarCounter++;
                if(hpBarCounter > 600)
                {
                    hpBarCounter = 0;
                    hpBar = false;
                }

            }


            if(godmode == true)
            {
                hpBar = true;
                hpBarCounter = 0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f));
            }

            if(dying == true)
            {
                dyingAnimation(g2);
            }

            g2.drawImage(image, screenX ,screenY , gp.tileSize * scaleX, gp.tileSize * scaleY, null);



            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
        }
    }
    public void dyingAnimation(Graphics2D g2)
    {
        dyingCounter++;

        int i = 10;

        if(dyingCounter == 1)
            gp.randomPlay(6,9);

        if(dyingCounter <= i)
        {
            changeAlpha(g2,0f);

        }
        if(dyingCounter > i && dyingCounter <= i*2)
        {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*2 && dyingCounter <= i*3)
        {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*3 && dyingCounter <= i*4)
        {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*4 && dyingCounter <= i*5)
        {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*5 && dyingCounter <= i*6)
        {
            changeAlpha(g2,1f);
        }
        if(dyingCounter > i*6 && dyingCounter <= i*7)
        {
            changeAlpha(g2,0f);
        }
        if(dyingCounter > i*7 && dyingCounter <= i*8)
        {
            changeAlpha(g2,1f);
        }

        if(dyingCounter > i*8)
        {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue)
    {

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));

    }

    public BufferedImage setup(String imageName,int width,int height)
    {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream( imageName + ".png"));
            image = uTool.scaleImage(image,width,height);

        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }
}
