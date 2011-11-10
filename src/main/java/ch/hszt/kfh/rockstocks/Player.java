package ch.hszt.kfh.rockstocks;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player extends Thread {

	private String filename;

	private Position curPosition;

	private final int EXTERNAL_BUFFER_SIZE = 65536; //524288; // 128Kb

	public Player(String wavfile) {
		filename = wavfile;
		curPosition = Position.NORMAL;
	}

	public Player(String wavfile, Position p) {
		filename = wavfile;
		curPosition = p;
	}
	
	public void run() {

		File soundFile = new File(filename);
		if (!soundFile.exists()) {
			System.err.println("Wave file not found: " + filename);
			return;
		}

		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		System.out.print(audioInputStream.getFormat().getSampleSizeInBits());
		System.out.print(audioInputStream.getFormat().isBigEndian());

		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		if (auline.isControlSupported(FloatControl.Type.PAN)) {
			FloatControl pan = (FloatControl) auline
					.getControl(FloatControl.Type.PAN);
			if (curPosition == Position.RIGHT)
				pan.setValue(1.0f);
			else if (curPosition == Position.LEFT)
				pan.setValue(-1.0f);
		}

		auline.start();
		int nBytesRead = 0;
		byte[] buffer = new byte[EXTERNAL_BUFFER_SIZE];

		PitchShift2 pitchShift = new PitchShift2(8192);
		pitchShift.setFftFrameSize(2048);
		pitchShift.setOversampling(2);
		pitchShift.setSampleRate(44100);
		
		
		double pitch  =1.0;
		
		double[] pitches = new double[] {
				1.0 / 1.0,
				9.0 / 8.0,
				5.0 / 4.0,
				4.0 / 3.0,
				3.0 / 2.0,
				5.0 / 3.0,
				15.0 / 8.0,
				2.0 / 1.0
		};
		
		int chunkCount = 0;
		 
		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(buffer, 0, buffer.length);
				
				
				
				double[] input = new double[nBytesRead / 2];
				
				// reconstruct samples
				for (int offset = 0; offset < input.length; offset++) {
					int ioff = offset * 2;
					// http://www.jsresources.org/faq_audio.html#reconstruct_samples
					input[offset] = ((buffer[ioff] & 0xFF) | (buffer[ioff+1] << 8)) / 32768.0D;
				}
				
				double[] output = new double[input.length];
				
				for (int i = 0; i< output.length; i++) {
					output[i] = input[i];
				}
				
				//pitch = pitch == 1.0 ? 2.0 : 1.0;
				pitch = pitches[chunkCount % pitches.length];
				chunkCount++;
				System.out.println(pitch);
				
				/*
				double[] shiftInput = new double[input.length * 2];
				double[] shiftOutput = new double[input.length * 2];
				for (int i = 0; i < input.length; i++) {
					shiftInput[i * 2] = input[i];
				}*/
				pitchShift.setPitchShift(pitch);
				pitchShift.smbPitchShift(input, output, 0, input.length);
				/*
				pitchShift.smbPitchShift(
						pitch, 
						null, 
						pitch, 
						null, 
						0, 
						0, 
						shiftInput.length, 
						32, 
						shiftInput, shiftOutput
				);*/
				/*
				for (int i = 0; i < output.length; i++) {
					output[i] = shiftOutput[i * 2];
				}
				*/
				// store samples
				for (int i = 0; i < output.length; i++) {
					int sm = (int)Math.round(output[i] * 32767.0);
					
					/*
					byte lower = (byte) (sm & 0xFF);
					byte higher = (byte) ((sm >> 8) & 0xFF);
					if (lower != buffer[i * 2 + 0]) {
						System.out.println( i + " Lower: " + lower + " / " + buffer[i * 2 + 0]);
					}
					if (higher != buffer[i * 2 + 1]) {
						System.out.println( i + " Higher: " + higher + " / " + buffer[i * 2 + 1]);
					}*/
					buffer[i*2 + 0] = (byte) (sm & 0xFF);
					buffer[i*2 + 1] = (byte) ((sm >> 8) & 0xFF);
				}
				
/*				for (int offset = 0; offset < output.length; offset = offset + 2) {
					int ioff = offset * 2;
					// http://www.jsresources.org/faq_audio.html#reconstruct_samples
					float sample = ((buffer[ioff] & 0xFF) | (buffer[ioff+1] << 8)) / 32768.0F;
					output[offset] = buffer[ioff];
					output[offset + 1] = buffer[ioff + 1];
					
					// http://www.jsresources.org/faq_audio.html#short_to_byte
				}
			*/
				
			/*	byte[] output = new byte[nBytesRead / 2];
				
				for (int offset = 0; offset < output.length; offset = offset + 2) {
					int ioff = offset * 2;
					// http://www.jsresources.org/faq_audio.html#reconstruct_samples
					float sample = ((buffer[ioff] & 0xFF) | (buffer[ioff+1] << 8)) / 32768.0F;
					output[offset] = buffer[ioff];
					output[offset + 1] = buffer[ioff + 1];
					
					// http://www.jsresources.org/faq_audio.html#short_to_byte
				}*/
				
/*				byte[] output = new byte[nBytesRead / 2];

				for (int offset = )
				
				float sample =
					(  (abData[offset + 0] & 0xFF)
					 | (abData[offset + 1] << 8) )
					/ 32768.0F;
				*/
/*
							float speed = 0.5f;

				byte[] output = new byte[(int)((float)nBytesRead / speed)];
			
				for (int i = 0; i < output.length; i++) {
					output[i] = (byte) ((byte)(buffer[i] + buffer[i*2]) / 2);
				}
				*/
				if (nBytesRead >= 0)
					auline.write(buffer, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}

	}
	
	/*
	private void transform(float[] workspace, int frameSize, boolean mode) {
		for (int i = 2; i < 2*frameSize-2; i += 2) {
			int j = 0;
			for (int bitm = 2; bitm < 2*frameSize; bitm <<= 1) {
				if ((i & bitm) != 0) j++;
				j <<= 1;
			}
			if (i < j) {
				p1 = fftBuffer+i; p2 = fftBuffer+j;
				temp = *p1; *(p1++) = *p2;
				*(p2++) = temp; temp = *p1;
				*p1 = *p2; *p2 = temp;
			}
		}
		
	}*/

}
