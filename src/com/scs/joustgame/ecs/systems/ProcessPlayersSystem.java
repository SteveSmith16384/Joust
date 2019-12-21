package com.scs.joustgame.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.joustgame.input.IPlayerInput;
import com.scs.joustgame.models.PlayerData;
import com.scs.lang.NumberFunctions;

public class ProcessPlayersSystem {

	private JoustMain game;

	public ProcessPlayersSystem(JoustMain _game) {
		game = _game;
	}


	public void process() {
		for (PlayerData player : game.players.values()) {
			if (player.isInGame() && player.quit == false) {
				if (player.lives > 0) {
					if (player.avatar == null) {
						player.timeUntilAvatar -= game.diff;
						if (player.timeUntilAvatar <= 0) {
							createPlayersAvatar(player, player.controller);
						}
					}
				}
			}
		}

		// Check for winner
		int winner = -1;
		int highestScore = -1;

		for (PlayerData player : game.players.values()) {
			if (player.isInGame()) {
				if (player.lives <= 0) {
					if (player.score > highestScore) {
						highestScore = player.score;
						winner = player.imageId;
					}
				} else {
					return;
				}
			}
		}

		game.setWinner(winner);

	}

	
	private void createPlayersAvatar(PlayerData player, IPlayerInput controller) {
		int xPos = NumberFunctions.rnd(50,  Settings.LOGICAL_WIDTH_PIXELS-50);
		AbstractEntity avatar = game.entityFactory.createPlayersAvatar(player, controller, xPos, Settings.LOGICAL_HEIGHT_PIXELS);
		game.ecs.addEntity(avatar);

		player.avatar = avatar;
	}

}
