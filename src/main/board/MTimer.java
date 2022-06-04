package main.board;

import java.util.Timer;

/**
 * {@link Timer} to track how long player takes to solve a board.
 * 
 * @author Mr. P&#x03B9;&#x03B7;&#x03B5;&#x03B1;&#x03C1;&#x03C1;l&#x03BE;
 * @version 2022 06 03
 */
public class MTimer extends Timer {
    /**
     * The amount of seconds that have elapsed since the start of the timer.
     */
    public int time;

    /**
     * A boolean determining if this is running.
     */
    public boolean isRunning;

    /**
     * Create a {@code Mtimer} with {@code name}
     * 
     * @param name the name of the associated thread
     * 
     * @throws NullPointerException if {@link Timer#Timer(String)} throws
     */
    public MTimer() throws NullPointerException {
	super("Timer");
	this.time = 0;
	this.isRunning = false;
    }
}