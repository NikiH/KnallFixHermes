package ch.hszt.kfh.rockstocks.sources;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import ch.hszt.kfh.rockstocks.normalizers.ISampleNormalizer;
import ch.hszt.kfh.rockstocks.normalizers.SampleNormalizerFactory;

public class AudioFileSource implements ISource {

	private final int BUFFER_SIZE = 65536;

	private AudioInputStream stream;
	private ISampleNormalizer normalizer;
	
	private byte[] buffer = new byte[BUFFER_SIZE];
	private int bytesRead;
	private int totalBytesRead;
	
	public AudioFileSource(File file) {
		try {
			// stream holen
			stream = AudioSystem.getAudioInputStream(file);
			
			// normalizer holen
			normalizer = SampleNormalizerFactory.create(stream.getFormat());
			
			
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public double[] send() {
		try {
			bytesRead = stream.read(buffer, 0, buffer.length);
			
			if (bytesRead != -1) {
				totalBytesRead += bytesRead;
				return normalizer.normalize(buffer);
			}
			return null;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getTime() {
		return (int)(totalBytesRead / stream.getFormat().getSampleRate() / stream.getFormat().getSampleSizeInBits() * 8);
	}

	@Override
	public boolean isDone() {
		return bytesRead == -1;
	}

}
