package com.scs.coopplatformer.ecs.systems;

import com.scs.basicecs.AbstractEntity;
import com.scs.coopplatformer.MyGdxGame;
import com.scs.coopplatformer.Settings;
import com.scs.coopplatformer.models.PlayerData;
import com.scs.lang.NumberFunctions;
import com.scs.simple2dgamelib.input.Controller;

public class ProcessPlayersSystem {

	private MyGdxGame game;

	public ProcessPlayersSystem(MyGdxGame _game) {
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

	
	private void createPlayersAvatar(PlayerData player, Controller controller) {
		int xPos = NumberFunctions.rnd(50,  Settings.LOGICAL_WIDTH_PIXELS-50);
		AbstractEntity avatar = game.entityFactory.createPlayersAvatar(player, controller, xPos, Settings.LOGICAL_HEIGHT_PIXELS);
		game.ecs.addEntity(avatar);

		player.avatar = avatar;
	}

}
