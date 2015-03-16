package com.game.time;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Clock extends JPanel implements TimeObserver {

	private long hours = 0;
	private long seconds = 0;
	private long minutes;
	private JLabel title;
	private String ClockFormat = "0:0:0";

	public Clock() {
		super();
		title = new JLabel(ClockFormat);
		this.add(title);
	}

	public void ticktock(TimeHistory timehistory) {
		// figure out hours, minutes and seconds

		hours = timehistory.getHours();
		minutes = timehistory.getMinutes();
		seconds = timehistory.getSeconds();

		title.setText(hours + ":" + minutes + ":" + seconds);
	}

}