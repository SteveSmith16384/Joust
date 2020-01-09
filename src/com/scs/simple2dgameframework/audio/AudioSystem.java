package com.scs.simple2dgameframework.audio;

public class AudioSystem {

	private AudioPlayer musicPlayer;
	
	public AudioSystem() {
	}


	public void playMusic(String s) {
		if (musicPlayer != null) {
			musicPlayer.stopNow();
		}
		musicPlayer = new AudioPlayer(s, true);
		musicPlayer.start();
	}


}
