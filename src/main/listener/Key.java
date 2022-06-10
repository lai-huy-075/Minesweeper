package main.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

import javax.swing.JOptionPane;

import main.Minesweeper;
import main.board.Panel;

public class Key implements KeyListener {
    public final Panel panel;

    public Key(Panel panel) {
	this.panel = Objects.requireNonNull(panel, "Panel cannot be null");
    }

    @Override
    public void keyPressed(KeyEvent e) {
	return;
    }

    @Override
    public void keyReleased(KeyEvent e) {
	return;
    }

    @Override
    public void keyTyped(KeyEvent e) {
	Minesweeper.logger.info("Typed:\t" + e.getKeyChar());

	switch (e.getKeyChar()) {
	case KeyEvent.VK_ESCAPE:
	    this.panel.displayMenu();
	    return;
	case 'r':
	    switch (JOptionPane.showConfirmDialog(this.panel, "Are you sure you want to reset?", "",
		    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, Minesweeper.icon)) {
	    case JOptionPane.YES_OPTION:
		Panel.menu.setIcon(Panel.menuDefault);;
		this.panel.board.reset();
		return;
	    default:
		return;
	    }
	case 'q':
	    switch (JOptionPane.showConfirmDialog(this.panel, "Are you sure you want to quit?", "",
		    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, Minesweeper.icon)) {
	    case JOptionPane.YES_OPTION:
		System.exit(0);
		return;
	    default:
		return;
	    }
	}
    }
}