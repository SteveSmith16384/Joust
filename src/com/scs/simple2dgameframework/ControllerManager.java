package com.scs.simple2dgameframework;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ControllerManager {

	protected List<Controller> knownControllers = new ArrayList<Controller>();
	protected List<Controller> controllersAdded = new ArrayList<Controller>();
	protected List<Controller> controllersRemoved = new ArrayList<Controller>();

	public ControllerManager() {
	}


	public void checkForControllers() {
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for (Controller controller : controllers) {
			if (knownControllers.contains(controller) == false) {
				knownControllers.add(controller);
				controllersAdded.add(controller);
				//p("Controller added: " + controller);
			}
		}
		
		// Todo - removed controllers
		//this.controllersRemoved = controllers.
	}

	/*
	private void checkNewOrRemovedControllers() {
		for (Controller c : this.controllersAdded) {
			this.addPlayerForController(c);
		}
		this.controllersAdded.clear();

		for (Controller c : this.controllersRemoved) {
			this.removePlayerForController(c);
		}
		this.controllersAdded.clear();
	}
	 */

}
