package com.scs.joustgame.ecs.systems;

import com.scs.joustgame.JoustMain;
import com.scs.joustgame.Settings;
import com.scs.simple2dgameframework.graphics.Sprite;

public class DrawPostGameGuiSystem {

	private Sprite background;
	private JoustMain game;
	private Sprite winnerSprite;

	public DrawPostGameGuiSystem(JoustMain _game) {
		game = _game;

		//if (Settings.RELEASE_MODE) {
			background = game.createSprite(Settings.GFX_FOLDER + "background.jpg");
			background.setSize(Settings.LOGICAL_WIDTH_PIXELS,  Settings.LOGICAL_HEIGHT_PIXELS);
		//}
	}


	public void process() {
		//if (Settings.RELEASE_MODE) {
			background.drawSprite();
		//}
		
		game.drawFont("WINNER!", 20, Settings.LOGICAL_HEIGHT_PIXELS-40);
		if (winnerSprite == null) {
			winnerSprite = game.createSprite(Settings.GFX_FOLDER + "player" + game.winnerImageId + "_right1.png");
			winnerSprite.setSize(Settings.LOGICAL_WIDTH_PIXELS/4, Settings.LOGICAL_HEIGHT_PIXELS/4);
			winnerSprite.setPosition(Settings.LOGICAL_WIDTH_PIXELS/2, Settings.LOGICAL_HEIGHT_PIXELS/2);
		}
		winnerSprite.drawSprite();
		
		game.drawFont("PRESS 'S' TO RESTART", 20, Settings.LOGICAL_HEIGHT_PIXELS-120);
	}
	
	

}
