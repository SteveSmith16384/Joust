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
			BufferedImage sourceImage = ImageIO.read(new File(Simple2DGameFramework.ASSETS_FOLDER + filename));

			BufferedImage finalImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

			// First coords are DEST, second coords are SOURCE.
			// All coords are POINTS not W/H!

			// Left
			finalImg.getGraphics().drawImage(sourceImage, 0, 0, insets.x, h, 
					0, 0, insets.x, sourceImage.getHeight(), game.frame);

			// Top
			finalImg.getGraphics().drawImage(sourceImage, 0, 0, w, insets.y, 
					0, 0, sourceImage.getWidth(), insets.y, game.frame);

			// Right
			finalImg.getGraphics().drawImage(sourceImage, w-insets.width, 0, w, h, 
					sourceImage.getWidth()-insets.width, 0, sourceImage.getWidth(), sourceImage.getHeight(), game.frame);

			// Bottom
			finalImg.getGraphics().drawImage(sourceImage, 0, h-insets.height, w, h, 
					0, 0, sourceImage.getWidth(), sourceImage.getHeight(), game.frame);

			// Middle
			finalImg.getGraphics().drawImage(sourceImage, insets.x, insets.height, w-insets.width, h-insets.height,
					insets.x, insets.height, sourceImage.getWidth()-insets.width, sourceImage.getHeight()-insets.height, game.frame);

			return new Sprite(game, finalImg);
		} catch (IOException ex) {
			game.handleException(ex);
			return null;
		}
	}


}
