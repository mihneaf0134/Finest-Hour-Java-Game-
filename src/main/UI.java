package main;

import entity.Entity;
import object.OBJ_Clip;
import object.OBJ_Heart;
import object.OBJ_key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;

    Graphics2D g2;

    Font arial_40, arial_80B;

    BufferedImage keyImage, heart_full, heart_half, heart_blank, uigun;


    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;

    double playTime = 60;
    DecimalFormat dFormat = new DecimalFormat("#0");

    int subState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Agency FB", Font.BOLD, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_key key = new OBJ_key(gp);
        keyImage = key.image;

        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity clip = new OBJ_Clip(gp);
        uigun = clip.image;



    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        if(gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }


        if (gp.gameState == gp.playState) {
            if (gameFinished == true) {
                g2.setFont(arial_40);
                g2.setColor(Color.WHITE);
                String text;
                int textLength;
                int x;
                int y;

                g2.setFont(arial_80B);
                g2.setColor(Color.red);

                text = "YOU WON";
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

                x = gp.screenWidth / 2 - textLength / 2;
                y = gp.screenHeight / 2 - (gp.tileSize * 3);
                g2.drawString(text, x, y);

                gp.gameThread = null;
            }

            //HUD

            drawPlayerLife();


            //HUD
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            //g2.drawImage(keyImage, gp.tileSize, gp.tileSize, gp.tileSize, gp.tileSize, null);
           // g2.drawString("x " + gp.player.hasKey, 74, 50);


            //playTime -= (double) 1 / 60;
            //g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);

            if (messageOn == true) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, 150, 100);
                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

        if(gp.gameState == gp.pauseState)
        {
            drawOptionsScreen();
        }

        if(gp.gameState == gp.dialogueState)
        {
            drawDialogueScreen();
        }

        if(gp.gameState == gp.gameOverState)
        {
            drawGameOverScreen();
        }
        if(gp.gameState == gp.victoryState)
        {
            drawVictoryScreen();
            if(gp.keyH.shotKeyPressed == true)
            {
                gp.restart();
                gp.gameState = gp.titleState;
            }
        }

    }

    public void drawVictoryScreen()
    {
        BufferedImage[] backgroundImage = new BufferedImage[10];

        try {
            backgroundImage[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Victory/img.png"));
            g2.drawImage(backgroundImage[0],0,0,960,576,null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.setColor(Color.YELLOW);
        g2.drawString("Press F to continue", 500,500);

    }

    public void drawOptionsScreen()
    {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 9;
        int frameHeight = gp.tileSize * 11;

        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        switch(subState)
        {
            case 0: options_top(frameX,frameY);break;
            case 1: options_control(frameX,frameY);break;
            case 2: break;
        }
    }

    public void options_top(int frameX,int frameY)
    {

        int textX;
        int textY;

        String text = "Options";
        textX = getXforCenteredText(text) - 40;
        textY = frameY + gp.tileSize;
        g2.drawString(text,textX,textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 3;

        g2.drawString("Controls",textX,textY);
        if(commandNum == 0)
        {
            g2.drawString(">", textX-30, textY);
            if(gp.keyH.enterPressed == true)
            {
                subState = 1;
                commandNum = 1;
            }
        }

        textY += gp.tileSize * 2;
        g2.drawString("Save Game",textX,textY);
        if(commandNum == 1)
        {
            g2.drawString(">", textX-30, textY);
            if(gp.keyH.enterPressed == true)
            {
                gp.saveData();
            }
        }

        textY += gp.tileSize * 2 + 24;
        g2.setFont(g2.getFont().deriveFont(24F));
        g2.drawString("Back to Main Menu", textX,textY);
        if(commandNum == 2)
        {
            g2.drawString(">", textX-30, textY);
            if(gp.keyH.enterPressed == true)
            {
                gp.restart();
                gp.gameState = gp.titleState;
            }
        }


    }

    public void options_control(int frameX, int frameY)
    {
            int textY;
            int textX;

            String text = "Controls";
            textX = getXforCenteredText(text);
            textY = frameY + gp.tileSize;
            g2.drawString(text,textX,textY);
            textX = frameX + gp.tileSize;
            textY += gp.tileSize;
            g2.drawString("Move    W/A/S/D", textX,textY); textY +=gp.tileSize+20;
            g2.drawString("Fire       F", textX,textY); textY +=gp.tileSize+20;
            g2.drawString("Reload     R", textX,textY); textY +=gp.tileSize+20;
            g2.drawString("Interact  Space", textX,textY); textY +=gp.tileSize+140;
            g2.drawString("Back", textX,textY);

            if(commandNum == 0 || commandNum == 2)
            {
                g2.drawString(">", textX - 25, textY);
                if(gp.keyH.enterPressed == true)
                {
                    subState = 0;
                    commandNum = 1;
                }
            }

    }

    private void drawGameOverScreen() {

        String text1 = "You have died.";
        String text2 = "It is foolish and wrong to mourn the men who died.";
        String text3 = "Rather, we should thank God that such men lived.";
        String text4 = "- George S. Patton Jr.";

        // Fill the screen with a black background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setColor(Color.WHITE);

        // Set the transparency level for the text
        if(gp.alpha <= 1) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, gp.alpha));
        }
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
        g2.drawString(text1, gp.screenWidth/3 - 10, gp.screenHeight/5);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
        if(gp.alpha2 <= 1) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, gp.alpha2));
        }
        g2.drawString(text2,gp.screenWidth/4-120,gp.screenHeight - 300);
        g2.drawString(text3,gp.screenWidth/4-120,gp.screenHeight - 250);
        g2.drawString(text4,gp.screenWidth/4 + 80,gp.screenHeight- 150);



    }

    public void drawPlayerLife()
    {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        while(i < gp.player.maxLife/2)
        {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        //reset
         x = gp.tileSize/2;
         y = gp.tileSize/2;
         i = 0;

         while(i < gp.player.life)
         {
             g2.drawImage(heart_half,x, y, null);
             i++;
             if(i < gp.player.life)
             {
                 g2.drawImage(heart_full,x, y, null);
             }
             i++;
             x += gp.tileSize;
         }

         //Gun UI

        x = gp.tileSize/2;
         y = gp.tileSize*2;
         g2.drawImage(uigun,x,y,null);
         g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(20F));
        g2.drawString("Mosin-Nagant \n" + gp.player.Clip + " / " + gp.player.MaxMagazine,x, y + gp.tileSize * 2);

        g2.setColor(Color.RED);
        if(gp.player.Clip == 0 && gp.player.reloadProgress == false && gp.player.MaxMagazine > 0)
        {
            g2.drawString("Press R to Reload",x,y + gp.tileSize * 3);
        }

        if(gp.player.reloadProgress == true)
        {
            g2.drawString("Reloading...",x,y + gp.tileSize * 3);
        }


    }

    public int ok = 0;

    public void drawTitleScreen() {

        if(titleScreenState == 0)
        {
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0,0,gp.screenWidth,gp.screenHeight);


            //Fill here the code necessary to display a background image
            BufferedImage[] backgroundImage = new BufferedImage[10];

            try {
                backgroundImage[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("titlescreen/men.png"));
                g2.drawImage(backgroundImage[0],0,0,960,576,null);
                backgroundImage[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("titlescreen/FinestHour.png"));
                g2.drawImage(backgroundImage[1],75,50,413*2,53*2,null);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN,96F));
            String text = "";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*3;

            g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

            text = "New Game";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.setColor(Color.BLACK);
            g2.drawString(text,x+5,y+5);
            g2.setColor(Color.WHITE);
            g2.drawString(text,x,y);

            if(commandNum == 0)
            {
                g2.drawString(">", x-gp.tileSize,y);
            }

            text = "Mission Select";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x,y);
            if(commandNum == 1)
            {
                g2.drawString(">", x-gp.tileSize,y);
            }


            text = "Load Save";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x,y);
            if(commandNum == 2)
            {
                g2.drawString(">", x-gp.tileSize,y);
            }


            text = "Exit";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x,y);

            if(commandNum == 3)
            {
                g2.drawString(">", x-gp.tileSize,y);
            }
        }
        else if (titleScreenState == 1)
        {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "1. Eastern Campaign";
            int x = getXforCenteredText(text);
            int y = gp.tileSize*2;
            g2.drawString(text,x,y);
            if(commandNum == 0)
            {
                g2.drawString(">", x-gp.tileSize, y);
            }
            text = "2. African Campaign";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x,y);
            if(commandNum == 1)
            {
                g2.drawString(">", x-gp.tileSize, y);
            }
            text = "3. Western Campaign";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text,x,y);
            if(commandNum == 2)
            {
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*3;
            g2.drawString(text,x,y);

            if(commandNum == 3)
            {
                g2.drawString(">", x-gp.tileSize, y);
            }
        }

    }

    public void drawDialogueScreen()
    {
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize * 5;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.TRUETYPE_FONT, 40F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n"))
        {
            g2.drawString(line,x,y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height)
    {
        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    public int getXforCenteredText(String text)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 -length/2;
        return x;
    }
}
