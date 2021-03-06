package com.scs.simple2dgameframework.input;

import java.util.ArrayList;
import java.util.List;

import com.scs.simple2dgameframework.Simple2DGameFramework;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class ControllerManager {

	private List<Controller> knownControllers = new ArrayList<Controller>();
	public List<Controller> controllersAdded = new ArrayList<Controller>();
	public List<Controller> controllersRemoved = new ArrayList<Controller>();
	public Controller[] controllers;
	
	private long lastCheckTime;

	public ControllerManager() {
	}


	public void checkForControllers() {
		if (lastCheckTime + 4000 > System.currentTimeMillis()) {
			return;
		}
		
		controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for (Controller controller : controllers) {
			if (knownControllers.contains(controller) == false) {
				knownControllers.add(controller);
				if (controller.getName().toLowerCase().indexOf("keyboard") < 0) {
					controllersAdded.add(controller);
				}
				Simple2DGameFramework.p("Controller added: " + controller.getName());
			}
		}

		// Removed controllers
		for (Controller knownController : this.knownControllers) {
			boolean found = false; 
			for (Controller controller : controllers) {
				if (controller == knownController) {
					found = true;
					break;
				}
			}
			if (found == false) {
				Simple2DGameFramework.p("Controller removed: " + knownController.getName());
				this.knownControllers.remove(knownController);
				this.controllersRemoved.add(knownController);
			}
		}
	}

}