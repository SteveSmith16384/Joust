package com.scs.joustgame.input;

import java.awt.event.KeyEvent;

import com.scs.joustgame.JoustMain;

public class KeyboardCursorInput implements IPlayerInput {

	private JoustMain game;
	
	public KeyboardCursorInput(JoustMain _game) {
		game = _game;
	}
	
	
	@Override
	public boolean isLeftPressed() {
		return game.isKeyPressed(KeyEvent.VK_LEFT);
	}
	

	@Override
	public boolean isRightPressed() {
		return game.isKeyPressed(KeyEvent.VK_RIGHT);
	}

	
	@Override
	public boolean isJumpPressed() {
		return game.isKeyPressed(KeyEvent.VK_UP);
	}


	@Override
	public void poll() {
		// Not required		
	}

}
