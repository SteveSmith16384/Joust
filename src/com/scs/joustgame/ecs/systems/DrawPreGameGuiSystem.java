package com.scs.joustgame.ecs.systems;

import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.joustgame.models.PlayerData;
import com.scs.simple2dgameframework.graphics.Sprite;

public class DrawPreGameGuiSystem {

	private Sprite background;
	//private Sprite logo;
	private JoustMain game;

	public DrawPreGameGuiSystem(JoustMain _game) {
		game = _game;

		background = game.createSprite(Settings.GFX_FOLDER + "background.jpg");
		background.setSize(Settings.LOGICAL_WIDTH_PIXELS,  Settings.LOGICAL_HEIGHT_PIXELS);

		/*logo = game.createSprite(Settings.GFX_FOLDER + "ctc_logo.png");
			logo.setSize(Settings.LOGICAL_WIDTH_PIXELS/2, Settings.LOGICAL_HEIGHT_PIXELS/2);
			logo.setPosition(Settings.LOGICAL_WIDTH_PIXELS/4, Settings.LOGICAL_HEIGHT_PIXELS/2);*/
		//}
	}


	public void process() {
		//if (Settings.RELEASE_MODE) {
		background.drawSprite();
		//logo.drawSprite();
		//}

		//game.drawFont(batch, Controllers.getControllers().size + " controllers found", 20, Settings.LOGICAL_HEIGHT_PIXELS-40);

		int count = 0;
		for (PlayerData player : game.players) {
			if (player.isInGame()) {
				count++;
			}
		}
		game.setColour(0, 0, 0);
		game.drawFont(count + " players in the game!", 20, 20);
		game.drawFont("Press Space, W or Down for keyboard players", 20, 50);
		game.drawFont("Press X on controller", 20, 80);
		game.drawFont("PRESS 'S' TO START!", 20, 110);
	}



}
