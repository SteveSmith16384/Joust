package com.scs.joustgame.input;

import net.java.games.input.Controller;

public class ControllerInput implements IPlayerInput {

	public Controller controller;
	
	public ControllerInput(Controller _controller) {
		controller = _controller;
	}

	@Override
	public boolean isLeftPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRightPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isJumpPressed() {
		// TODO Auto-generated method stub
		return false;
	}

}
