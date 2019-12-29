package com.tutorial.main;

import java.awt.*;
import java.util.Random;

public class HardEnemy extends GameObject {

    private Handler handler;
    private Random r = new Random();

    public HardEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = 5;
        velY = 5;

    }

    public void tick() {
        x += velX;
        y += velY;

        if(y <= 0 || y >= Game.HEIGHT - 50){
            if(velY < 0) {
                velY = (r.nextInt(8) + 2);
            }
            else{
                velY = -(r.nextInt(8) + 2);
            }
        }
        if(x <= 0 || x >= Game.WIDTH - 26) {
            if(velX < 0) {
                velX = (r.nextInt(7) + 2);
            }
            else{
                velX = -(r.nextInt(7) + 2);
            }
        }

        handler.addObject(new Trail(x, y, ID.Trail, Color.yellow, 16, 16, 0.03f, handler));

    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect((int)x, (int)y, 16, 16);

    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }

}
