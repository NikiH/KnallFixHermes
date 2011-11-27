package ch.hszt.kfh.rockstocks.series.historical;

import java.util.ArrayList;
import java.util.List;

import ch.hszt.kfh.rockstocks.series.ITimeSeries;
import ch.hszt.kfh.rockstocks.series.ITimeSeriesProvider;

public class HistoricalSmiSimulationSeriesProvider implements ITimeSeriesProvider {
	
	private IHistory history;
		
	private ArrayList<ITimeSeries> series = new ArrayList<ITimeSeries>();
		
	/**
	 * Instantiiert diesen Provider. Verwendet die normale History.
	 */
	public HistoricalSmiSimulationSeriesProvider() {
		this(new History());
	}
	
	/**
	 * Instantiiert diesen Provider. Verwendet die übergebene History.
	 * @param history
	 */
	public HistoricalSmiSimulationSeriesProvider(IHistory history) {
		
		this.history = history;
		
		// Serien herstellen
		for (String isin : this.history.getIsins()) {
			
			// Preis-Serie
			HistoricalSmiPriceSimulationSeries priceSeries = new HistoricalSmiPriceSimulationSeries();
			priceSeries.setIsin(isin);
			priceSeries.setHistory(this.history);
			series.add(priceSeries);
			
			// Umsatz-Serie
			HistoricalSmiTurnoverSimulationSeries turnoverSeries = new HistoricalSmiTurnoverSimulationSeries();
			turnoverSeries.setIsin(isin);
			turnoverSeries.setHistory(this.history);
			series.add(turnoverSeries);
		}
	}
	
	@Override
	public List<ITimeSeries> getAvailableSeries() {
		return series;
	}

	@Override
	public String getName() {
		return "Simulation mit historischen Daten des SMI";
	}
	
	public ITimeSeries getPriceSeriesForIsin(String isin) {
		for (ITimeSeries s : series) {
			if (s instanceof HistoricalSmiPriceSimulationSeries) {
				if (((HistoricalSmiPriceSimulationSeries)s).getIsin() == isin) {
					return s;
				}
			}
		}
		return null;
	}
	
	public ITimeSeries getTurnoverSeriesForIsin(String isin) {
		for (ITimeSeries s : series) {
			if (s instanceof HistoricalSmiTurnoverSimulationSeries) {
				if (((HistoricalSmiTurnoverSimulationSeries)s).getIsin() == isin) {
					return s;
				}
			}
		}
		return null;
	}

}
