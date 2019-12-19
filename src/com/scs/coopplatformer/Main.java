package com.scs.coopplatformer;

import com.scs.simple2dgamelib.Simple2DGameLib;
import com.scs.simple2dgamelib.Sprite;

public class Main extends Simple2DGameLib {

	Sprite sprite;
	
	@Override
	public void start() {
		createWindow(640, 480, false);
		
		sprite = createSprite("assets/gfx/easy_ninja_l0.png", 20, 20);
	}
	
	
	@Override
	public void draw() {
		this.setBackgroundColour(255, 255, 255);
		
		drawSprite(sprite, 50, 50);
	}
	
	// ----------------------------------------------

	public static void main(String[] args) {
		new Main();
	}

}
