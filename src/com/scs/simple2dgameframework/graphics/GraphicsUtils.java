package com.scs.simple2dgameframework.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.scs.simple2dgameframework.Simple2DGameFramework;

public class GraphicsUtils {

	private GraphicsUtils() {
	}
	
	
	public static BufferedImage loadImage(String filename) {
		try {
			return ImageIO.read(new File(Simple2DGameFramework.ASSETS_FOLDER + filename));
		} catch (IOException ex) {
			throw new RuntimeException("Unable to load image " + filename);
		}
	}


	public static BufferedImage scaleImage(BufferedImage img, int w, int h) {
		BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		scaled.getGraphics().drawImage(img, 0, 0, w, h, null);
		return scaled;		
	}

	
	public static BufferedImage extractImage(BufferedImage img, int sx, int sy, int dw, int dh, ImageObserver obs) {
		//int dw = ex-sx;
		//int dh = ey-sy;
		BufferedImage finalImg = new BufferedImage(dw, dh, BufferedImage.TYPE_INT_ARGB);
		finalImg.getGraphics().drawImage(img, 0, 0, dw, dh, sx, sy, sx+dw, sy+dw, obs);		
		return finalImg;
	}


	public static BufferedImage flipLR(BufferedImage image) {
		BufferedImage newimage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int j=0;j<image.getHeight();j++) {
			for (int i=0;i<image.getWidth();i++) {
				//newimage.setRGB(i, j, image.getRGB(i, image.getHeight()-j-1));
				int tmp = image.getRGB(i, j);
				newimage.setRGB(i, image.getHeight()-j-1, tmp);
			}
		}
		return newimage;
	}


}
