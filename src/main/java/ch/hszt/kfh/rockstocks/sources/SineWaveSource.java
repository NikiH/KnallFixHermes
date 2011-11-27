package ch.hszt.kfh.rockstocks.sources;

public class SineWaveSource implements ISource {

	private final int BUFFER_SIZE = 65536;
	private final double FREQ = 440;
	private final double RATE = 44100;
	
	private long samplesSent;

	@Override
	public double[] send() {
		double[] data = new double[BUFFER_SIZE];
		for (int i = 0; i < data.length; i++) {
			data[i] = Math.sin(RATE / FREQ * 2 * Math.PI);
		}
		return data;
	}

	@Override
	public int getTime() {
		return (int)(samplesSent / 44100);
	}

	@Override
	public boolean isDone() {
		return false;
	}

}
