package skyline;

import java.applet.Applet;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import skyline.events.ArrowKeyEvent;
import skyline.events.ArrowKeyType;
import skyline.events.FireLaserBoltEvent;
import skyline.events.SetMouseTargetLocationEvent;
import skyline.events.ToggleSpaceshipVisibilityEvent;

public class GameWindow extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	@Override
	public void init() {
		engine = new Engine();
		setSize(engine.WIDTH, engine.HEIGHT);
		offDimension = new Dimension(getWidth(), getHeight());
		offImage = createImage(offDimension.width, offDimension.height);
		offGraphics = offImage.getGraphics();
		
		Thread thread = new Thread(this);
		thread.start();	
		
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		
		Image invis = Toolkit.getDefaultToolkit().getImage("invisible.png");
		Cursor invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(invis, new Point(0, 0), "Invisible");
		setCursor(invisibleCursor);
		
		stopped = false;
	}
	
	@Override
	public void start() {

	}
	
	@Override
	public void stop() {
	
	}
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void paint(Graphics g) {

		g.drawImage(offImage, 0, 0, this);
	
	}
	
	@Override
	public void update(Graphics g) {
		
		if (offDimension.width != getWidth() || offDimension.height != getHeight()) {
			offDimension.setSize(getWidth(), getHeight());
			offImage = createImage(offDimension.width, offDimension.height);
			offGraphics = offImage.getGraphics();
		}
		
		synchronized(this) {
			engine.draw(offGraphics);
		}
		
		paint(g);
		
	}


	@Override
	public void run() {
		
		while (!stopped) {
			synchronized(this) {
				engine.tick(TIME_PER_TICK);
				stopped = engine.isDead();
			}
			repaint();
			try {
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		if (engine.isAliensArrived()) {
			engine.plotEvent(new FireLaserBoltEvent(e.getX(), e.getY()));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			// Arrow keys or WASD
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				engine.plotEvent(new ArrowKeyEvent(ArrowKeyType.RIGHT, true));
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				engine.plotEvent(new ArrowKeyEvent(ArrowKeyType.LEFT, true));
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				engine.plotEvent(new ArrowKeyEvent(ArrowKeyType.UP, true));
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				engine.plotEvent(new ArrowKeyEvent(ArrowKeyType.DOWN, true));
				break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			// Arrow Keys or WASD
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				engine.plotEvent(new ArrowKeyEvent(ArrowKeyType.RIGHT, false));
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				engine.plotEvent(new ArrowKeyEvent(ArrowKeyType.LEFT, false));
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				engine.plotEvent(new ArrowKeyEvent(ArrowKeyType.UP, false));
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				engine.plotEvent(new ArrowKeyEvent(ArrowKeyType.DOWN, false));
				break;
				
			// Space key (only in keyReleased)
			case KeyEvent.VK_SPACE:
				engine.plotEvent(new ToggleSpaceshipVisibilityEvent());
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e) {
		engine.plotEvent(new SetMouseTargetLocationEvent(e.getX(), e.getY()));
	}
	
	
	private boolean stopped;
	private Engine engine;
	
	private Dimension offDimension;		// for double buffering to eliminate flashing
	private Image offImage;
	private Graphics offGraphics;
	
	private final int FRAME_DELAY = 17;
	private final double TIME_PER_TICK = .01;//.005;
	
}
