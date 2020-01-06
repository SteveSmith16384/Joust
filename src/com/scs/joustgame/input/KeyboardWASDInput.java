package com.scs.joustgame.input;

import java.awt.event.KeyEvent;

import com.scs.joustgame.JoustMain;

public class KeyboardWASDInput implements IPlayerInput {

	private JoustMain game;
	
	public KeyboardWASDInput(JoustMain _game) {
		game = _game;
	}
	
	@Override
	public boolean isLeftPressed() {
		return game.isKeyPressed(KeyEvent.VK_A);
	}

	@Override
	public boolean isRightPressed() {
		return game.isKeyPressed(KeyEvent.VK_D);
	}

	@Override
	public boolean isJumpPressed() {
		return game.isKeyPressed(KeyEvent.VK_W);
	}

}
