package com.scs.joustgame;

import java.awt.event.KeyEvent;

import com.scs.basicecs.BasicECS;
import com.scs.simple2dgamelib.Simple2DGameLib;
import com.scs.simple2dgamelib.Sprite;

public class SimpleLibTest extends Simple2DGameLib {

	private Sprite sprite;
	private int x = 50;
	public BasicECS ecs;
	public EntityFactory entityFactory;
	
	@Override
	public void init() {
		//createWindow(800, 600, false);

		this.setBackgroundColour(255, 255, 255);
		
		sprite = createSprite("platform1.png", 20, 20);
	}
	
	
	@Override
	public void draw() {
		if (this.isKeyPressed(KeyEvent.VK_P)) {
			x += 100*super.diff_secs;
		} else if (this.isKeyPressed(KeyEvent.VK_O)) {
			x -= 100*super.diff_secs;
		}

		drawSprite(sprite, x, 50);
	}
	
	// ----------------------------------------------

	public static void main(String[] args) {
		new SimpleLibTest();
	}

}
