package com.scs.coopplatformer.ecs.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scs.coopplatformer.MyGdxGame;
import com.scs.coopplatformer.Settings;
import com.scs.coopplatformer.models.PlayerData;
import com.scs.simple2dgamelib.Sprite;
import com.sun.prism.Texture;

public class DrawInGameGuiSystem {

	private MyGdxGame game;
	private SpriteBatch batch;
	private Sprite[] players = new Sprite[3];

	public DrawInGameGuiSystem(MyGdxGame _game, SpriteBatch _batch) {
		game = _game;
		batch = _batch;
	}


	public void process() {
		int num = 0;
		for (PlayerData player : game.players.values()) {
			if (player.isInGame()) {
				int xStart = 20+(num*250);
				game.drawFont(batch, "Score: " + player.score, xStart, 90);
				if (player.lives > 0) {
					if (players[num] == null) {
						players[num] = new Sprite("player" + player.imageId + "_right1.png");
						players[num].setSize(Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
					}
					for (int i=0 ; i<player.lives ; i++) {
						players[num].setPosition(xStart+(i*20), 30);
						players[num].draw(batch);
					}
				} else {
					game.drawFont(batch, "GAME OVER!", xStart, 40);
				}
				num++;
			}
		}
	}
}
