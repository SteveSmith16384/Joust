package com.scs.simple2dgamelib.input;

import net.java.games.input.Controller;

public class ControllerWrapper implements IPlayerInput {

	private Controller controller;
	
	public ControllerWrapper(Controller _controller) {
		controller = _controller;
	}

}
