package com.scs.joustgame.models;

import com.scs.basicecs.AbstractEntity;
import com.scs.joustgame.input.IPlayerInput;

public class PlayerData {

	public static int nextImageId = 1;

	public boolean moveLeft, moveRight, jumpOrFlap;
	public IPlayerInput controller;
	private boolean in_game = false;
	public AbstractEntity avatar;
	public float timeUntilAvatar;
	public int score;
	public int lives;
	public int imageId;

	public PlayerData(IPlayerInput _controller) {
		this.controller = _controller;
	}


	public void setInGame(boolean b) {
		if (b) {
			if (in_game) {
				throw new RuntimeException("Player already in game!");
			}
			this.in_game = true;
			this.lives = 3;
			//if (imageId <= 0) {
				imageId = nextImageId++;
			//}
		} else {
			this.in_game = false;
		}
	}


	public boolean isInGame() {
		return this.in_game;
	}

}
