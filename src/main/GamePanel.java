package main;
import DataBase.DatabaseManager;
import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import static entity.Player.createPlayer;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings

    final int originalTileSize = 16; // 16x16 tile

    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48

    public final int maxScreenCol = 20; //32

    public final int maxScreenRow = 12; //18

    public final int screenWidth = tileSize * maxScreenCol; // 960 pixels

    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public final int maxMap = 4;

    public int currentMap = 0;



    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;

    Graphics2D g2;


    public DatabaseManager dbmng = new DatabaseManager("DB",this);

    int FPS = 60;

    TileManager tileM = new TileManager(this);

    public KeyHandler keyH = new KeyHandler(this);

    Sound se = new Sound();

    Sound music = new Sound();


    public CollisionChecker cChecker = new CollisionChecker(this);

    public AssetSetter aSetter = new AssetSetter(this);

    public UI ui = new UI(this);
    public float alpha = 0;
    public float alpha2 = 0;



    Thread gameThread;

    public Player player = createPlayer(this, keyH);

    public Entity[][] obj = new Entity[maxMap][10];

    public Entity[][] npc = new Entity[maxMap][10];

    public Entity[][] enemies = new Entity[maxMap][20];
    public ArrayList<Entity> entityList = new ArrayList<>();

    public ArrayList<Entity> projectileList = new ArrayList<>();

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public int gameOverState = 4;

    public final int victoryState = 5;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setEnemy();
        playMusic(3);
        gameState = titleState;

    }


    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

        public void loadData() {

            // Player Settings
            String tableName = "PlayerSave";

            // Extragere date din tabel
            dbmng.selectPlayerTable(tableName);
            gameState = playState;
        }

    public void saveData() {

        // Entities
        String monsters = "";
        for(int i = 0; i < maxMap; ++i) {
            for(int j = 0; j < enemies[i].length; ++j) {
                if(enemies[i][j] != null) {
                    monsters += enemies[i][j].worldX + ", " + enemies[i][j].worldY + ", " + enemies[i][j].life + ", ";
                } else {
                    monsters += -1 + ", " + -1 + ", " + -1 + ", ";
                }
            }
        }
        if(!monsters.isEmpty()) {
            monsters = monsters.substring(0, monsters.length() - 2);
        }

        String npct = "";
        for(int i = 0; i < maxMap; ++i) {
            for(int j = 0; j < npc[i].length; ++j) {
                if(npc[i][j] != null) {
                    npct += npc[i][j].worldX + ", " + npc[i][j].worldY + ", ";
                } else {
                    npct += -1 + ", " + -1 + ", ";
                }
            }
        }

        if(!npct.isEmpty()) {
            npct = npct.substring(0, npct.length() - 2);
        }

        String objects = "";
        for(int i = 0; i < maxMap; ++i) {
            for(int j = 0; j < obj[i].length; ++j) {
                if(obj[i][j] != null) {
                    objects += obj[i][j].worldX + ", " + obj[i][j].worldY + ", ";
                } else {
                    objects += -1 + ", " + -1 + ", ";
                }
            }
        }
        if(!objects.isEmpty()) {
            objects = objects.substring(0, objects.length() - 2);
        }



        // Player Settings
        String tableName = "PlayerSave";

        // Creare tabel daca nu exista
        ArrayList<String> fields = new ArrayList<>();
        fields.add("PLAYERPOSX");
        fields.add("TEXT");
        fields.add("PLAYERPOSY");
        fields.add("TEXT");
        fields.add("CURRENTMAP");
        fields.add("TEXT");
        fields.add("DIRECTION");
        fields.add("TEXT");
        fields.add("LIFE");
        fields.add("TEXT");
        fields.add("MONSTERS");
        fields.add("TEXT");
        fields.add("NPC");
        fields.add("TEXT");
        fields.add("OBJECTS");
        fields.add("TEXT");

        dbmng.createPlayerTable(tableName, fields);

        // Adaugare date in tabel
        fields.clear();
        fields.add("PLAYERPOSX");
        fields.add("PLAYERPOSY");
        fields.add("CURRENTMAP");
        fields.add("DIRECTION");
        fields.add("LIFE");
        fields.add("MONSTERS");
        fields.add("NPC");
        fields.add("OBJECTS");

        ArrayList<String> values = new ArrayList<>();
        values.add(String.valueOf(player.worldX));
        values.add(String.valueOf(player.worldY));
        values.add(String.valueOf(currentMap));
        values.add(player.direction);
        values.add(String.valueOf(player.life));
        values.add(monsters);
        values.add(npct);
        values.add(objects);
        dbmng.insertPlayerTable(tableName, fields, values);

    }


    public void restart()
    {
        player.setDefaultValues();
        player.setDefaultPosition();
        player.restoreLifeandAmmo();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setEnemy();

    }
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while (gameThread != null) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void update() {
        if (gameState == playState) {
            player.update();
            for (int i = 0; i < npc[1].length; i++)
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }

            for(int i = 0; i < enemies[1].length;i++)
            {
                if(enemies[currentMap][i] != null)
                {
                    if(enemies[currentMap][i].alive == true && enemies[currentMap][i].dying == false){
                        enemies[currentMap][i].update();
                    }
                    if(enemies[currentMap][i].alive == false){
                        enemies[currentMap][i].checkDrop();
                        enemies[currentMap][i] = null;
                    }
                }
            }

            for(int i = 0; i < projectileList.size();i++)
            {
                if(projectileList.get(i) != null)
                {
                    if(projectileList.get(i).alive == true)
                    {
                        projectileList.get(i).update();
                    }
                    if(projectileList.get(i).alive == false)
                    {
                        projectileList.remove(i);
                    }
                }
            }
        }
        if (gameState == pauseState) {

        }

        if(gameState == gameOverState)
        {
            alpha += 0.005;
            if(alpha > 0.8) {
                alpha2 += 0.006;
                if(alpha2 > 1.3) {
                    gameState = titleState;
                    ui.titleScreenState = 0;
                    player.life = 2;
                    playMusic(3);
                    alpha = 0;
                    alpha2 = 0;
                    restart();
                }

            }

        }
    }

    public void paintComponent(Graphics g)
    {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            //debug

            if(gameState == titleState)
            {
                ui.draw(g2);
            }
            else {
                tileM.draw(g2);

                //add entities
                entityList.add(player);

                for(int i = 0; i < npc[1].length; i++)
                {
                    if(npc[currentMap][i] != null)
                    {
                        entityList.add(npc[currentMap][i]);
                    }
                }

                for(int i = 0; i < enemies[1].length; i++)
                {
                    if(enemies[currentMap][i] != null)
                    {
                        entityList.add(enemies[currentMap][i]);
                    }
                }

                for(int i = 0; i < obj[1].length; i++)
                {
                    if(obj[currentMap][i] != null)
                    {
                        entityList.add(obj[currentMap][i]);
                    }
                }

                for(int i = 0; i < projectileList.size(); i++)
                {
                    if(projectileList.get(i) != null)
                    {
                        entityList.add(projectileList.get(i));
                    }
                }

                Collections.sort(entityList, new Comparator<Entity>() {
                    @Override
                    public int compare(Entity e1, Entity e2) {

                        int result = Integer.compare(e1.worldY, e2.worldY);
                        return result;
                    }
                });

                //draw entities
                for(int i = 0; i < entityList.size(); i++)
                {
                    entityList.get(i).draw(g2);
                }


                entityList.clear();


                ui.draw(g2);

                if(keyH.showDebugText == true)
                {
                    g2.setFont(new Font("Arial",Font.PLAIN,20));
                    g2.setColor(Color.white);
                    int x = 10;
                    int y = 400;
                    int lineHeight = 20;

                    g2.drawString("WorldX" + player.worldX, x, y);
                    y += lineHeight;
                    g2.drawString("WorldY" + player.worldY, x, y);
                    y += lineHeight;
                    g2.drawString("Col" + (player.worldX+ player.solidArea.x)/tileSize, x, y);
                    y += lineHeight;
                    g2.drawString("Row" + (player.worldY + player.solidArea.y)/tileSize,x,y );
                }
            }

                g2.dispose();
            }



    public void playMusic(int i )
    {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic()
    {
        music.stop();
    }

    public void playSE(int i)
    {
        se.setFile(i);
        se.play();

    }

    public void randomPlay(int a, int b)
    {
        Random rand = new Random();
        playSE(rand.nextInt(a,b+1));
    }

}
