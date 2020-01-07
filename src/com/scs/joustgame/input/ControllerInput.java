package com.scs.joustgame.input;

import net.java.games.input.Controller;

public class ControllerInput implements IPlayerInput {

	public Controller controller;
	
	public ControllerInput(Controller _controller) {
		controller = _controller;
	}

	@Override
	public boolean isLeftPressed() {
		return false;//controller.; //Hat Switch changed to Off (1) 
	}

	@Override
	public boolean isRightPressed() {
		// TODO Auto-generated method stub
		//Hat Switch changed to Off (0.5)
		return false;
	}

	@Override
	public boolean isJumpPressed() {
		// TODO Auto-generated method stub
		return false; // Button 1 changed to On (1.0)
	}

}
