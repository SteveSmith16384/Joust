package com.scs.coopplatformer.ecs.systems;

import com.scs.coopplatformer.MyGdxGame;
import com.scs.coopplatformer.Settings;
import com.scs.simple2dgamelib.Sprite;

public class DrawPostGameGuiSystem {

	private Sprite background;
	private MyGdxGame game;
	private Sprite winnerSprite;

	public DrawPostGameGuiSystem(MyGdxGame _game) {
		game = _game;

		if (Settings.RELEASE_MODE) {
			background = game.createSprite("background.jpg");
			background.setSize(Settings.LOGICAL_WIDTH_PIXELS,  Settings.LOGICAL_HEIGHT_PIXELS);
		}
	}


	public void process() {
		if (Settings.RELEASE_MODE) {
			background.drawSprite();
		}
		
		game.drawFont("WINNER!", 20, Settings.LOGICAL_HEIGHT_PIXELS-40);
		if (winnerSprite == null) {
			winnerSprite = game.createSprite("player" + game.winnerImageId + "_right1.png");
			winnerSprite.setSize(Settings.LOGICAL_WIDTH_PIXELS/4, Settings.LOGICAL_HEIGHT_PIXELS/4);
			winnerSprite.setPosition(Settings.LOGICAL_WIDTH_PIXELS/2, Settings.LOGICAL_HEIGHT_PIXELS/2);
		}
		winnerSprite.drawSprite();
		
		game.drawFont("PRESS 'S' TO RESTART", 20, Settings.LOGICAL_HEIGHT_PIXELS-120);
	}
	
	

}
