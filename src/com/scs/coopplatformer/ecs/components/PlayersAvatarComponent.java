package com.scs.coopplatformer.ecs.components;

import com.scs.coopplatformer.models.PlayerData;
import com.scs.simple2dgamelib.input.Controller;

public class PlayersAvatarComponent {

	public PlayerData player;
	public Controller controller; // If null, player is keyboard
	public boolean moveLeft, moveRight, jump;
	public long timeStarted;
	
	public PlayersAvatarComponent(PlayerData _player, Controller _controller) {
		player = _player;
		controller = _controller;
		
		timeStarted = System.currentTimeMillis();
	}
	
}
