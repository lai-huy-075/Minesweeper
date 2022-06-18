package main.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

import main.Minesweeper;
import main.board.Board;
import main.board.Panel;
import main.board.Tile;

/**
 * {@link MouseListener} for {@link Tile}
 * 
 * @author Mr. P&#x03B9;&#x03B7;&#x03B5;&#x03B1;&#x03C1;&#x03C1;l&#x03BE;
 * @version 2022 06 03
 */
public class Mouse implements MouseListener {
	/**
	 * {@link Board} interacting with
	 */
	public final Board board;

	/**
	 * {@link Tile} interacting with
	 */
	public final Tile tile;

	/**
	 * Constructor
	 * @param panel {@link Panel} this is interacting with
	 * @param board {@link Board} this is interacting with
	 * @param tile {@link Tile} this is interacting with
	 */
	public Mouse(Panel panel, Board board, Tile tile) {
		this.board = Objects.requireNonNull(board, "Board cannot be null");
		this.tile = Objects.requireNonNull(tile, "Tile cannot be null");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Minesweeper.logger.info("Mouse button:\t" + e.getButton());
		switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				this.board.reveal(this.tile);
				return;
			case MouseEvent.BUTTON3:
				if (this.tile.isRevealed())
					return;
				if (this.board.getGameOver())
					return;
				if (this.tile.isFlagged()) {
					this.board.incFlagCount();
					this.tile.toggleFlagged();
				} else {
					this.board.decFlagCount();
					this.tile.toggleFlagged();
				}
				return;
			default:
				return;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		return;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		return;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (this.board.getGameOver())
			return;

		this.tile.getModel().setPressed(true);
		Panel.menu.setIcon(Panel.menuClick);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.board.getGameOver())
			return;

		this.tile.getModel().setPressed(false);
		Panel.menu.setIcon(Panel.menuDefault);
	}
}