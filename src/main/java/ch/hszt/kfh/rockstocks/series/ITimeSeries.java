package ch.hszt.kfh.rockstocks.series;

/**
 * Repräsentiert eine Zeitreihe.
 * 
 * @author florian
 *
 */
public interface ITimeSeries {
	
	String getName();

	/**
	 * Liefert einen Datenpunkt auf einer gegebenen Zeitreihe für einen bestimmten Zeitpunkt.
	 * @param time
	 * @return
	 */
	double getValue(int time);

}
