package main.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

import javax.swing.JOptionPane;

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
	switch (e.getKeyChar()) {
	case KeyEvent.VK_ESCAPE:
	    this.panel.menu.actionPerformed(null);
	    return;
	case 'r':
	    switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?", "",
		    JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null)) {
	    case JOptionPane.YES_OPTION:
		this.panel.menu.reset();
		this.panel.getBoard().reset();
	    default:
		return;
	    }
	case 'q':
	    switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "", JOptionPane.YES_NO_OPTION,
		    JOptionPane.PLAIN_MESSAGE, null)) {
	    case JOptionPane.YES_OPTION:
		System.exit(0);
	    default:
		return;
	    }
	}
    }
}