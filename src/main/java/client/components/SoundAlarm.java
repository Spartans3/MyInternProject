package client.components;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundAlarm {
	int secondsPassed = 0;
	Timer mytimer = new Timer();

	TimerTask task = new TimerTask() {
		public void run() {
			secondsPassed++;
			if (secondsPassed == 120) {
				File riho = new File(
						"C:\\Users\\LENOVO\\eclipse-workspace\\InternProject\\src\\main\\resources\\sounds\\Work.wav");

				Clip clip = null;
				try {
					clip = AudioSystem.getClip();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					clip.open(AudioSystem.getAudioInputStream(riho));
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				clip.start();
			}
		}
	};

	public void Start() {

		mytimer.scheduleAtFixedRate(task, 1000, 1000);

	}

	public void Restart() {
		secondsPassed = 0;

	}
}
