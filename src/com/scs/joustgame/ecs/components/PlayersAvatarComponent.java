package com.scs.joustgame.ecs.components;

import com.scs.joustgame.models.PlayerData;

public class PlayersAvatarComponent {

	public PlayerData player;
	//public IPlayerInput controller; // todo - remove
	public long timeStarted;

	public PlayersAvatarComponent(PlayerData _player) {
		player = _player;
		//controller = _controller;
		
		timeStarted = System.currentTimeMillis();
	}
	
}
