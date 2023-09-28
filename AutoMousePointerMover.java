
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;

class AutoMousePointerMover extends Frame implements ActionListener {
	// Frame
	static JFrame f;
	static int width = 500;
	static int height = 500;

	// default constructor
	AutoMousePointerMover() {
	}

	// main function
	public static void main(String args[]) {
		AutoMousePointerMover rm = new AutoMousePointerMover();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

		rm.notepadThread();

		f = new JFrame("AlphaMouse-Keyboard");
		width = (int) size.getWidth();
		height = (int) size.getHeight();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Button b = new Button("Start");
		b.addActionListener(rm);
		Panel p = new Panel();
		p.add(b);
		f.add(p);

		f.setSize(100, 100);
		f.show();
	}

	public void actionPerformed(ActionEvent e) {
		try {
			Robot r = new Robot();
			int xi1, yi1, xi, yi;

			// get initial location
			Point p = MouseInfo.getPointerInfo().getLocation();
			xi = p.x;
			yi = p.y;

			// get x and y points
			xi1 = width;
			yi1 = height;
			int i = xi, j = yi;

			while (i != xi1 || j != yi1) {
				r.mouseMove(i, j);

				if (i < xi1)
					i++;
				if (j < yi1)
					j++;

				if (i > xi1)
					i--;
				if (j > yi1)
					j--;

				// wait
				Thread.sleep(10);
			}
		} catch (Exception evt) {
			System.err.println(evt.getMessage());
		}
	}

	public void notepadThread() {
		Thread notepadThread = new Thread() {
			public void run() {
				try {
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec("notepad");
					Thread.sleep(5000);

					Robot r = new Robot();

					int count = 10;
					while (count <= 10000000) {
						Random rand = new Random();
						int max = 90, min = 65;
						int nextNumber = rand.nextInt(max - min + 1) + min;
						r.keyPress(nextNumber);
						r.keyRelease(nextNumber);
						Thread.sleep(100);
						if(count % 1000 == 0) {
							Thread.sleep(50000);
						}
						count++;
					}

					//process.destroyForcibly();
					System.out.println("Closing notepad");

					Thread.sleep(30000); // 300000 = 5 min
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		notepadThread.start();
	}
}
