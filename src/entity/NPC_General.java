package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class NPC_General extends Entity{

    public NPC_General(GamePanel gp)
    {
        super(gp);
        direction = "down";
        speed = 1;
        getImage();
        setDialogue();
    }
    public void getImage() {
        try {

            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/russiannpcback1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/russiannpcback2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/russiannpcfront1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/russiannpcfront2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/russiannpcfront1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/russiannpcfront2.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/russiannpcfront1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("NPC/russiannpcfront2.png"));


        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void setDialogue(){

        dialogues[0] = " Dimitri tu esti? ";
        dialogues[1] = "Du te la HQ si ia rapid munitie.";
        dialogues[2] = "Nemtii sunt pe cale sa ne ia baza din SUD-EST, \n si sa ii impuste pe tovarasii nostri.";
        dialogues[3] = "Gaseste cufarul pentru a castiga";
        dialogues[4] ="Bafta soldat.";
    }

    public void setAction() {

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
                actionLockCounter =0;
            }

        }

        public void speak()
        {
            super.speak();
        }

    }
