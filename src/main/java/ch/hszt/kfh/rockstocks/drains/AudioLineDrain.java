package ch.hszt.kfh.rockstocks.drains;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import ch.hszt.kfh.rockstocks.normalizers.ISampleNormalizer;
import ch.hszt.kfh.rockstocks.normalizers.SampleNormalizerFactory;

/**
 * Implementiert eine javax.sound.sampled-AudioLine als output.
 * 
 * @author florian
 *
 */
public class AudioLineDrain implements IDrain {

	private ISampleNormalizer normalizer;
	
	private SourceDataLine line;

	public AudioLineDrain() {
		AudioFormat format = new AudioFormat(44100, 16, 2, true, false);
		
		normalizer = SampleNormalizerFactory.create(format);
		
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try {
			line = (SourceDataLine)AudioSystem.getLine(info);
			line.open();
			line.start();
			
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void receive(double[] data) {
		byte[] bytes = normalizer.denormalize(data);
		line.write(bytes, 0, bytes.length);
	}

}
