package main.board;

import java.awt.Color;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import main.Minesweeper;

/**
 * Minesweeper Board 
 * 
 * @author Mr. P&#x03B9;&#x03B7;&#x03B5;&#x03B1;&#x03C1;&#x03C1;l&#x03BE;
 * @version 2022 06 03
 */
public class Board {
    /**
     * {@link Random}
     */
    public static final Random rand = new Random();

    /**
     * The maximum number of rows.
     */
    public final int rowMax;

    /**
     * The maximum number of columns
     */
    public final int colMax;

    /**
     * The number of bombs on board.
     */
    public final int numBombs;

    /**
     * The number of {@link Tile} that can be revealed
     */
    public final int revealableTile;

    /**
     * The {@link Panel} holding this.
     */
    public final Panel panel;

    /**
     * An 2D {@code Array} of {@link Tile}
     */
    private final Tile[][] board;

    /**
     * {@link MTimer}
     */
    private MTimer timer = new MTimer();

    /**
     * A boolean determining if the game is over.
     */
    private boolean isGameOver;

    /**
     * The number of {@link Tile} that are revealed
     */
    private int numReveal;

    /**
     * The number of bombs left to flag. <br>
     * This number can go negative, signifying there are more flags then there are
     * bombs.
     */
    private int numFlag;

    /**
     * Creates a {@code Board} initialising all atributes.
     * 
     * @param panel    is the {@link Panel}
     * @param rowMax   is the maximum number of rows.
     * @param colMax   is the maximum number of columns.
     * @param numBombs is the number of bombs on the board.
     * 
     * @throws IndexOutOfBoundsException if {@code rowMax} is less than 3 or
     *                                   {@code colMax} is less than 3.
     * @throws IllegalArgumentException  if {@code panel} is {@code null} or is
     *                                   {@code numBombs} is greater ({@code rowMax}
     *                                   * {@code colMax} - 1) or less than 1.
     */
    public Board(Panel panel, int rowMax, int colMax, int numBombs)
	    throws IndexOutOfBoundsException, IllegalArgumentException {
	this.panel = Objects.requireNonNull(panel, "Board must be on MinesweeperPanel");
	
	if (rowMax < 3)
	    throw new IndexOutOfBoundsException("Illegal maximum number of rows: " + rowMax);
	else
	    this.rowMax = rowMax;
	
	if (colMax < 3)
	    throw new IndexOutOfBoundsException("Illegal maximun number of columns: " + colMax);
	else
	    this.colMax = rowMax;
	
	if (numBombs > this.rowMax * this.colMax - 1 || numBombs < 1)
	    throw new IllegalArgumentException("Illegal number of bombs: " + numBombs);
	else
	    this.numBombs = numBombs;

	this.revealableTile = (this.rowMax * this.colMax) - this.numBombs;
	this.board = new Tile[this.rowMax][this.colMax];

	this.createBoard();
	this.reset();
    }

    /**
     * Checks if the game is won.
     */
    private void checkGameOver() {
	if (this.isGameOver)
	    return;
	if (this.revealableTile != this.numReveal)
	    return;
	if (this.timer.isRunning)
	    this.setTimer();
	
	JTextArea jta = new JTextArea("You Win!\n" + this.panel.getTimeLabel().getText());
	jta.setOpaque(false);
	JOptionPane.showMessageDialog(null, jta, "Congradulations!", JOptionPane.PLAIN_MESSAGE, null);
	this.setGameOver(true);
    }

    /**
     * Count the number of bombs directly surrounding a tile.
     * 
     * @param tile is the center tile
     * 
     * @return the number of bombs directly surrounding {@code tile}.
     */
    private int count(Tile tile) {
	int count = 0;

	for (int i = tile.getRow() - 1; i < tile.getRow() + 2; i++) {
	    for (int j = tile.getCol() - 1; j < tile.getCol() + 2; j++) {
		try {
		    count += this.board[i][j].isBomb() ? 1 : 0;
		} catch (ArrayIndexOutOfBoundsException aioobe) {
		    continue;
		}
	    }
	}
	return tile.setCount(count);
    }

    /**
     * Place all {@link Tile} into {@link #board}
     */
    private void createBoard() {
	Minesweeper.logger.info("Creaing Board");
	for (int i = 0; i < this.board.length; ++i)
	    for (int j = 0; j < this.board[i].length; ++j)
		this.board[i][j] = new Tile(this.panel, i, j);
    }

    /**
     * Decrement the number of flags.
     */
    public void decFlagCount() {
	--this.numFlag;
	this.panel.updateBLabel();
    }

    /**
     * Returns {@link #board}
     * 
     * @return {@code board}.
     */
    public Tile[][] getBoard() {
	return this.board;
    }

    /**
     * Determine the maximum number of columns.
     * 
     * @return {@link #colMax}
     */
    public int getColMax() {
	return this.colMax;
    }

    /**
     * Determine the number of {@link Tile} that have been flagged.
     * 
     * @return {@link #numFlag}
     */
    public int getFlags() {
	return this.numFlag;
    }

    /**
     * Determine if the game is over.
     * 
     * @return {@link #isGameOver}
     */
    public boolean getGameOver() {
	return this.isGameOver;
    }

    /**
     * Determine the number of {@link Tile} that have been revealed.
     * 
     * @return {@link #numReveal}
     */
    public int getNumReveal() {
	return this.numReveal;
    }

    /**
     * Determine the number of {@link Tile} that can be revealed.
     * 
     * @return {@link #revealableTile}
     */
    public int getRevealableTile() {
	return this.revealableTile;
    }

    /**
     * Determine the maximum number of rows.
     * 
     * @return {@link #rowMax}
     */
    public int getRowMax() {
	return this.rowMax;
    }

    /**
     * Returns {@link Tile} of {@link #board} located at ({@code row}, {@code col}).
     * 
     * @param row is the row of {@code Tile}
     * @param col is the column of {@code Tile}
     * @return {@link Tile} at position ({@code row}, {@code col}).
     */
    public Tile getTile(int row, int col) {
	return this.board[row][col];
    }

    @Override
    public int hashCode() {
	final int prime = 0x1F;
	int result = 0x1;
	result = prime * result + Arrays.deepHashCode(board);
	result = prime * result + colMax;
	result = prime * result + (isGameOver ? 0x4CF : 0x4D5);
	result = prime * result + numBombs;
	result = prime * result + numFlag;
	result = prime * result + numReveal;
	result = prime * result + ((panel == null) ? 0 : panel.hashCode());
	result = prime * result + revealableTile;
	result = prime * result + rowMax;
	result = prime * result + ((timer == null) ? 0 : timer.hashCode());
	return result;
    }

    /**
     * Increment the number of flags.
     */
    public void incFlagCount() {
	this.numFlag++;
	this.panel.updateBLabel();
    }

    /**
     * Creates a new game.
     */
    public void reset() {
	Minesweeper.logger.info("Reset board");
	
	if (this.timer.isRunning)
	    this.setTimer();

	this.isGameOver = false;
	this.numFlag = this.numBombs;
	this.numReveal = 0;

	for (Tile[] row : this.board) {
	    for (Tile tile : row) {
		tile.reset();
	    }
	}
	for (int i = 0; i < this.numBombs; i++) {
	    int x = rand.nextInt(this.rowMax), y = rand.nextInt(this.colMax);
	    while (this.board[x][y].isBomb()) {
		x = rand.nextInt(this.rowMax);
		y = rand.nextInt(this.colMax);
	    }
	    this.board[x][y].setBomb(true);
	}
    }

    /**
     * Reveal {@code tile}
     * 
     * @param tile is the {@link Tile} to reveal
     */
    public void reveal(Tile tile) {
	Minesweeper.logger.info("Reaveal tile:\t" + tile.toString());
	if (tile.isFlagged())
	    return;
	if (tile.isRevealed())
	    return;
	if (this.isGameOver)
	    return;

	if (tile.isBomb()) {
	    JOptionPane.showMessageDialog(null, "Game Over", "Game Over!", JOptionPane.PLAIN_MESSAGE, null);
	    tile.setBackground(Color.RED);
	    this.revealBomb();
	    if (this.timer.isRunning)
		this.setTimer();
	    return;
	}
	if (!this.timer.isRunning)
	    this.setTimer();

	tile.setRevealed(true);
	tile.setBorder(BorderFactory.createLoweredBevelBorder());
	this.updateTileReveal();

	if (this.count(tile) != 0)
	    tile.setIcon(Tile.numbers[tile.getCount()]);
	else
	    this.specialReveal(tile);

	this.checkGameOver();
    }

    /**
     * Reveals the locations of all bombs on {@link #board}
     */
    private void revealBomb() {
	this.isGameOver = true;
	this.panel.menu.gameOver();
	ImageIcon bombIcon = Tile.bomb;
	ImageIcon inc = Tile.incorrectFlag;
	for (Tile[] row : this.board) {
	    for (Tile tile : row) {
		if (tile.isBomb())
		    tile.setIcon(bombIcon);
		if (tile.isFlagged() && !tile.isBomb())
		    tile.setIcon(inc);
		else
		    continue;
	    }
	}
	return;
    }

    /**
     * Set {@link #isGameOver} to {@code isGameOver}.
     * 
     * @param isGameOver is the new value.
     */
    public void setGameOver(boolean isGameOver) {
	this.isGameOver = isGameOver;
    }

    /**
     * Create {@link #timer}
     */
    private void setTimer() {
	if (this.timer.isRunning) {
	    this.timer.isRunning = false;
	    this.timer.cancel();
	} else {
	    this.timer = new MTimer();
	    TimerTask task = new TimerTask() {
		@Override
		public void run() {
		    Board.this.panel.updateTLabel(++Board.this.timer.time);
		}
	    };
	    this.timer.isRunning = true;
	    this.timer.schedule(task, 0L, 0x3E8L);
	}
    }

    /**
     * Reveal all {@link Tile} directly surrounding {@code tile}.
     * 
     * @param tile is the center tile.
     */
    private void specialReveal(Tile tile) {
	Minesweeper.logger.info("Special Reveal tile:\t" + tile.toString());
	
	for (int i = tile.getRow() - 1; i < tile.getRow() + 2; i++) {
	    for (int j = tile.getCol() - 1; j < tile.getCol() + 2; j++) {
		try {
		    if (!(tile.getRow() == i && tile.getCol() == j))
			this.reveal(this.board[i][j]);
		} catch (ArrayIndexOutOfBoundsException aioobe) {
		    continue;
		}
	    }
	}
    }

    @Override
    public String toString() {
	String str = "";
	for (Tile[] row : this.board) {
	    for (Tile tile : row) {
		str += tile.getCount() + "\t";
	    }
	    str += "\n";
	}
	return str;
    }

    /**
     * Increment the number of {@link Tile} that have been <i>revealed</i>.
     */
    public void updateTileReveal() {
	this.numReveal++;
    }
}