package com.scs.simple2dgamelib;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
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
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.scs.coopplatformer.KeyboardPlayer;
import com.scs.coopplatformer.models.PlayerData;
import com.scs.simple2dgamelib.input.ControllerWrapper;
import com.scs.simple2dgamelib.input.IPlayerInput;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public abstract class Simple2DGameLib extends JFrame implements MouseListener, KeyListener, MouseMotionListener, WindowListener, MouseWheelListener, Runnable {

	//public static 
	private Thread thread;
	private BufferStrategy bs;
	private boolean running = true;
	private boolean fullScreen;
	private Graphics2D g2;
	private Color backgroundColor = new Color(255, 255, 255);
	private boolean[] keys = new boolean[255];
	public float diff;
	private boolean listenForPlayers = false;

	protected List<IPlayerInput> controllersAdded = new ArrayList<IPlayerInput>();
	protected List<IPlayerInput> controllersRemoved = new ArrayList<IPlayerInput>();
	public HashMap<IPlayerInput, PlayerData> players = new HashMap<IPlayerInput, PlayerData>();
	//public DummyController dummyController = new DummyController(); // todo - Interface keyboard

	public Simple2DGameLib() {
		super();

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		this.addWindowListener(this);
		this.addMouseWheelListener(this);

		this.setVisible(false);
		this.setResizable(false);
		
		thread = new Thread(this, "MainThread");
		thread.setDaemon(true);
		thread.start();
		
	}
	
	
	public void checkForControllers() {
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		// Add players for all connected controllers
		for (Controller controller : controllers) {
			controllersAdded.add(new ControllerWrapper(controller));
		}
		listenForPlayers = true;
	}
	

	private void checkNewOrRemovedControllers() {
		for (IPlayerInput c : this.controllersAdded) {
			this.addPlayerForController(c);
		}
		this.controllersAdded.clear();

		for (IPlayerInput c : this.controllersRemoved) {
			this.removePlayerForController(c);
		}
		this.controllersAdded.clear();
	}


	public void addPlayerForController(IPlayerInput controller) {
		// Overridden
	}


	public void removePlayerForController(IPlayerInput controller) {
		// Overridden
	}
	
	
	public void start() {
		// Overridden
	}


	public void run() {
		try {
			start();

			diff = 100;
			
			while (running) {
				long start = System.currentTimeMillis();
				
				if (this.listenForPlayers) {
					this.checkNewOrRemovedControllers();
				}
				
				parentDraw(diff/1000f);

				//if (diff < Settings.FPS) {
					Thread.sleep(50);//Settings.FPS - diff);
				//}
					diff = System.currentTimeMillis() - start;
			}
		} catch (Exception ex) {
			handleException(ex);
		}
	}


	protected void parentDraw(float delta) {
		if (bs != null) { // Have we got a canvas yet?
			g2 = (Graphics2D)bs.getDrawGraphics();

			g2.setColor(this.backgroundColor);
			g2.fillRect(0,  0,  this.getWidth(), this.getHeight());

			draw(delta);

			bs.show();
		}
	}


	protected void draw(float delta) {
		// To be overridden by parent
	}


	protected void setBackgroundColour(int r, int g, int b) {
		backgroundColor = new Color(r, g, b);
		//super.setBackground(new Color(r, g, b));
	}


	public void createWindow(int w, int h, boolean _fullScreen) {
		fullScreen = _fullScreen;

		this.setUndecorated(true);
		if (fullScreen) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
			device.setFullScreenWindow(this);

		} else {
			this.setSize(w, h); // 25 since we've pushed image down to take into account insets of Window
		}

		this.setVisible(true);
		this.createBufferStrategy(2);
		bs = this.getBufferStrategy();
	}


	public Sprite createSprite(String filename) {
		try {
			BufferedImage img = ImageIO.read(new File(filename));
			return new Sprite(this, img);
		} catch (IOException ex) {
			handleException(ex);
			return null;
		}
	}


	public Sprite createSprite(String filename, int w, int h) {
		try {
			BufferedImage img = ImageIO.read(new File(filename));

			BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			scaled.getGraphics().drawImage(img, 0, 0, w, h, this);

			return new Sprite(this, scaled);
		} catch (IOException ex) {
			handleException(ex);
			return null;
		}
	}


	public void drawSprite(Sprite s, int x, int y) {
		if (s.img != null) {
			s.setPosition(x, y);
			g2.drawImage(s.img, x, y, null);
		}
	}


	public void drawSprite(Sprite s) {
		if (s.img != null) {
			g2.drawImage(s.img, (int)s.pos.x, (int)s.pos.y, null);
		}
	}


	public void drawFont(String text, float x, float y) { // todo - rename
		g2.drawString(text, x, y);
	}


	public boolean isKeyPressed(int code) {
		return keys[code];
	}
	
	
	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_F1) {
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
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			return;
		}
		
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
		this.dispose();

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
		running = false;
		this.setVisible(false);
	}


	public static void p(String s) {
		System.out.println(s);
	}
}
