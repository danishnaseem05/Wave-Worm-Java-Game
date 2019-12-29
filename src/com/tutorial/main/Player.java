package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends GameObject {

	Random r = new Random();
	Handler handler;

	private BufferedImage player_image;
	
	public Player(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;

		//SpriteSheet ss = new SpriteSheet(Game.sprite_sheet_enemies);

		//player_image = ss.grabImage((int) x, (int) y, 32, 32);
	}

	public void tick() {
		x += velX;
		y += velY;
		
		x = Game.clamp(x, 2, Game.WIDTH - 48);
		y = Game.clamp(y, 2, Game.HEIGHT - 70);
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.white, 32, 32, 0.06f, handler));
		
		collision();
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, 32, 32);

		//g.drawImage(player_image, (int) x, (int) y, null);
	}
	
	private void collision() {
		for(int i=0; i< handler.object.size(); i++) {
			
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getid() == ID.BasicEnemy 
					|| tempObject.getid() == ID.FastEnemy 
					|| tempObject.getid() == ID.SmartEnemy) {
				if(getBounds().intersects(tempObject.getBounds())) { //tempObject is now BasicEnemy
																	//or FastEnemy or SmartEnemy
					//collision code
					HUD.HEALTH -=2;
				}
			}
			else if(tempObject.getid() == ID.EnemyBoss) {
				if(getBounds().intersects(tempObject.getBounds())) { //tempObject is now EnemyBoss
					//collision code
					HUD.HEALTH -=4;
				}
			}

		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	
}
