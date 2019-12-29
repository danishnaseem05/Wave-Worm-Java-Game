package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.tutorial.main.Game.STATE;

public class Menu extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		//Play button
		if(game.gameState == STATE.Menu) {
			if(mouseOver(mx, my, 210, 150, 200, 64)) {
				AudioPlayer.getSound("menu_sound").play();
				game.gameState = STATE.Select;
				return;
			}
			//Help button
			if(mouseOver(mx, my, 210, 250, 200, 64)) {
				AudioPlayer.getSound("menu_sound").play();
				game.gameState = STATE.Help;
			}
			//Quit button
			if(mouseOver(mx, my, 210, 350, 200, 64)) {
				AudioPlayer.getSound("menu_sound").play();
				System.exit(0);
			}
		}
		//Select Difficulty for Play button
		if(game.gameState == STATE.Select){
			//Normal Mode
			if(mouseOver(mx, my, 210, 150, 200, 64)){
				AudioPlayer.getSound("menu_sound").play();
				game.gameState = STATE.Game;
				handler.addObject(new Player(game.WIDTH / 2 - 32, game.HEIGHT / 2 - 32, ID.Player, handler));
				handler.clearEnemies();
				handler.addObject(new BasicEnemy(r.nextInt(game.WIDTH - 50), r.nextInt(game.HEIGHT - 50), ID.BasicEnemy, handler));
				game.difficulty = -1;
			}
			//Hard Mode
			if(mouseOver(mx, my, 210, 250, 200, 64)){
				AudioPlayer.getSound("menu_sound").play();
				game.gameState = STATE.Game;
				handler.addObject(new Player(game.WIDTH / 2 - 32, game.HEIGHT / 2 - 32, ID.Player, handler));
				handler.clearEnemies();
				handler.addObject(new HardEnemy(r.nextInt(game.WIDTH - 50), r.nextInt(game.HEIGHT - 50), ID.BasicEnemy, handler));
				game.difficulty = 1;
			}
		}

		//Back button for Help and Select difficulty
		if(game.gameState == STATE.Help || game.gameState == STATE.Select) {
			if(mouseOver(mx, my, 210, 350, 200, 64)) {
				AudioPlayer.getSound("menu_sound").play();
				game.gameState = STATE.Menu;
				return;
			}	
		}
		//Back button for Help
		if(game.gameState == STATE.End) {
			if(mouseOver(mx, my, 210, 350, 200, 64)) {
				AudioPlayer.getSound("menu_sound").play();
				game.gameState = STATE.Select;
				hud.setScore(0);
				hud.setLevel(1);
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {return true;}
			else return false;}
		else return false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		if(game.gameState == STATE.Menu) {
			Font fnt = new Font("aerial", 3, 50);
			Font fnt2 = new Font("aerial", 1, 30);
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("WAVE WORM", 150, 75);
			
			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Play", 275, 190);
			
			g.drawRect(210, 250, 200, 64);
			g.drawString("Help", 275, 290);
			
			g.drawRect(210, 350, 200, 64);
			g.drawString("Quit", 275, 390);
		}
		
		else if(game.gameState == STATE.Help) {
			Font fnt = new Font("aerial", 1, 50);
			Font fnt2 = new Font("aerial", 1, 15);
			Font fnt3 = new Font("aerial", 2, 26);
			Font fnt4 = new Font("aerial", 1, 30);
			
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Help", 260, 75);
			
			g.setFont(fnt2);
			g.drawString("Use (W S A D) keys to move player (up, down, left, right) directions respectively.", 24, 150);
			g.drawString("Use (ESC) key to Exit, and (P) key Pause during Gameplay.", 100,180);
			g.drawString("And Dodge Enemies!", 234,210);
			
			g.setFont(fnt3);
			g.drawString("Have Fun!", 245, 300);
			
			g.setFont(fnt4);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 275, 390);
		}
		
		else if(game.gameState == STATE.End) {
			Font fnt = new Font("aerial", 1, 50);
			Font fnt2 = new Font("aerial", 3, 30);
			Font fnt3 = new Font("aerial", 1, 18);
			Font fnt4 = new Font("aerial", 1, 30);
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Game Over", 175, 75);
			
			g.setFont(fnt2);
			g.drawString("You Lost!", 242, 175);
			g.setFont(fnt3);
			g.drawString("With a score of: " + hud.getScore(), 221, 255);
			g.drawString("At level: " + hud.getLevel(), 269, 285);
			
			g.setFont(fnt4);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Try Again", 240, 390);
		}

		if(game.gameState == STATE.Select) {
			Font fnt = new Font("aerial", 3, 50);
			Font fnt2 = new Font("aerial", 1, 30);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("SELECT DIFFICULTY", 70, 75);

			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Normal", 260, 190);

			g.drawRect(210, 250, 200, 64);
			g.drawString("Hard", 275, 290);

			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 275, 390);
		}
	}

}
