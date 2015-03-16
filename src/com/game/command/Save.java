package com.game.command;

import javax.swing.JButton;

import com.game.save.Savable;

public class Save implements Command {

	private Savable saveContainer;
	public Save(Savable saveContainer)
	{
		this.saveContainer = saveContainer;
	}

	public void execute() {
		saveContainer.save();
	}

	@Override
	public void unexecute() {
		saveContainer.load();	
	}
	

}
