package main.listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import main.Minesweeper;

public class Window implements WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {
	Minesweeper.logger.info("Window opened");
    }

    @Override
    public void windowClosing(WindowEvent e) {
	Minesweeper.logger.info("Window closing");
	
	switch (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Careful!",
		JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null)) {
	case JOptionPane.YES_OPTION:
	    System.exit(0);
	    return;
	default:
	    return;
	}
    }

    @Override
    public void windowClosed(WindowEvent e) {
	Minesweeper.logger.info("Window closed.");
    }

    @Override
    public void windowIconified(WindowEvent e) {
	Minesweeper.logger.info("Window iconified.");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
	Minesweeper.logger.info("Window deiconified.");
    }

    @Override
    public void windowActivated(WindowEvent e) {
	Minesweeper.logger.info("Window activated.");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
	Minesweeper.logger.info("Window deactivated.");
    }

}
