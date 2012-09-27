package water;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Water extends JFrame implements Runnable, MouseListener,
		MouseMotionListener {

	private int width, height, hwidth, hheight;
	private MemoryImageSource source;
	private Image image, offImage;
	private Graphics offGraphics;
	private int i, a, b;
	private int fps, delay, size;

	private short ripplemap[];
	private int texture[];
	private int ripple[];
	private int oldind, newind, mapind;
	private int riprad;
	private Image im;

	private Thread animatorThread;
	private boolean frozen = true;
	private JPanel pnlMain = null;

	public Water() {
		super("Water");
		initialize();
	}
	
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(762, 502);
		this.setLocationRelativeTo(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		try {
			im = ImageIO.read(getClass().getResource("/images/frame_20101210.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		fps = 100;
		delay = (fps > 0) ? (1000 / fps) : 100;

		width = im.getWidth(this);
		height = im.getHeight(this);
		hwidth = width >> 1;
		hheight = height >> 1;
		riprad = 3;

		size = width * (height + 2) * 2;
		ripplemap = new short[size];
		ripple = new int[width * height];
		texture = new int[width * height];
		oldind = width;
		newind = width * (height + 3);
		PixelGrabber pg = new PixelGrabber(im, 0, 0, width, height, texture, 0,
				width);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		source = new MemoryImageSource(width, height, ripple, 0, width);
		source.setAnimated(true);
		source.setFullBufferUpdates(true);

		image = createImage(source);
		offImage = im;
		offGraphics = offImage.getGraphics();
		pnlMain = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(offImage, 0, 0, this);
			}
		};
		this.setContentPane(pnlMain);
		this.setVisible(true);
	}

	public void start() {
		if (frozen) {
			// Do nothing.
		} else {
			// Start animation thread
			if (animatorThread == null) {
				animatorThread = new Thread(this);
			}
			animatorThread.start();
		}
	}

	public void stop() {
		animatorThread = null;
	}

	public void destroy() {
		removeMouseListener(this);
		removeMouseMotionListener(this);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (frozen) {
			frozen = false;
			start();
		} else {
			frozen = true;
			animatorThread = null;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		disturb(e.getX(), e.getY());
	}

	@Override
	public void run() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		long startTime = System.currentTimeMillis();

		while (Thread.currentThread() == animatorThread) {
			newframe();
			source.newPixels();
			offGraphics.drawImage(image, 0, 0, width, height, null);
			pnlMain.repaint();

			try {
				startTime += delay;
				Thread.sleep(Math
						.max(0, startTime - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public void disturb(int dx, int dy) {
		for (int j = dy - riprad; j < dy + riprad; j++) {
			for (int k = dx - riprad; k < dx + riprad; k++) {
				if (j >= 0 && j < height && k >= 0 && k < width) {
					ripplemap[oldind + (j * width) + k] += 256;
				}
			}
		}
	}

	public void newframe() {
		// Toggle maps each frame
		i = oldind;
		oldind = newind;
		newind = i;

		i = 0;
		mapind = oldind;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				short data = (short) ((ripplemap[mapind - width]
						+ ripplemap[mapind + width] + ripplemap[mapind - 1] + ripplemap[mapind + 1]) >> 1);
				data -= ripplemap[newind + i];
				data -= data >> 5;
				ripplemap[newind + i] = data;

				// where data=0 then still, where data>0 then wave
				data = (short) (1024 - data);

				// offsets
				a = ((x - hwidth) * data / 1024) + hwidth;
				b = ((y - hheight) * data / 1024) + hheight;

				// bounds check
				if (a >= width)
					a = width - 1;
				if (a < 0)
					a = 0;
				if (b >= height)
					b = height - 1;
				if (b < 0)
					b = 0;

				ripple[i] = texture[a + (b * width)];
				mapind++;
				i++;
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Water();
			}
		});
	}

}
