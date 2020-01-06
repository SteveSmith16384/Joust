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
		return game.isKeyPressed(KeyEvent.VK_O) || game.isKeyPressed(KeyEvent.VK_LEFT) || game.isKeyPressed(KeyEvent.VK_A);
	}

	@Override
	public boolean isRightPressed() {
		return game.isKeyPressed(KeyEvent.VK_P) || game.isKeyPressed(KeyEvent.VK_RIGHT) || game.isKeyPressed(KeyEvent.VK_D);
	}

	@Override
	public boolean isJumpPressed() {
		return game.isKeyPressed(KeyEvent.VK_SPACE) || game.isKeyPressed(KeyEvent.VK_UP) || game.isKeyPressed(KeyEvent.VK_W);
	}

}
