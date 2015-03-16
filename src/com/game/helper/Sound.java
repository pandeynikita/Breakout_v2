package com.game.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Sound {

	public void playAudio() {
		InputStream in = null;
		AudioStream as = null;
		try {
			// create audio data source
			in = new FileInputStream(Constants.soundPath);
		} catch (FileNotFoundException fnfe) {
			System.out.println("The audio file was not found");
		}
		try {
			// create audio stream from file stream
			as = new AudioStream(in);
		} catch (IOException ie) {
			System.out.println("Audio stream could not be created");
		}
		AudioPlayer.player.start(as);
	}

}
