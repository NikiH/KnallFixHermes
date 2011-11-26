package ch.hszt.kfh.rockstocks.normalizers;

/**
 * Repräsentiert einen Normalizer/Denormalizer für Samples.
 * 
 * @author florian
 *
 */
public interface ISampleNormalizer {
	
	/**
	 * Normalisiert.
	 * @param bytes
	 * @return
	 */
	public double[] normalize(byte[] bytes);
	
	/**
	 * Denormalisiert.
	 * @param sample
	 * @return
	 */
	public byte[] denormalize(double[] sample);
	
	/**
	 * Liefert die Anzahl Bytes pro denormalisiertem Sample.
	 */
	public int getBytesPerSample();

}
