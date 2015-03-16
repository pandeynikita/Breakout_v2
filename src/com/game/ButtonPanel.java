package com.game;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.game.helper.Constants;
import com.game.save.Savable;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ButtonPanel extends JPanel implements Savable {

	public JButton startAndStopButton;
	public JButton pauseButton;
	public JButton replay;
	public JButton undo;
	public JButton saveButton;
	public JButton loadButton;
	public JButton changeButton;
	private JSONArray startstopbutton;
	private Canvas canvas;

	FlowLayout flowLayout = new FlowLayout();
	BorderLayout borderLayout = new BorderLayout();

	public ButtonPanel() {

	}

	public ButtonPanel(Canvas canvas) {

		this.setLayout(borderLayout);
		JPanel jpanel = new JPanel();

		startAndStopButton = new StartAndStopButton(canvas);
		pauseButton = new PauseButton(canvas);

		replay = new ReplayButton(canvas);
		undo = new UndoButton(canvas);
		saveButton = new SaveButton(canvas, this);
		loadButton = new LoadButton(canvas, this);
		changeButton = new ChangeButton(canvas, this);

		this.add(startAndStopButton, BorderLayout.NORTH);
		this.add(pauseButton, BorderLayout.EAST);
		this.add(replay, BorderLayout.SOUTH);
		jpanel.add(undo, BorderLayout.NORTH);
		jpanel.add(saveButton, BorderLayout.CENTER);
		jpanel.add(loadButton, BorderLayout.SOUTH);
		this.add(jpanel, BorderLayout.CENTER);
		this.add(changeButton, BorderLayout.WEST);
		this.validate();
	}

	@Override
	public void save() {

		JSONObject obj = new JSONObject();
		JSONArray panelList = new JSONArray();
		JSONArray buttons = new JSONArray();

		startstopbutton = new JSONArray();
		startstopbutton = saveButtonState(this.startAndStopButton);
		JSONArray undoButton = new JSONArray();
		JSONArray replayButton = new JSONArray();
		undoButton = saveButtonState(this.undo);

		buttons.add(startstopbutton);
		buttons.add(undoButton);
		obj.put(Constants.buttons, buttons);
		try {
			FileWriter file = new FileWriter("d:\\test.json");
			file.write(obj.toJSONString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public JSONArray saveButtonState(JButton button) {
		JSONArray tempJsonArray = new JSONArray();
		tempJsonArray.add(button.getText());
		return tempJsonArray;
	}

	public void load() {
		JSONParser parser = new JSONParser();
		Canvas canvas = new Canvas();
		ButtonPanel buttonpanel = new ButtonPanel();
		JFrame frame = new JFrame();
		try {

			Object obj = parser.parse(new FileReader("d:\\test.json"));

			JSONObject jsonObject = (JSONObject) obj;

			JSONArray buttons = (JSONArray) jsonObject.get(Constants.buttons);

			System.out.println(buttons.size());

			for (Object o : buttons) {
				for (Object obj1 : (ArrayList) o) {
					JButton button = null;
					String text = obj1.toString();
					switch (text) {
					case "Stop":
						button = new StartAndStopButton();
						break;
					case "Start":
						button = new StartAndStopButton();
						break;
					case "Undo":
						button = new UndoButton(canvas);
						break;
					}
					button.setText(text);
					buttonpanel.add(button);
				}
			}

			frame.add(canvas.clock, BorderLayout.WEST);
			frame.add(canvas, BorderLayout.NORTH);

			frame.add(buttonpanel);
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
