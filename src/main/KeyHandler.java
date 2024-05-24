package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, shotKeyPressed,reloadKeyPressed,enterPressed;

    boolean showDebugText = false;

    GamePanel gp;

    public KeyHandler(GamePanel gp)
    {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(gp.gameState == gp.titleState)
        {

            if(gp.ui.titleScreenState == 0)
            {
                if(code == KeyEvent.VK_W)
                {
                    gp.ui.commandNum--;
                    gp.playSE(4);
                    if(gp.ui.commandNum < 0)
                    {
                        gp.ui.commandNum = 3;
                    }
                }
                if(code == KeyEvent.VK_S)
                {
                    gp.playSE(4);
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 3)
                    {
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER)
                {
                    gp.playSE(5);
                    if(gp.ui.commandNum == 0)
                    {
                        gp.gameState = gp.playState;
                        gp.stopMusic();
                        gp.playMusic(0);
                    }
                    if(gp.ui.commandNum == 1)
                    {
                        gp.ui.titleScreenState = 1;
                        enterPressed = false;
                    }
                    if(gp.ui.commandNum ==2)
                    {
                        gp.loadData();
                        gp.gameState =gp.playState;
                    }
                    if(gp.ui.commandNum == 3)
                    {
                        System.exit(0);
                    }

                }
            }
            if(gp.ui.titleScreenState == 1)
            {
                if(code == KeyEvent.VK_W)
                {
                    gp.playSE(4);
                    enterPressed = true;

                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0)
                    {
                        gp.ui.commandNum = 3;
                    }
                }
                if(code == KeyEvent.VK_S)
                {
                    gp.playSE(4);
                    enterPressed = true;

                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 3)
                    {
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER && enterPressed == true) // problema
                {
                    gp.playSE(5);
                    if(gp.ui.commandNum == 0) //Eastern
                    {
                        gp.currentMap = 0;
                        gp.player.getPlayerImage();
                        gp.player.setDefaultPosition();
                        gp.gameState = gp.playState;
                        gp.stopMusic();
                        gp.playMusic(0);
                    }
                    if(gp.ui.commandNum == 1) //African
                    {
                        gp.currentMap = 1;
                        gp.player.getPlayerImage();
                        gp.player.setDefaultPosition();
                        gp.gameState = gp.playState;
                        gp.stopMusic();
                        gp.playMusic(0);

                    }
                    if(gp.ui.commandNum == 2) //
                    {
                        gp.currentMap = 2;
                        gp.player.getPlayerImage();
                        gp.player.setDefaultPosition();
                        gp.gameState = gp.playState;
                        gp.stopMusic();
                        gp.playMusic(0);
                    }
                    if(gp.ui.commandNum == 3)
                    {
                        gp.ui.titleScreenState = 0;
                    }

                }
            }
        }

        if(gp.gameState == gp.playState)
        {
            if(code == KeyEvent.VK_W)
            {
                upPressed = true;
            }
            if(code == KeyEvent.VK_S)
            {
                downPressed = true;
            }
            if(code == KeyEvent.VK_A)
            {
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D)
            {
                rightPressed = true;
            }
            if(code == KeyEvent.VK_SPACE)
            {
                spacePressed = true;
            }
            if(code == KeyEvent.VK_F)
            {
                shotKeyPressed = true;
            }

            if(code == KeyEvent.VK_T)
            {
                if(showDebugText == false)
                {
                    showDebugText = true;
                }
                else if(showDebugText == true)
                {
                    showDebugText = false;
                }
            }

            if(code == KeyEvent.VK_R)
            {
                reloadKeyPressed = true;
            }

        }
        if(code == KeyEvent.VK_ESCAPE) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            }
            else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }

        if(gp.gameState == gp.pauseState)
        {
            if(code == KeyEvent.VK_ENTER)
            {
                enterPressed = true;
            }

            int maxCommandNum = 0;

            switch(gp.ui.subState){
                case 0: maxCommandNum = 3; break;
            }

            if(code == KeyEvent.VK_W)
            {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0)
                {
                    gp.ui.commandNum = 2;
                }
            }

            if(code == KeyEvent.VK_S)
            {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2)
                {
                    gp.ui.commandNum = 0;
                }

            }

            if(code == KeyEvent.VK_P)
            {
                gp.restart();
                gp.gameState = gp.titleState;
            }
        }

        if(gp.gameState == gp.dialogueState)
        {
            if(code == KeyEvent.VK_SPACE)
            {
                gp.gameState = gp.playState;
            }
        }

        if(gp.gameState == gp.victoryState)
        {
            if(code == KeyEvent.VK_F)
            {
                gp.stopMusic();
                gp.playMusic(3);
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W)
        {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S)
        {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A)
        {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_F)
        {
            shotKeyPressed = false;
        }
        if(code == KeyEvent.VK_R)
        {
            reloadKeyPressed = false;
        }
        if(code == KeyEvent.VK_ENTER)
        {
            enterPressed = false;
        }
    }
}
