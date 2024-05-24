package entity;

import main.GamePanel;

public class Projectile extends Entity {

    Entity user;
    GamePanel gp;

    public Projectile(GamePanel gp)
    {
        super(gp);
        this.gp =gp;
    }

    public void set(int worldX, int worldY, String direction, boolean alive,Entity user)
    {

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    public void update()
    {

        if(user == gp.player)
        {
            int monsterIndex = gp.cChecker.checkEntity(this,gp.enemies);
            if(monsterIndex != 999)
            {
                gp.player.damageEnemy(monsterIndex,3);
                alive = false;
            }
        }

        if(user != gp.player)
        {
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if(gp.player.godmode == false && contactPlayer == true)
            {
                damagePlayer(1);
                alive =  false;
            }
        }

        switch(direction)
        {
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

        life--;
        if(life <= 0)
        {
            alive = false;
        }

    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if(user.Clip >= useCost)
        {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResource(Entity user)
    {
    }

}
