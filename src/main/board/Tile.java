package main.board;

import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;

/**
 * This {@code Tile} class represents a Tile on {@link Board}.
 * 
 * @version 3 April 2020
 * @author MrPineapple065
 */
public class Tile extends JButton {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 0x448000763278F6FAL;

    /**
     * A {@code Array} of {@link ImageIcon} holding all the {@code ImageIcon} that
     * this will display.
     */
    public static final ImageIcon[] numbers = new ImageIcon[9];

    /**
     * A {@link ImageIcon} holding the {@code ImageIcon} for the bomb.
     */
    public static final ImageIcon bomb;

    /**
     * A {@link ImageIcon} holding the {@code ImageIcon} for an incorrectly flagged
     * {@link Tile}.
     */
    public static final ImageIcon incorrectFlag;

    /**
     * A {@link ImageIcon} holding the {@code ImageIcon} for the flag.
     */
    public static final ImageIcon flag;

    /**
     * A reference holding a {@link Color} that every Tile will be.
     */
    private static final Color color = new Color(0xbdbdbd);

    static {
	UIManager.put("TextArea.font", new Font("Arial", Font.PLAIN, 30));
	Tile.numbers[1] = new ImageIcon("./src/resources/one.png");
	Tile.numbers[2] = new ImageIcon("./src/resources/two.png");
	Tile.numbers[3] = new ImageIcon("./src/resources/three.png");
	Tile.numbers[4] = new ImageIcon("./src/resources/four.png");
	Tile.numbers[5] = new ImageIcon("./src/resources/five.png");
	Tile.numbers[6] = new ImageIcon("./src/resources/six.png");
	Tile.numbers[7] = new ImageIcon("./src/resources/seven.png");
	Tile.numbers[8] = new ImageIcon("./src/resources/eight.png");
	bomb = new ImageIcon("./src/resource/bomb.png");
	incorrectFlag = new ImageIcon("./src/resource/incorrectFlag.png");
	flag = new ImageIcon("./src/resource/flag.png");
    }

    /**
     * The {@link Panel} holding this.
     */
    @SuppressWarnings("unused")
    @Deprecated
    private final Panel panel;

    /**
     * The row that this is in.
     */
    public final int row;

    /**
     * The column that this is in.
     */
    public final int col;

    /**
     * The number of bombs surrounding this.
     */
    private int count;

    /**
     * A {@code boolean} determining if this has been flagged.
     */
    private boolean isFlagged;

    /**
     * A {@code boolean} determining if this is a bomb.
     */
    private boolean isBomb;

    /**
     * A {@code boolean} determininf if this is revealed
     */
    private boolean isRevealed;

    /**
     * Creates a {@code Tile} with row, col, panel.getBoard(), and panel defined.
     * 
     * @param panel is the {@link Panel} holding this.
     * @param row   is the {@link #row}
     * @param col   is the {@link #col}
     */
    public Tile(Panel panel, int row, int col) {
	super(null, null);

	this.panel = Objects.requireNonNull(panel, "Tile must be on MinesweeperPanel");
	this.row = row;
	this.col = col;

	// Set Default GUI Elements
	this.setBorder(BorderFactory.createRaisedBevelBorder());
	this.setHorizontalAlignment(JButton.CENTER);
	this.setVerticalAlignment(JButton.CENTER);
	this.setFocusPainted(false);

	this.reset();

	// Add Interactivity
	this.setFocusable(true);
	this.requestFocusInWindow();
    }

    /**
     * @return {@link #col}
     */
    public int getCol() {
	return this.col;
    }

    /**
     * @return {@link #count}
     */
    public int getCount() {
	return this.count;
    }

    /**
     * @return {@link #row}
     */
    public int getRow() {
	return this.row;
    }

    /**
     * @return {@link Tile#isBomb}
     */
    public boolean isBomb() {
	return this.isBomb;
    }

    /**
     * @return {@link Tile#isFlagged}
     */
    public boolean isFlagged() {
	return this.isFlagged;
    }

    /**
     * @return {@link Tile#isRevealed}
     */
    public boolean isRevealed() {
	return this.isRevealed;
    }

    /**
     * Reset Tile
     */
    public void reset() {
	this.setBorder(BorderFactory.createRaisedBevelBorder());
	this.isFlagged = false;
	this.isBomb = false;
	this.isRevealed = false;
	this.setIcon(null);
	this.setBackground(color);
    }

    /**
     * Set {@link #isBomb} to {@code isBomb}
     * 
     * @param isBomb new {@link #isBomb} value.
     */
    public void setBomb(boolean isBomb) {
	this.isBomb = isBomb;
    }

    /**
     * Set {@link #count}
     * 
     * @param count is the new {@code count}.
     * 
     * @return the new value.
     */
    public int setCount(int count) {
	this.count = count;
	return this.count;
    }

    /**
     * Set {@link #isFlagged} to {@code isFlagged}
     * 
     * @param isFlagged new {@link #isFlagged} value.
     */
    public void setFlagged(boolean isFlagged) {
	this.isFlagged = isFlagged;
    }

    /**
     * Set {@link #isRevealed} to {@code isRevealed}
     * 
     * @param isRevealed new {@link #isRevealed} value.
     */
    public void setRevealed(boolean isRevealed) {
	this.isRevealed = isRevealed;
    }

    /**
     * Toggle {@link #isFlagged}
     */
    public void toggleFlagged() {
	this.isFlagged ^= true;
    }

    @Override
    public String toString() {
	return String.format("(%s, %s)", String.valueOf(this.row), String.valueOf(this.col));
    }
}