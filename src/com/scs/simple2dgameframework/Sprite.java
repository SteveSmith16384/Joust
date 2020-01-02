package com.scs.simple2dgameframework;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Sprite {

	public BufferedImage img;
	private Simple2DGameFramework game;
	public Point2D.Float pos = new Point2D.Float();

	public Sprite(Simple2DGameFramework _game, BufferedImage _img) {
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
