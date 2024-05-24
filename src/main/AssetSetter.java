package main;

import enemies.ENE_Tank;
import enemies.ENE_wolf;
import entity.NPC_General;
import object.OBJ_Ammo;
import object.OBJ_chest;
import object.OBJ_door;
import object.OBJ_key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp)
    {
        this.gp = gp;
    }

    public void setObject(){

        int mapNum = 0;

        gp.obj[mapNum][1] = new OBJ_chest(gp);
        gp.obj[mapNum][1].worldX = 35 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 15* gp.tileSize;

        gp.obj[mapNum][4] = new OBJ_Ammo(gp);
        gp.obj[mapNum][4].worldX = 19 * gp.tileSize;
        gp.obj[mapNum][4].worldY = 8 * gp.tileSize;

        mapNum = 1;
        gp.obj[mapNum][1] = new OBJ_chest(gp);
        gp.obj[mapNum][1].worldX = 14 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 42* gp.tileSize;

        mapNum = 2;

        gp.obj[mapNum][1] = new OBJ_chest(gp);
        gp.obj[mapNum][1].worldX = 35 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 15* gp.tileSize;

    }

    public void setNPC()
    {
        int mapNum = 0;

        gp.npc[mapNum][0] = new NPC_General(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize*19;
        gp.npc[mapNum][0].worldY = gp.tileSize*9;

        mapNum = 1;

    }

    public void setEnemy()
    {
        int mapNum = 0;

        gp.enemies[mapNum][0] = new ENE_wolf(gp);
        gp.enemies[mapNum][0].worldX = gp.tileSize*25;
        gp.enemies[mapNum][0].worldY = gp.tileSize*25;


        gp.enemies[mapNum][1] = new ENE_wolf(gp);
        gp.enemies[mapNum][1].worldX = gp.tileSize*23;
        gp.enemies[mapNum][1].worldY = gp.tileSize*27;

        gp.enemies[mapNum][2] = new ENE_wolf(gp);
        gp.enemies[mapNum][2].worldX = gp.tileSize*26;
        gp.enemies[mapNum][2].worldY = gp.tileSize*29;

        gp.enemies[mapNum][3] = new ENE_wolf(gp);
        gp.enemies[mapNum][3].worldX = gp.tileSize*29;
        gp.enemies[mapNum][3].worldY = gp.tileSize*28;

        gp.enemies[mapNum][4] = new ENE_wolf(gp);
        gp.enemies[mapNum][4].worldX = gp.tileSize*33;
        gp.enemies[mapNum][4].worldY = gp.tileSize*25;

        gp.enemies[mapNum][5] = new ENE_wolf(gp);
        gp.enemies[mapNum][5].worldX = gp.tileSize*37;
        gp.enemies[mapNum][5].worldY = gp.tileSize*37;

        gp.enemies[mapNum][6] = new ENE_wolf(gp);
        gp.enemies[mapNum][6].worldX = gp.tileSize*38;
        gp.enemies[mapNum][6].worldY = gp.tileSize*31;

        gp.enemies[mapNum][7] = new ENE_wolf(gp);
        gp.enemies[mapNum][7].worldX = gp.tileSize*38;
        gp.enemies[mapNum][7].worldY = gp.tileSize*21;

        gp.enemies[mapNum][8] = new ENE_wolf(gp);
        gp.enemies[mapNum][8].worldX = gp.tileSize*35;
        gp.enemies[mapNum][8].worldY = gp.tileSize*18;

        mapNum = 1;

        gp.enemies[mapNum][0] = new ENE_wolf(gp);
        gp.enemies[mapNum][0].worldX = gp.tileSize*20;
        gp.enemies[mapNum][0].worldY = gp.tileSize*30;

        gp.enemies[mapNum][1] = new ENE_wolf(gp);
        gp.enemies[mapNum][1].worldX = gp.tileSize*30;
        gp.enemies[mapNum][1].worldY = gp.tileSize*40;

        gp.enemies[mapNum][2] = new ENE_wolf(gp);
        gp.enemies[mapNum][2].worldX = gp.tileSize*35;
        gp.enemies[mapNum][2].worldY = gp.tileSize*36;

        gp.enemies[mapNum][3] = new ENE_wolf(gp);
        gp.enemies[mapNum][3].worldX = gp.tileSize*40;
        gp.enemies[mapNum][3].worldY = gp.tileSize*32;

        gp.enemies[mapNum][4] = new ENE_wolf(gp);
        gp.enemies[mapNum][4].worldX = gp.tileSize*42;
        gp.enemies[mapNum][4].worldY = gp.tileSize*25;

        gp.enemies[mapNum][5] = new ENE_wolf(gp);
        gp.enemies[mapNum][5].worldX = gp.tileSize*43;
        gp.enemies[mapNum][5].worldY = gp.tileSize*21;

        gp.enemies[mapNum][6] = new ENE_Tank(gp);
        gp.enemies[mapNum][6].worldX = gp.tileSize*38;
        gp.enemies[mapNum][6].worldY = gp.tileSize*28;

        gp.enemies[mapNum][7] = new ENE_Tank(gp);
        gp.enemies[mapNum][7].worldX = gp.tileSize*39;
        gp.enemies[mapNum][7].worldY = gp.tileSize*37;

        mapNum = 2;

        gp.enemies[mapNum][0] = new ENE_wolf(gp);
        gp.enemies[mapNum][0].worldX = gp.tileSize*20;
        gp.enemies[mapNum][0].worldY = gp.tileSize*30;

        gp.enemies[mapNum][1] = new ENE_wolf(gp);
        gp.enemies[mapNum][1].worldX = gp.tileSize*31;
        gp.enemies[mapNum][1].worldY = gp.tileSize*31;

        gp.enemies[mapNum][2] = new ENE_wolf(gp);
        gp.enemies[mapNum][2].worldX = gp.tileSize*34;
        gp.enemies[mapNum][2].worldY = gp.tileSize*12;

        gp.enemies[mapNum][3] = new ENE_wolf(gp);
        gp.enemies[mapNum][3].worldX = gp.tileSize*36;
        gp.enemies[mapNum][3].worldY = gp.tileSize*12;

        gp.enemies[mapNum][4] = new ENE_wolf(gp);
        gp.enemies[mapNum][4].worldX = gp.tileSize*38;
        gp.enemies[mapNum][4].worldY = gp.tileSize*12;

        gp.enemies[mapNum][5] = new ENE_wolf(gp);
        gp.enemies[mapNum][5].worldX = gp.tileSize*40;
        gp.enemies[mapNum][5].worldY = gp.tileSize*12;

    }
}
