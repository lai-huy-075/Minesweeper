package main.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import javafx.scene.control.MenuButton;
import main.Minesweeper;
import main.listener.Key;
import main.listener.Mouse;

/**
 * This {@code MinesweeperPanel} class holds all intractable and GUI elements of
 * {@link Minesweeper}.
 * 
 * @version 3 April 2020
 * @author MrPineapple065
 */
public class Panel extends JPanel implements ActionListener {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 0x80704238D46657C5L;

    /**
     * The standard {@link Font}.
     */
    public static final Font standardFont = new Font("Arial", Font.PLAIN, 30);

    /**
     * {@link MenuButton}
     */
    public static final JButton menu;

    /**
     * A {@link ImageIcon} holding the default {@code ImageIcon}
     */
    public static final ImageIcon menuDefault;

    /**
     * A {@link ImageIcon} holding the {@code ImageIcon} to display when
     * {@code Mouse} is pressed.
     */
    public static final ImageIcon menuClick;

    /**
     * A {@link ImageIcon} holding the {@code ImageIcon} to display when the game is
     * over.
     */
    public static final ImageIcon menuGameOver;

    /**
     * {@link Key} for this
     */
    public final Key keys;

    /**
     * A {@link JLabel} used to indicate {@link Board#getFlags()}
     */
    public final JLabel flagLabel = new JLabel("", JLabel.CENTER);

    /**
     * A {@link JLabel} used to indicate {@link Board#timer}
     */
    public final JLabel timeLabel = new JLabel("0", JLabel.CENTER);

    /**
     * Primitive type array of {@link String} of options
     */
    private static final String[] options = { "Reset", "Quit", "Controls" };

    /**
     * The actual {@link Board}
     */
    public final Board board;

    static {
        String filename = "./src/resources/menuDefault.png";
        File file = new File(filename);
        URL url;
        ReadableByteChannel rbc;
        FileOutputStream fout;

        if (!file.exists()) {
            Minesweeper.logger.info(file.getName() + " created");
            try {
                file.createNewFile();
                url = new URL("https://raw.githubusercontent.com/MrPineapple065/Minesweeper/master/src/resources/menuDefault.png");
                rbc = Channels.newChannel(url.openStream());
                fout = new FileOutputStream(file);
                fout.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (IOException ioe) {
                Minesweeper.logger.throwing("Panel", "static", ioe);
            }
        } else {
            Minesweeper.logger.info(file.getName() + " exists");
        }

        filename = "./src/resources/menuClick.png";
        file = new File(filename);
        if (!file.exists()) {
            Minesweeper.logger.info(file.getName() + " created");
            try {
                file.createNewFile();
                url = new URL("https://raw.githubusercontent.com/MrPineapple065/Minesweeper/master/src/resources/menuClick.png");
                rbc = Channels.newChannel(url.openStream());
                fout = new FileOutputStream(file);
                fout.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (IOException ioe) {
                Minesweeper.logger.throwing("Panel", "static", ioe);
            }
        } else {
            Minesweeper.logger.info(file.getName() + " exists");
        }

        filename = "./src/resources/menuDefault.png";
        file = new File(filename);
        if (!file.exists()) {
            Minesweeper.logger.info(file.getName() + " created");
            try {
                file.createNewFile();
                url = new URL("https://raw.githubusercontent.com/MrPineapple065/Minesweeper/master/src/resources/menuDefault.png");
                rbc = Channels.newChannel(url.openStream());
                fout = new FileOutputStream(file);
                fout.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (IOException ioe) {
                Minesweeper.logger.throwing("Panel", "static", ioe);
            }
        } else {
            Minesweeper.logger.info(file.getName() + " exists");
        }

        menuDefault = new ImageIcon(
                new ImageIcon("./src/resources/menuDefault.png").getImage().getScaledInstance(32, 32,
                        Image.SCALE_SMOOTH));
        menuClick = new ImageIcon(
                new ImageIcon("./src/resources/menuClick.png").getImage().getScaledInstance(32, 32,
                        Image.SCALE_SMOOTH));
        menuGameOver = new ImageIcon(new ImageIcon("./src/resources/menuGameOver.png").getImage().getScaledInstance(32,
                32, Image.SCALE_SMOOTH));

        menu = new JButton(menuDefault);
        menu.setOpaque(false);
        menu.setContentAreaFilled(false);
    }

    /**
     * Create a {@link Panel} with {@code row} number of rows, <br>
     * {@code col} number of columns, <br>
     * and {@code numBombs} bombs.
     * 
     * @param row      is the number of rows.
     * @param col      is the number of columns.
     * @param numBombs is the number of bombs.
     * 
     * @throws IllegalArgumentException  if construction of
     *                                   {@link Board#MinesweeperBoard(Panel, int, int, int)}
     *                                   fails.
     * @throws IndexOutOfBoundsException if construction of
     *                                   {@link Board#MinesweeperBoard(Panel, int, int, int)}
     *                                   fails.
     */
    public Panel(int row, int col, int numBombs) throws IllegalArgumentException, IndexOutOfBoundsException {
        super();
        try {
            this.board = new Board(this, row, col, numBombs);
        } catch (IllegalArgumentException iae) {
            throw iae;
        } catch (IndexOutOfBoundsException ioobe) {
            throw ioobe;
        }

        this.setLayout(new GridLayout(row + 1, col));

        UIManager.put("OptionPane.messageFont", standardFont);
        UIManager.put("OptionPane.buttonFont", standardFont);
        UIManager.put("Label.font", standardFont);
        UIManager.put("Label.background", null);
        UIManager.put("Label.foreground", Color.BLACK);

        this.keys = new Key(this);

        // Create other GUI Elements
        this.createLabels();
        this.createTiles();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        Minesweeper.logger.info(e.getActionCommand());
        this.displayMenu();
    }

    /**
     * Create {@link JLabel} and add them to the {@link Panel}
     */
    private void createLabels() {
        Minesweeper.logger.info("Creaing Labels");

        this.updateBLabel();

        for (int i = 0; i < this.board.colMax; i++) {
            if (i == this.board.colMax / 4)
                this.add(this.flagLabel);
            else if (i == this.board.colMax / 2)
                this.add(Panel.menu);
            else if (i == 3 * this.board.colMax / 4)
                this.add(this.timeLabel);
            else
                this.add(new JLabel("", JLabel.CENTER));
        }
    }

    /**
     * Initialize all {@link Tile} in {@link board}
     */
    private void createTiles() {
        Minesweeper.logger.info("Creaing tiles");

        for (Tile[] row : this.board.getBoard()) {
            for (Tile tile : row) {
                tile.addKeyListener(this.keys);
                tile.addMouseListener(new Mouse(this, this.board, tile));

                this.add(tile);
            }
        }
    }

    public void displayMenu() {
        switch (JOptionPane.showOptionDialog(this, "Pick an option", "Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, Minesweeper.icon, options, "Reset")) {
            case 0:
                if (this.board.getGameOver()) {
                    menu.setIcon(menuDefault);
                    this.board.reset();
                    return;
                } else {
                    switch (JOptionPane.showConfirmDialog(this, "Are you sure you want to reset?", "",
                            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, Minesweeper.icon)) {
                        case JOptionPane.YES_OPTION:
                            menu.setIcon(menuDefault);
                            this.board.reset();
                        default:
                            return;
                    }
                }
            case 1:
                switch (JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, Minesweeper.icon)) {
                    case JOptionPane.YES_OPTION:
                        System.exit(0);
                        return;
                    default:
                        return;
                }
            case 2:
                JTextArea jta = new JTextArea("Escape:\tPause\nr:\tReset\nq:\tQuit");
                jta.setOpaque(false);
                jta.setFont(Panel.standardFont);
                JOptionPane.showMessageDialog(this, jta, "Controls", JOptionPane.PLAIN_MESSAGE, Minesweeper.icon);
                return;
            default:
                return;
        }
    }

    /**
     * @return {@link #board}
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * @return {@link #timeLabel}
     */
    public JLabel getTimeLabel() {
        return this.timeLabel;
    }

    /**
     * Updates {@link #flagLabel} to display the number of bombs left to flag.
     */
    public void updateBLabel() {
        this.flagLabel.setText(String.valueOf(this.board.getFlags()));
    }

    /**
     * Update {@link #timeLabel} to display {@code time}
     * 
     * @param time is the time to display
     * 
     * @see Board#timer
     */
    public void updateTLabel(int time) {
        this.timeLabel.setText(String.valueOf(time));
    }
}