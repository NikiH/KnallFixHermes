package ch.hszt.kfh.rockstocks.normalizers;

/**
 * Der im Prototyp verwendete Normalizer.
 * Wird dann wahrscheinlich durch einen generischeren ersetzt werden.
 * 
 * @author florian
 *
 */
public class BigEndian16BitNormalizer implements ISampleNormalizer {

	private static final int BYTES = 2;
	
	@Override
	public double[] normalize(byte[] bytes) {
		double[] output = new double[bytes.length / BYTES];
		for (int i = 0; i < output.length; i++) {
			// klappt momentan nur für BYTES == 2....
			output[i] = ((bytes[i*2] & 0xFF) | (bytes[i*2 + 1] << 8)) / 32768.0D;
		}
		return output;
	}

	@Override
	public byte[] denormalize(double samples[]) {
		byte[] output = new byte[samples.length * BYTES];
		for (int i = 0; i < samples.length; i++) {
			int sm = (int)Math.round(samples[i] * 32767.0);
			// klappt momentan nur für BYTES == 2...
			output[i*2] = (byte)(sm & 0xFF);
			output[i*2 + 1] = (byte) ((sm >> 8) & 0xFF);
		}
		return output;
	}

	@Override
	public int getBytesPerSample() {
		return BYTES;
	}

}
