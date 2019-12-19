package com.scs.coopplatformer;

import com.scs.simple2dgamelib.Simple2DGameLib;

public class Main extends Simple2DGameLib {

	public Main() {
		createWindow(640, 480, false);
	}
	
	
	@Override
	public void draw() {
		this.setBackgroundColour(255, 255, 0);
	}
	
	// ----------------------------------------------

	public static void main(String[] args) {
		new Main();
	}

}
