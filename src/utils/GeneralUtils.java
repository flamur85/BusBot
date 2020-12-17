package utils;

import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GeneralUtils {

	public int getRandomTime(int low, int high) {
		Random r = new Random();
		return r.nextInt(high - low) + low;
	}

	public void playAlertSound() {
		try {
			Clip clip = AudioSystem.getClip();
			for (int x = 0; x < 3; x++) {
				clip.open(AudioSystem.getAudioInputStream(new File("sound.wav")));
				clip.start();
				while (!clip.isRunning())
					Thread.sleep(10);
				while (clip.isRunning())
					Thread.sleep(10);
				clip.close();
				// Thread.sleep(5000);
			}
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}
}
