package com.scs.joustgame.input;

import java.awt.event.KeyEvent;

import com.scs.joustgame.JoustMain;

public class KeyboardInput implements IPlayerInput {

	private JoustMain game;
	
	public KeyboardInput(JoustMain _game) {
		game = _game;
	}
	
	@Override
	public boolean isLeftPressed() {
		return game.isKeyPressed(KeyEvent.VK_O);
	}

	@Override
	public boolean isRightPressed() {
		return game.isKeyPressed(KeyEvent.VK_P);
	}

	@Override
	public boolean isJumpPressed() {
		return game.isKeyPressed(KeyEvent.VK_SPACE);
	}

}
