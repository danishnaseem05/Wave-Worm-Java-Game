package com.tutorial.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Shop extends MouseAdapter{

    private Handler handler;
    private HUD hud;

    private int B1 = 1000;
    private int B2 = 1000;
    private int B3 = 1000;

    public Shop(Handler handler, HUD hud){
        this.handler = handler;
        this.hud = hud;
    }

    public void render(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 0, 50));
        g.drawString("SHOP", 255, 75);

        //box 1
        g.setFont(new Font("Arial", 0, 12));
        g.drawString("Upgrade Health", 130, 120);
        g.drawString("Cost: " + B1,130, 140);
        g.draw3DRect(120, 100, 100, 80, true);

        //box 2
        g.drawString("Upgrade Speed", 280, 120);
        g.drawString("Cost: " + B2,280, 140);
        g.draw3DRect(270, 100, 100, 80, true);

        //box 3
        g.drawString("Refill Health", 430, 120);
        g.drawString("Cost: " + B3,430, 140);
        g.draw3DRect(420, 100, 100, 80, true);

        g.drawString("SCORE: " + hud.getScore(), 270, 250);
    }

    public void mousePressed(MouseEvent e){


    }

}
