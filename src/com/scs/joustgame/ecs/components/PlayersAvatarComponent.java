package com.scs.joustgame.ecs.components;

import com.scs.joustgame.input.IPlayerInput;
import com.scs.joustgame.models.PlayerData;

public class PlayersAvatarComponent {

	public PlayerData player;
	public IPlayerInput controller;
	public boolean moveLeft, moveRight, jumpOrFlap;
	public long timeStarted;

	public PlayersAvatarComponent(PlayerData _player, IPlayerInput _controller) {
		player = _player;
		controller = _controller;
		
		timeStarted = System.currentTimeMillis();
	}
	
}
