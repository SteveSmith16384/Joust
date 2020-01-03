package com.scs.simple2dgameframework;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Simple2DGameFramework game;
	private Canvas canvas;
	
	public GameWindow(Simple2DGameFramework _game, int pw, int ph, GraphicsConfiguration config) {
		game = _game;
		
		setSize(pw, ph);
		setUndecorated(true);
		setVisible(true);
		addWindowListener(game);
		setResizable(false);
		
		canvas = new Canvas(config);
		canvas.setSize(pw, ph);
		canvas.setIgnoreRepaint(true);
		add(canvas, 0);

		canvas.addKeyListener(game);
		canvas.addMouseListener(game);
		canvas.addMouseMotionListener(game);
		canvas.addMouseWheelListener(game);
		canvas.createBufferStrategy(2);

	}
	
	
	public BufferStrategy getBufferStrategy() {
		return canvas.getBufferStrategy();
	}

}
