package com.scs.simple2dgameframework.demo;

import java.awt.event.KeyEvent;

import com.scs.simple2dgameframework.Simple2DGameFramework;
import com.scs.simple2dgameframework.graphics.Sprite;

public class FrameworkDemo extends Simple2DGameFramework {

	private Sprite sprite;
	private int x = 50;
	
	public FrameworkDemo() {
		super(400, 300);
		
		this.setScreenSize(800, 600);
	}
	
	
	@Override
	public void init() {
		this.setBackgroundColour(255, 255, 255);
		
		sprite = createSprite("grey_box.png", 20, 20);
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
		new FrameworkDemo();
	}

}
