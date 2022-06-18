package main.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.UIManager;

import main.Minesweeper;

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

        for (int i = 1; i < 9; ++i) {
            String number;

            switch (i) {
                case 1:
                    number = "one";
                    break;
                case 2:
                    number = "two";
                    break;
                case 3:
                    number = "three";
                    break;
                case 4:
                    number = "four";
                    break;
                case 5:
                    number = "five";
                    break;
                case 6:
                    number = "six";
                    break;
                case 7:
                    number = "seven";
                    break;
                case 8:
                    number = "eight";
                    break;
                default:
                    number = null;
                    break;
            }

            Image image = new ImageIcon("./src/resources/" + number + ".png").getImage().getScaledInstance(32, 32,
                    Image.SCALE_SMOOTH);
            Tile.numbers[i] = new ImageIcon(image);
        }

        bomb = new ImageIcon(
                new ImageIcon("./src/resources/bomb.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        incorrectFlag = new ImageIcon(
                new ImageIcon("./src/resources/incorrectFlag.png").getImage().getScaledInstance(32,
                        32, Image.SCALE_SMOOTH));
        flag = new ImageIcon(
                new ImageIcon("./src/resources/flag.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
    }

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
     * @return {@link #count}
     */
    public int getCount() {
        return this.count;
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
        this.setIcon(this.isFlagged ? Tile.flag : null);
        Minesweeper.logger.info("Flagged set to:\t" + this.isFlagged);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.row, this.col);
    }
}