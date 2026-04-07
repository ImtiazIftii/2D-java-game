package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;  //we use this open audio file
	URL soundURL[] = new URL[30]; //we use this URL to store the file path

	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
		soundURL[1] = getClass().getResource("/sound/coin.wav");
		soundURL[2] = getClass().getResource("/sound/powerup.wav");
		soundURL[3] = getClass().getResource("/sound/unlock.wav");
		soundURL[4] = getClass().getResource("/sound/fanfare.wav");
		soundURL[5] = getClass().getResource("/sound/gey.wav");
		soundURL[6] = getClass().getResource("/sound/nager.wav");
	}
	
	public void setFile(int i) {
		
		try {
			//this is the format to open an audio file in java
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);     
			
		}catch(Exception e) {
			
		}
	}
	public void play() {
		
		clip.start();
	}
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		
		clip.stop();
	}
}
