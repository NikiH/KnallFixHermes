package ch.hszt.kfh.rockstocks.modulators;

import ch.hszt.kfh.rockstocks.series.ITimeSeries;

/**
 * Kann eine Reihe von aufeinanderfolgenden normalisierten Samples modulieren.S
 * 
 * @author The Team
 *
 */
public interface IModulator {
	
	/**
	 * Moduliert eine Reihe von normalisierten Samples.
	 * @param data
	 * @param time
	 * @return
	 */
	double[] modulate(double[] data, int time);

	/**
	 * Liefert die dem Modulator zugeordnete Datenreihe.
	 * @return
	 */
	ITimeSeries getTimeSeries();
	
	/**
	 * Ordnet dem Modulator eine Datenreihe zu.
	 * @param series
	 */
	void setTimeSeries(ITimeSeries series);
	
}
