package com.scs.joustgame.ecs.components;

import java.awt.geom.Rectangle2D;

import com.scs.simple2dgameframework.graphics.Sprite;

public class ImageComponent {

	public final String imageFilename;
	public int w, h;
	public Sprite sprite;
	public int zOrder;

	public Rectangle2D.Float atlasPosition; // Fill this in if it uses atlas

	public ImageComponent(String _filename, int _zOrder, float _w, float _h)  {
		this.imageFilename = _filename;
		zOrder = _zOrder;
		w = (int)_w;
		h = (int)_h;

		if (_filename == null || _filename.length() == 0) {
			throw new RuntimeException("No filename specified");
		}
	}


	public ImageComponent(Sprite _sprite, int _zOrder)  {
		this.sprite = _sprite;
		zOrder = _zOrder;
		w = -1;//_w;
		h = -1;//_h;
		imageFilename = "none";
		
		if (sprite == null) {
			throw new RuntimeException("No sprite");
		}
	}


	public ImageComponent(String _filename, int _zOrder, float _w, float _h, Rectangle2D.Float _atlasPosition)  {
		this(_filename, _zOrder, _w, _h);

		atlasPosition = _atlasPosition;
	}


}
