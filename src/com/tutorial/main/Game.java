package com.tutorial.main;

import org.newdawn.slick.openal.Audio;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1550691097823471818L;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH/12*9;
	private Thread thread;
	private boolean running = false;

	public static boolean paused = false;
	public int difficulty = 0; // -1 = Normal, 1 = Hard

	private Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	private Shop shop;
	
	public enum STATE{
		Menu,
		Shop,
		Select,
		Help,
		Game,
		End
	};
	
	public STATE gameState = STATE.Menu;

	public static BufferedImage sprite_sheet_player;
	public static BufferedImage sprite_sheet_enemies;
	
	public Game() {
		
		handler = new Handler();
		hud = new HUD();
		shop = new Shop(handler, hud);
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new KeyInput(handler, this));
		this.addMouseListener(menu);
		
		AudioPlayer.Load();
		AudioPlayer.getMusic("music").loop();

		new Window(WIDTH, HEIGHT, "Let's build a Game!", this);

		BufferedImageLoader loader = new BufferedImageLoader();

		sprite_sheet_player = loader.loadImage("\\res\\Player.png");
		sprite_sheet_enemies = loader.loadImage("\\res\\Enemies.png");
		
		spawner = new Spawn(handler, hud, this);
		r = new Random();
		
		if(gameState == STATE.Game && (difficulty == -1 || difficulty == 1)) {
			handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
			//handler.addObject(new Player(WIDTH/2+64, HEIGHT/2-32, ID.Player2));
			handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
		}else {
			for(int i=0; i < 20; i++) {
				handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
			}
		}
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames =0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >=1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		if(gameState == STATE.Game && (difficulty == -1 || difficulty == 1)) {
			if(!paused){
				handler.tick();
				spawner.tick();
				hud.tick();
			
				if(hud.HEALTH <= 0) {
					hud.HEALTH = 100;
					gameState = STATE.End;
					handler.clearAll();
					for(int i=0; i < 20; i++) {
						handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
					}
				}
			}
			
		}
		else if(gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Help || gameState == STATE.Select) {
			handler.tick();
			menu.tick();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0, WIDTH, HEIGHT);

		if(paused){
			g.setFont(new Font("Times New Roman",3, 18));
			g.setColor(Color.WHITE);
			g.drawString("PAUSED", 280, 100);
		}

		if(gameState == STATE.Game && (difficulty == -1 || difficulty == 1)) {
			handler.render(g);
			hud.render(g);
		}
		else if(gameState == STATE.Shop){
			shop.render(g);
		}
		else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select){
			handler.render(g);
			menu.render(g);
		}
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >= max)
			return var = max;
		else if(var <-min)
			return var = min;
		else
			return var;
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
