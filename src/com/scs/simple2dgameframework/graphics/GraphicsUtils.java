package com.scs.simple2dgameframework.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.scs.simple2dgameframework.Simple2DGameFramework;

public class GraphicsUtils {

	private ClassLoader cl;
	
	public GraphicsUtils() {
		cl = this.getClass().getClassLoader();
	}
	
	
	public BufferedImage loadImage(String filename) {
		try {
			InputStream is = cl.getResourceAsStream(Simple2DGameFramework.ASSETS_FOLDER + filename);
			return ImageIO.read(is);
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
				int tmp = image.getRGB(i, j);
				newimage.setRGB(image.getWidth()-i-1, j, tmp);
			}
		}
		return newimage;
	}


}
