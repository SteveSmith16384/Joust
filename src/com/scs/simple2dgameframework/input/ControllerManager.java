package com.scs.simple2dgameframework.input;

import java.util.ArrayList;
import java.util.List;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ControllerManager {

	private List<Controller> knownControllers = new ArrayList<Controller>();
	public List<Controller> controllersAdded = new ArrayList<Controller>();
	public List<Controller> controllersRemoved = new ArrayList<Controller>();

	public ControllerManager() {
	}


	public void checkForControllers() {
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for (Controller controller : controllers) {
			if (knownControllers.contains(controller) == false) {
				knownControllers.add(controller);
				if (controller.getName().toLowerCase().indexOf("keyboard") < 0) {
					controllersAdded.add(controller);
				}
				//p("Controller added: " + controller);
			}
		}
		
		// Todo - removed controllers
		//this.controllersRemoved = controllers.
	}

}
