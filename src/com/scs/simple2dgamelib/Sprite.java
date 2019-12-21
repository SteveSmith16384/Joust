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


	public void setSize(int w, int h) {
		BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		scaled.getGraphics().drawImage(img, 0, 0, w, h, game);
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
