package com.scs.coopplatformer.ecs.systems;

import com.scs.coopplatformer.MyGdxGame;
import com.scs.coopplatformer.Settings;
import com.scs.coopplatformer.models.PlayerData;
import com.scs.simple2dgamelib.Sprite;
import com.sun.prism.Texture;

public class DrawPreGameGuiSystem {

	private Sprite background;
	private Sprite logo;
	private MyGdxGame game;

	public DrawPreGameGuiSystem(MyGdxGame _game) {
		game = _game;

		if (Settings.RELEASE_MODE) {
			background = new Sprite("background.jpg");
			background.setSize(Settings.LOGICAL_WIDTH_PIXELS,  Settings.LOGICAL_HEIGHT_PIXELS);

			Texture tex2 = new Texture();
			logo = new Sprite("ctc_logo.png");
			logo.setSize(Settings.LOGICAL_WIDTH_PIXELS/2, Settings.LOGICAL_HEIGHT_PIXELS/2);
			logo.setPosition(Settings.LOGICAL_WIDTH_PIXELS/4, Settings.LOGICAL_HEIGHT_PIXELS/2);
		}
	}


	public void process() {
		if (Settings.RELEASE_MODE) {
			background.draw(batch);
			logo.draw(batch);
		}

		//game.drawFont(batch, Controllers.getControllers().size + " controllers found", 20, Settings.LOGICAL_HEIGHT_PIXELS-40);

		int count = 0;
		for (PlayerData player : game.players.values()) {
			if (player.isInGame()) {
				count++;
			}
		}
		game.drawFont(batch, count + " players in the game!", 20, Settings.LOGICAL_HEIGHT_PIXELS-200);
		game.drawFont(batch, "Press 'Space' for keyboard player", 20, Settings.LOGICAL_HEIGHT_PIXELS-260);
		game.drawFont(batch, "PRESS 'S' TO START!", 20, Settings.LOGICAL_HEIGHT_PIXELS-320);
	}



}
