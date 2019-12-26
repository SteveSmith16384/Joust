package com.scs.simple2dgamelib;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.scs.joustgame.Settings;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

// Todo - rename
public abstract class Simple2DGameLib extends Thread implements MouseListener, KeyListener, MouseMotionListener, WindowListener, MouseWheelListener, Runnable {

	//public static 
	//private Thread thread;
	private boolean running = true;
	private boolean fullScreen;
	private Color backgroundColor = new Color(255, 255, 255);
	private boolean[] keys = new boolean[255];
	public float diff_secs;
	private boolean listenForPlayers = false;

	protected List<Controller> controllersAdded = new ArrayList<Controller>();
	protected List<Controller> controllersRemoved = new ArrayList<Controller>();

	// Graphics Stuff
	private boolean isRunning = true;
	private Canvas canvas;
	private BufferStrategy strategy;
	private BufferedImage background;
	private Graphics2D backgroundGraphics;
	private Graphics2D graphics;
	public JFrame frame;
	//private int width = 800;
	//private int height = 600;
	private int scale = 1;
	private GraphicsConfiguration config =
			GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getDefaultScreenDevice()
			.getDefaultConfiguration();

	// Create a hardware accelerated image
	public final BufferedImage create(final int width, final int height,
			final boolean alpha) {
		return config.createCompatibleImage(width, height, alpha
				? Transparency.TRANSLUCENT : Transparency.OPAQUE);
	}

	public Simple2DGameLib() {
		super();

		System.setProperty("net.java.games.input.librarypath", new File("libs/jinput").getAbsolutePath());

		// JFrame
		frame = new JFrame();
		//frame.addWindowListener(new FrameClose());
		//frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setSize(Settings.PHYSICAL_WIDTH_PIXELS, Settings.PHYSICAL_HEIGHT_PIXELS);
		frame.setUndecorated(true);
		frame.setVisible(true);
		/*frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.addKeyListener(this);
		frame.addMouseWheelListener(this);*/
		frame.addWindowListener(this);
		frame.setResizable(false);

		// Canvas
		canvas = new Canvas(config);
		canvas.setSize(Settings.PHYSICAL_WIDTH_PIXELS, Settings.PHYSICAL_HEIGHT_PIXELS);
		canvas.setIgnoreRepaint(true);
		frame.add(canvas, 0);
		
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addMouseWheelListener(this);

		// Background & Buffer
		background = create(Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, false);
		canvas.createBufferStrategy(2);
		do {
			strategy = canvas.getBufferStrategy();
		} while (strategy == null);


		this.start();

	}


	public void checkForControllers() {
		if (listenForPlayers == false) {
			Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
			// Add players for all connected controllers
			for (Controller controller : controllers) {
				controllersAdded.add(controller);
				p("Controller added: " + controller);
			}
			listenForPlayers = true;
		} else {
			throw new RuntimeException("Already checked for controllers!");
		}
	}


	private void checkNewOrRemovedControllers() {
		for (Controller c : this.controllersAdded) {
			this.addPlayerForController(c);
		}
		this.controllersAdded.clear();

		for (Controller c : this.controllersRemoved) {
			this.removePlayerForController(c);
		}
		this.controllersAdded.clear();
	}


	public void addPlayerForController(Controller controller) {
		// Overridden
	}


	public void removePlayerForController(Controller controller) {
		// Overridden
	}



	// Screen Stuff ------------------------------------------------------------
	private Graphics2D getBuffer() {
		if (graphics == null) {
			try {
				graphics = (Graphics2D) strategy.getDrawGraphics();
			} catch (IllegalStateException e) {
				return null;
			}
		}
		return graphics;
	}

	private boolean updateScreen() {
		graphics.dispose();
		graphics = null;
		try {
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
			return (!strategy.contentsLost());

		} catch (NullPointerException e) {
			return true;

		} catch (IllegalStateException e) {
			return true;
		}
	}
	

	// MainLoop ----------------------------------------------------------------
	public void run() {
		// Using a Background Image instead of drawing directly to the buffer 
		// has two advantages: 1. You're able to scale the output and 2. 
		// it's actually faster...
		backgroundGraphics = (Graphics2D) background.getGraphics();
		
		init();
		
		long fpsWait = (long) (1.0 / 30 * 1000);
		main: while (isRunning) {
			long start = System.currentTimeMillis();

			long renderStart = System.nanoTime();
			updateGame();

			// Update Graphics
			do {
				Graphics2D bg = getBuffer();
				if (!isRunning) {
					break main;
				}
				renderGame(backgroundGraphics);
				//if (scale != 1) {
					bg.drawImage(background, 0, 0, Settings.PHYSICAL_WIDTH_PIXELS, Settings.PHYSICAL_HEIGHT_PIXELS, 0, 0, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, null);
				/*} else {
					bg.drawImage(background, 0, 0, null);
				}*/
				bg.dispose();
			} while (!updateScreen());

			// FPS Limiting
			/*long renderTime = (System.nanoTime() - renderStart) / 1000000;
			try {
				Thread.sleep(Math.max(0, fpsWait - renderTime));
			} catch (InterruptedException e) {
				Thread.interrupted();
				break;
			}
			renderTime = (System.nanoTime() - renderStart) / 1000000;*/
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			diff_secs = (System.currentTimeMillis() - start)/1000f;

		}
		frame.dispose();
	}

	
	// Game Methods ------------------------------------------------------------
	public void updateGame() {
		// update game logic here
	}

	//private Random rand = new Random();
	
	public void renderGame(Graphics2D g) {
		g.setColor(this.backgroundColor);
		g.fillRect(0, 0, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS);
		
		//g.setColor(Color.ORANGE);
		
		draw();
		
		//g.fillRect(rand.nextInt(width), rand.nextInt(height), 20, 20);
	}

	
	/*
	public void run() {
		try {
			start();

			diff_secs = 1;

			while (running) {
				long start = System.currentTimeMillis();

				if (this.listenForPlayers) {
					this.checkNewOrRemovedControllers();
				}

				parentDraw();

				// Update Graphics
				do {
					Graphics2D bg = getBuffer();
					//renderGame(backgroundGraphics);
					bg.drawImage(background, 0, 0, Settings.PHYSICAL_WIDTH_PIXELS, Settings.PHYSICAL_HEIGHT_PIXELS
							, 0, 0, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, null);
					bg.dispose();
				} while (!updateScreen());

				//if (diff < Settings.FPS) {
				Thread.sleep(10); // Stop it running too fast
				//}
				diff_secs = (System.currentTimeMillis() - start)/1000f;
			}
		} catch (Exception ex) {
			handleException(ex);
		}
	}

/*
	protected void parentDraw() {
		if (bs != null) { // Have we got a canvas yet?
			g2 = (Graphics2D)bs.getDrawGraphics();

			g2.setColor(this.backgroundColor);
			g2.fillRect(0,  0,  this.getWidth(), this.getHeight());

			draw();

			bs.show();
		}
	}
*/

	protected void init() {
		// To be overridden by parent
	}
	
	protected void draw() {
		// To be overridden by parent
	}


	protected void setBackgroundColour(int r, int g, int b) {
		backgroundColor = new Color(r, g, b);
		//super.setBackground(new Color(r, g, b));
	}


	public Sprite createSprite(String filename) {
		try {
			BufferedImage img = ImageIO.read(new File(Settings.GFX_FOLDER + filename));
			return new Sprite(this, img);
		} catch (IOException ex) {
			handleException(ex);
			return null;
		}
	}


	public Sprite createSprite(String filename, int w, int h) { // todo - combine create and setsize
		try {
			BufferedImage img = ImageIO.read(new File(Settings.GFX_FOLDER + filename));

			BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			scaled.getGraphics().drawImage(img, 0, 0, w, h, frame);

			return new Sprite(this, scaled);
		} catch (IOException ex) {
			handleException(ex);
			return null;
		}
	}


	public void drawSprite(Sprite s, int x, int y) {
		if (s.img != null) {
			s.setPosition(x, y);
			backgroundGraphics.drawImage(s.img, x, y, null);
		}
	}


	public void drawSprite(Sprite s) {
		if (s.img != null) {
			this.backgroundGraphics.drawImage(s.img, (int)s.pos.x, (int)s.pos.y, null);
		}
	}


	public void drawFont(String text, float x, float y) { // todo - rename
		backgroundGraphics.drawString(text, x, y);
	}


	public void setColour(int r, int g, int b) {
		backgroundGraphics.setColor(new Color(r, g, b));
	}


	public boolean isKeyPressed(int code) {
		return keys[code];
	}


	public void playMusic(String s) {
		// todo
	}


	@Override
	public void keyPressed(KeyEvent ke) {
		/*if (ke.getKeyCode() == KeyEvent.VK_F1) {
			// Take screenshot
			try {
				Rectangle r = null;
				if (fullScreen) {
					r = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				} else {
					Insets insets = this.getInsets();
					r = new Rectangle(this.getX() + insets.left, this.getY() + insets.top, this.getWidth() - insets.left-insets.right, this.getHeight() - insets.top-insets.bottom);
				}
				BufferedImage image = new Robot().createScreenCapture(r);
				String filename = "StellarForces_Screenshot_" + System.currentTimeMillis() + ".png";
				ImageIO.write(image, "png", new File(filename));
				p("Screenshot saved as " + filename);
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			return;
		}*/

		keys[ke.getKeyCode()] = true;
	}


	@Override
	public void keyReleased(KeyEvent ke) {
		keys[ke.getKeyCode()] = false;
	}


	@Override
	public void keyTyped(KeyEvent ke) {
		// For some reason this method desn't get values for the KeyEvent!?
		//throw new RuntimeException("Do not use - keycode is " + ke.getKeyCode());
	}


	@Override
	public void mouseClicked(MouseEvent me) {
	}


	@Override
	public void mouseEntered(MouseEvent me) {

	}


	@Override
	public void mouseExited(MouseEvent me) {

	}


	@Override
	public void mousePressed(MouseEvent me) {
	}


	@Override
	public void mouseReleased(MouseEvent me) {
	}


	@Override
	public void mouseDragged(MouseEvent me) {
	}


	@Override
	public void mouseMoved(MouseEvent me) {

	}


	@Override
	public void windowActivated(WindowEvent arg0) {

	}


	@Override
	public void windowClosed(WindowEvent arg0) {
	}


	@Override
	public void windowClosing(WindowEvent arg0) {
		this.running = false;
		frame.dispose();

	}


	@Override
	public void windowDeactivated(WindowEvent arg0) {

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {

	}


	@Override
	public void windowIconified(WindowEvent arg0) {

	}


	@Override
	public void windowOpened(WindowEvent arg0) {

	}


	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		/*if (thread.module != null) {
			thread.module.mouseWheelMoved(mwe);
		}*/
	}


	public void handleException(Exception ex) {
		p(ex.getMessage());
		ex.printStackTrace();
		running = false;
		frame.setVisible(false);
		System.exit(-1);
	}


	public static void p(String s) {
		System.out.println(s);
	}
}
