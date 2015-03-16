package com.game;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


import com.game.command.Save;
import com.game.save.Savable;

public class ChangeButton extends JButton {
	private static final long serialVersionUID = 1L;
	private final String CHANGE_BUTTON_TEXT = "Change";
	
	private Canvas canvas;
	private Savable savable;
	private ButtonPanel buttonPanel;
	

	public ChangeButton(Canvas canvas,ButtonPanel buttonPanel) {
		super();
		this.canvas = canvas;
		this.buttonPanel = buttonPanel;
		this.setText(CHANGE_BUTTON_TEXT);
		this.addActionListener(new changeButtonActionListener());
	}
	
	
	class changeButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println(buttonPanel.getLayout());
			//System.out.println("buttonPanel.flowLayout"+buttonPanel.flowLayout);
			if(buttonPanel.getLayout()== buttonPanel.flowLayout)
			{
				buttonPanel.setLayout(buttonPanel.borderLayout);
				buttonPanel.validate();
				System.out.println("Hello");
			}
			else
				buttonPanel.setLayout(buttonPanel.flowLayout);
			
			buttonPanel.validate();
			
		}
	}

}
