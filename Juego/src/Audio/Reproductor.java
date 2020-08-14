package Audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Reproductor {
	
	private Clip clip;
		
	public Reproductor(String s) {
			
		try {
				
			AudioInputStream ais =
				AudioSystem.getAudioInputStream(
				getClass().getResource(
					s
				)
			);
		AudioFormat baseFormat = ais.getFormat();
		AudioFormat decodeFormat = new AudioFormat(
			AudioFormat.Encoding.PCM_SIGNED,
			baseFormat.getSampleRate(),
			16,
			baseFormat.getChannels(),
			baseFormat.getChannels() * 2,
			baseFormat.getSampleRate(),
			false
			);
			AudioInputStream dais =
			AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	public void play(boolean b) {
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
		
		if(b)
		clip.loop(Clip.LOOP_CONTINUOUSLY); //Usar while en start y loop
	}
		
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
		
	public void close() {
		stop();
		clip.close();
	}
		
}
