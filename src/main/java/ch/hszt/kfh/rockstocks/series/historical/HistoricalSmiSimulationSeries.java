package ch.hszt.kfh.rockstocks.series.historical;

import ch.hszt.kfh.rockstocks.series.ITimeSeries;

/**
 * Repräsentiert eine historische Datenreihe.
 * 
 * @author florian
 *
 */
abstract class HistoricalSmiSimulationSeries implements ITimeSeries {
	
	private String isin;
	private IHistory history;
	
	public String getIsin() {
		return isin;
	}
	
	public IHistory getHistory() {
		return history;
	}
	
	public void setIsin(String isin) {
		this.isin = isin;
	}
	
	public void setHistory(IHistory history) {
		this.history = history;
	}
	
	/**
	 * Liefert denn Datenreihennamen (unabhängig vom Instrument).
	 * @return
	 */
	protected abstract String getSeriesDataTypeName();
	
	/**
	 * Selektiert das richtige Datum aus dem gelieferten Record.
	 * @param record
	 * @return
	 */
	protected abstract double selectData(HistoryRecord record);
	
	@Override
	public String getName() {
		return history.get(isin).getInstrumentName() + " - " + getSeriesDataTypeName();
	}

	@Override
	public double getValue(int time) {
		// cumulative return: x / x0
		HistoryRecord r0 = history.get(isin).getRecord(0);
		HistoryRecord r = history.get(isin).getRecord(time);
		
		if (r0 == null || r == null) {
			return 1;
		}
		
		return selectData(r) / selectData(r0);
	}

}
