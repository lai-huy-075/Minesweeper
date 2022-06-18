package main;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import main.board.Panel;
import main.listener.Window;
import main.logging.CustomFormatter;

/**
 * Initialized the logic for the game.<br>
 * Main class
 * 
 * @author Mr. P&#x03B9;&#x03B7;&#x03B5;&#x03B1;&#x03C1;&#x03C1;l&#x03BE;
 * @version 2022 06 03
 */
public class Minesweeper {
	/**
	 * {@link Logger}
	 */
	public static final Logger logger;

	/**
	 * Primitive type array of {@link String} to chose a difficulty from.
	 */
	private static final String[] options = { "Beginner", "Intermediate", "Expert", "Custom", "Cancel" };

	/**
	 * {@link ImageIcon} for the {@link JFrame}
	 */
	public static final ImageIcon icon;

	/**
	 * {@link Image} of {@link #icon}
	 */
	public static final Image icon_image;

	static {
		FileHandler file = null;
		try {
			file = new FileHandler("./out.log", false);
		} catch (final SecurityException e) {
			Minesweeper.logger.throwing("Chess", "static", e);
		} catch (final IOException e) {
			Minesweeper.logger.throwing("Chess", "static", e);
		}
		file.setFormatter(new CustomFormatter());

		logger = Logger.getLogger("Chess");
		logger.setLevel(Level.ALL);
		logger.setUseParentHandlers(false);
		logger.addHandler(file);

		File f = new File("./src/resources/icon.png");
		if (!f.exists()) {
			f.mkdirs();
			try {
				f.createNewFile();
				URL url = new URL("https://raw.githubusercontent.com/MrPineapple065/Minesweeper/master/src/resources/icon.png");
				ReadableByteChannel rbc = Channels.newChannel(url.openStream());
				FileOutputStream fout = new FileOutputStream(f);
				fout.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fout.close();
			} catch (IOException ioe) {
				logger.throwing("Minesweeper", "static", ioe);
			}
		}

		icon = new ImageIcon("./src/resources/icon.png");
		icon_image = icon.getImage();
	}

	/**
	 * Main method
	 * 
	 * @param args primitive type array of {@link String}
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		Panel panel = null;

		final int difficulty = JOptionPane.showOptionDialog(null, "Choose Difficulty", "", JOptionPane.OK_OPTION,
				JOptionPane.PLAIN_MESSAGE, icon, options, "Medium");
		try {
			Minesweeper.logger.info("Difficulty:\t" + options[difficulty]);
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			return;
		}

		switch (difficulty) {
			case 0:
				panel = new Panel(9, 9, 10);
				break;
			case 1:
				panel = new Panel(16, 16, 40);
				break;
			case 2:
				panel = new Panel(16, 30, 99);
				break;
			case 3:
				JPanel jp = new JPanel();
				jp.setLayout(new GridLayout(3, 2));

				UIManager.put("Label.font", new Font("Arial", Font.PLAIN, 20));
				UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 20));

				JTextField row = new JTextField(10), col = new JTextField(10), bomb = new JTextField(10);

				jp.add(new JLabel("Row:"));
				jp.add(row);

				jp.add(new JLabel("Column:"));
				jp.add(col);

				jp.add(new JLabel("Bomb:"));
				jp.add(bomb);

				boolean fail = true;

				while (fail) {
					switch (JOptionPane.showConfirmDialog(null, jp, "", JOptionPane.OK_CANCEL_OPTION)) {
						case JOptionPane.OK_OPTION:
							try {
								panel = new Panel(Integer.parseInt(row.getText()), Integer.parseInt(col.getText()),
										Integer.parseInt(bomb.getText()));
								fail = false;
							} catch (IllegalArgumentException iae) {
								JOptionPane.showMessageDialog(null, "Try again", "", JOptionPane.ERROR_MESSAGE, icon);
								return;
							}
							break;
						default:
							return;
					}
				}
				break;
			default:
				return;
		}

		Objects.requireNonNull(panel, "panel cannot be null");
		frame.addWindowListener(new Window());
		frame.add(panel);
		frame.pack();
		frame.setIconImage(icon_image);
		frame.setSize(panel.getBoard().colMax * 50, (panel.getBoard().rowMax + 1) * 50);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
