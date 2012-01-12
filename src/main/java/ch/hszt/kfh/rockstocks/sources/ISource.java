package ch.hszt.kfh.rockstocks.sources;

/**
 * Repräsentiert eine Quelle von Samples.
 * @author The team
 *
 */
public interface ISource {
	
	/**
	 * Sendet eine Reihe von normalisierten Samples.
	 * @return
	 */
	double[] send();

	/**
	 * Liefert die aktuelle Zeit als Art von "Takt".
	 * @return
	 */
	int getTime();
	
	/**
	 * Zeigt an, ob noch Samples verfügbar sind oder nicht.
	 * @return
	 */
	boolean isDone();
	
	
	void rewind();
	
}
