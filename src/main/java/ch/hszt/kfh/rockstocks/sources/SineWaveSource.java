package ch.hszt.kfh.rockstocks.sources;

public class SineWaveSource implements ISource {

	private final int BUFFER_SIZE = 65536;
	private final double FREQ = 220;
	private final double RATE = 44100;
	
	private long samplesSent;
	
	private int moduloOffset;
	
	public SineWaveSource() {
		moduloOffset = (int) (BUFFER_SIZE % FREQ);
	}

	@Override
	public double[] send() {
		
		double[] data = new double[BUFFER_SIZE];

		for (int i = 0; i < BUFFER_SIZE; i++) {
			data[i] = 0.5 * Math.sin(2.0 * Math.PI * FREQ * (double)(i + moduloOffset) / RATE);
		}
		
		samplesSent += BUFFER_SIZE;
		
		return data;
	}
	
	@Override
	public int getTime() {
		return (int)(samplesSent / RATE);
	}

	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void rewind() {
		samplesSent = 0;
		moduloOffset = 0;
		
	}

}
