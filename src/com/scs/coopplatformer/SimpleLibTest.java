package com.scs.coopplatformer;

import java.awt.event.KeyEvent;

import com.scs.basicecs.BasicECS;
import com.scs.simple2dgamelib.Simple2DGameLib;
import com.scs.simple2dgamelib.Sprite;

public class SimpleLibTest extends Simple2DGameLib {

	//public static final int FPS = (int)(1000f/30f);

	private Sprite sprite;
	private int x = 50;
	public BasicECS ecs;
	public EntityFactory entityFactory;
	
	@Override
	public void start() {
		createWindow(640, 480, false);

		this.setBackgroundColour(255, 255, 255);
		
		sprite = createSprite("assets/gfx/easy_ninja_l0.png", 20, 20);
	}
	
	
	@Override
	public void draw(float delta) {
		if (this.isKeyPressed(KeyEvent.VK_P)) {
			x += 100*delta;
		} else if (this.isKeyPressed(KeyEvent.VK_O)) {
			x -= 100*delta;
		}

		drawSprite(sprite, x, 50);
	}
	
	// ----------------------------------------------

	public static void main(String[] args) {
		new MyGdxGame();
	}

}
