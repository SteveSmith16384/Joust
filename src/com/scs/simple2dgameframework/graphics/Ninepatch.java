package com.scs.simple2dgameframework.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.scs.simple2dgameframework.Simple2DGameFramework;

/**
 * Java Graphics: origin is top-left
 *
 */
public class Ninepatch {

	private Simple2DGameFramework game;
	private Rectangle insets;
	private String filename;

	public Ninepatch(Simple2DGameFramework _game, String _filename, Rectangle _rect) {
		game = _game;
		filename = _filename;
		insets = _rect;
	}


	public Sprite getImage(int w, int h) {
		try {
			//Sprite s = game.createSprite(filename, w, h);
			//BufferedImage p1 = s.img;
			BufferedImage sourceImage = ImageIO.read(new File(Simple2DGameFramework.ASSETS_FOLDER + filename));
			sourceImage = GraphicsUtils.scaleImage(sourceImage, w, h);

			BufferedImage finalImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

			// First coords are DEST, second coords are SOURCE

			// Left
			finalImg.getGraphics().drawImage(sourceImage, 0, 0, insets.x, h, 
					0, 0, insets.x, h, game.frame);

			// Top
			finalImg.getGraphics().drawImage(sourceImage, 0, 0, w, insets.y, 
					0, 0, w, insets.y, game.frame);

			// Right
			finalImg.getGraphics().drawImage(sourceImage, w-insets.width, 0, insets.width, h, 
					w-insets.width, 0, insets.width, h, game.frame);

			// Bottom
			finalImg.getGraphics().drawImage(sourceImage, 0, 0, w, insets.height, 
					0, 0, w, insets.height, game.frame);

			// Middle
			finalImg.getGraphics().drawImage(sourceImage, insets.x, insets.height, w-insets.x-insets.width, h-insets.height-insets.y,
					insets.x, insets.height, w-insets.x-insets.width, h-insets.height-insets.y, game.frame);

			return new Sprite(game, finalImg);
		} catch (IOException ex) {
			game.handleException(ex);
			return null;
		}
	}


}
