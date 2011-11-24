package ch.hszt.kfh.rockstocks;

import java.util.List;

/***
 * Dient als Lieferant für eine Zeitreihe.
 * Die Identifikation auf der Zeitlinie wird grundsätzlich
 * durch Anzahl Sekunden seit dem Start der Wiedergabe ausgedrückt.
 * 
 * Ein Lieferant kann mehrere miteinander verknüpfte Reihen liefern.
 * 
 * @author florian
 *
 */
public interface ITimeSeriesProvider {
	
	/**
	 * Listet die von diesem Provider erhältlichen Zeitreihen.
	 * @return
	 */
	List<ITimeSeries> getAvailableSeries();
	
	/**
	 * Liefert einen Datenpunkt auf einer gegebenen Zeitreihe für einen bestimmten Zeitpunkt.
	 * @param series
	 * @param time
	 * @return
	 */
	double getValue(ITimeSeries series, int time);

}
