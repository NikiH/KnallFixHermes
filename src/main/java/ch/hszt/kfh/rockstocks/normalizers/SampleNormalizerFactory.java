package ch.hszt.kfh.rockstocks.normalizers;

import javax.sound.sampled.AudioFormat;

/**
 * Factory für die Normalizers gemäss Audioformat.
 * 
 * @author florian
 *
 */
public class SampleNormalizerFactory {
	
	public static ISampleNormalizer create(AudioFormat format) {
		
		if (!format.isBigEndian() && format.getSampleSizeInBits() == 16) {
			return new BigEndian16BitNormalizer();
		}
		
		return null;
		
	}

}
