package ch.hszt.kfh.rockstocks.series;

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
	 * Liefert den Namen der Zeitreihe.
	 * @return
	 */
	String getName();
	
}
