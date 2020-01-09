package com.scs.joustgame.input;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.java.games.input.Component.Identifier;

public class PS4ControllerInput implements IPlayerInput {

	public Controller controller;
	private boolean leftPressed, rightPressed, firePressed;
	
	public PS4ControllerInput(Controller _controller) {
		controller = _controller;
	}


	@Override
	public void poll() {
		controller.poll();

		EventQueue queue = controller.getEventQueue();

		long lastEventTime = 0;
		Event event = new Event();
		
		while (queue.getNextEvent(event)) {
			if (event.getNanos() < lastEventTime) {
				//continue;
			}
			lastEventTime = event.getNanos();

			float value = event.getValue();
			//if (value == 0 || value < -0.5 || value > 0.5) {
			Component comp = event.getComponent();
			if (comp.isAnalog() == false) { // Only interested in digital events
				if (comp.getIdentifier() == Identifier.Axis.POV) {
					this.leftPressed = value >= 1f;
				//} else if (comp.getIdentifier() == Identifier.Button.RIGHT) {
					this.rightPressed = value == 0.5f;
				} else if (comp.getIdentifier() == Identifier.Button._1) { // X
					this.firePressed = value > 0.5f;
				}
			}
		}
	}


	@Override
	public boolean isLeftPressed() {
		return this.leftPressed; //Hat Switch changed to Off (1) 
	}

	@Override
	public boolean isRightPressed() {
		//Hat Switch changed to Off (0.5)
		return this.rightPressed;
	}

	@Override
	public boolean isJumpPressed() {
		return this.firePressed; // Button 1 changed to On (1.0)
	}

}
