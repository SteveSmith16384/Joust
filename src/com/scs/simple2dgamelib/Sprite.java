package com.scs.simple2dgamelib;

import java.awt.image.BufferedImage;

import com.scs.awt.PointF;

public class Sprite {

	public BufferedImage img;
	private Simple2DGameLib game;
	public PointF pos = new PointF();

	public Sprite(Simple2DGameLib _game, BufferedImage _img) {
		game = _game;
		img = _img;
	}


	public void flip(boolean x, boolean y) {
		// todo
	}
	
	
	public void setSize(float w, float h) {
		BufferedImage scaled = new BufferedImage((int)w, (int)h, BufferedImage.TYPE_INT_ARGB);
		scaled.getGraphics().drawImage(img, 0, 0, (int)w, (int)h, game.frame);
		this.img = scaled;
	}
	
	
	public void setPosition(float x, float y) {
		this.pos.x = x;
		this.pos.y = y;
	}

	
	public void drawSprite() {
		game.drawSprite(this);
	}
}
