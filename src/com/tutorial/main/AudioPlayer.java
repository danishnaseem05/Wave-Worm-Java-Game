package com.tutorial.main;


import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioPlayer {
	
	public static Map<String, Sound> soundMap = new HashMap<>();
	public static Map<String, Music> musicMap = new HashMap<>();
	
	public static void Load() {
		
		try {
			soundMap.put("menu_sound", new Sound("D:\\danis\\Documents\\Java Projects\\Game1\\src\\com\\tutorial\\main\\res\\menu_click.ogg"));
			musicMap.put("music", new Music("D:\\danis\\Documents\\Java Projects\\Game1\\src\\com\\tutorial\\main\\res\\background_music.ogg"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}
	
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
}
