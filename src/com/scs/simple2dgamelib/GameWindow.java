package com.scs.simple2dgamelib;

import java.awt.Canvas;

import javax.swing.JFrame;

import com.scs.joustgame.Settings;

public class GameWindow extends JFrame {

	private Simple2DGameLib game;
	private Canvas canvas;
	
	public GameWindow(Simple2DGameLib _game) {
		game = _game;
		
		setSize(Settings.PHYSICAL_WIDTH_PIXELS, Settings.PHYSICAL_HEIGHT_PIXELS);
		setUndecorated(true);
		setVisible(true);
		addWindowListener(game);
		setResizable(false);
		
		canvas = new Canvas(game.config);
		canvas.setSize(Settings.PHYSICAL_WIDTH_PIXELS, Settings.PHYSICAL_HEIGHT_PIXELS);
		canvas.setIgnoreRepaint(true);
		add(canvas, 0);

		canvas.addKeyListener(game);
		canvas.addMouseListener(game);
		canvas.addMouseMotionListener(game);
		canvas.addMouseWheelListener(game);

	}
	
	
	public Canvas getCanvas() {
		return this.canvas;
	}

}
