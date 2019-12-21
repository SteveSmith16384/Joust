package com.scs.joustgame.ecs.systems;

import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.joustgame.models.PlayerData;
import com.scs.simple2dgamelib.Sprite;

public class DrawPreGameGuiSystem {

	private Sprite background;
	private Sprite logo;
	private JoustMain game;

	public DrawPreGameGuiSystem(JoustMain _game) {
		game = _game;

		if (Settings.RELEASE_MODE) {
			background = game.createSprite("background.jpg");
			background.setSize(Settings.LOGICAL_WIDTH_PIXELS,  Settings.LOGICAL_HEIGHT_PIXELS);

			logo = game.createSprite("ctc_logo.png");
			logo.setSize(Settings.LOGICAL_WIDTH_PIXELS/2, Settings.LOGICAL_HEIGHT_PIXELS/2);
			logo.setPosition(Settings.LOGICAL_WIDTH_PIXELS/4, Settings.LOGICAL_HEIGHT_PIXELS/2);
		}
	}


	public void process() {
		if (Settings.RELEASE_MODE) {
			background.drawSprite();
			logo.drawSprite();
		}

		//game.drawFont(batch, Controllers.getControllers().size + " controllers found", 20, Settings.LOGICAL_HEIGHT_PIXELS-40);

		int count = 0;
		for (PlayerData player : game.players.values()) {
			if (player.isInGame()) {
				count++;
			}
		}
		game.drawFont(count + " players in the game!", 20, Settings.LOGICAL_HEIGHT_PIXELS-200);
		game.drawFont("Press 'Space' for keyboard player", 20, Settings.LOGICAL_HEIGHT_PIXELS-260);
		game.drawFont("PRESS 'S' TO START!", 20, Settings.LOGICAL_HEIGHT_PIXELS-320);
	}



}