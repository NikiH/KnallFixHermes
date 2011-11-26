package ch.hszt.kfh.rockstocks.series.historical;

import java.util.HashMap;

/**
 * Repräsentiert die simulierte History für ein Instrument.
 * 
 * @author florian
 *
 */
public class HistoryCollection {
	
	private HashMap<Integer, HistoryRecord> series = new HashMap<Integer, HistoryRecord>();
	
	private int time0;
	private String instrumentName;
	
	/**
	 * Instantiiert eine History.
	 * @param time0 Der "Nullpunkt" in Excel-Time
	 * @param instrumentName Der Name des Instruments.
	 */
	public HistoryCollection(int time0, String instrumentName) {
		this.time0 = time0;
		this.instrumentName = instrumentName;
	}
	
	/**
	 * Liefert den Namen des Instruments.
	 * @return
	 */
	public String getInstrumentName() {
		return instrumentName;
	}
	
	/**
	 * Shortcut-Add-Funktion für einen Datenpunkt.
	 * @param excelTime Der Zeitpunkt als Excel-Time
	 * @param price Der Schluss-Preis zum entsprechenden Zeitpunkt.
	 * @param turnover Der kumulierte Handelsumsatz (Preis * # Shares) zum entsprechenden Zeitpunkt.
	 */
	public void add(int excelTime, double price, double turnover) {
		series.put(excelTime - time0, new HistoryRecord(price, turnover));
	}
	
	/**
	 * Liefert einen Satz für einen Zeitpunkt.
	 * @param time
	 * @return
	 */
	public HistoryRecord getRecord(int time) {
		if (series.containsKey(time)) {
			return series.get(time);
		}
		return null;
	}
	
}
