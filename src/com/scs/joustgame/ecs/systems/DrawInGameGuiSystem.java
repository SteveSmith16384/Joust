package com.scs.joustgame.ecs.systems;

import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.joustgame.models.PlayerData;
import com.scs.simple2dgameframework.graphics.Sprite;

public class DrawInGameGuiSystem {

	private JoustMain game;
	private Sprite[] players = new Sprite[3];

	public DrawInGameGuiSystem(JoustMain _game) {
		game = _game;
	}


	public void process() {
		int num = 0;
		for (PlayerData player : game.players) {
			if (player.isInGame()) {
				int xStart = 20+(num*250);
				game.drawFont("Score: " + player.score, xStart, 90);
				if (player.lives > 0) {
					if (players[num] == null) {
						players[num] = game.createSprite(Settings.GFX_FOLDER + "player" + player.imageId + "_right1.png");
						players[num].setSize(Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
					}
					for (int i=0 ; i<player.lives ; i++) {
						players[num].setPosition(xStart+(i*20), 30);
						players[num].drawSprite();
					}
				} else {
					game.drawFont("GAME OVER!", xStart, 40);
				}
				num++;
			}
		}
	}
}
