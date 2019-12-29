package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject {
	
	private Handler handler;
	Random r = new Random();
	private int red = r.nextInt(255);
	private int green = r.nextInt(255);
	private int blue = r.nextInt(255);
	private Color color;

	public MenuParticle(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velX = (r.nextInt(7- -7) + -7);
		velY = (r.nextInt(7- -7) + -7);
		if(velX == 0) velX = 1;
		if(velY == 0) velY = 1;
		
		color = new Color(red, green, blue);
		
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Game.HEIGHT - 50) velY *= -1;
		if(x <= 0 || x >= Game.WIDTH - 26) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, color, 16, 16, 0.05f, handler));
		
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, 16, 16);
		
	}

	public Rectangle getBounds() {
		return null;
	}

}
