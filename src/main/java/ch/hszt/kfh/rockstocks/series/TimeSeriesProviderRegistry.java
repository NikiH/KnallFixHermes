package ch.hszt.kfh.rockstocks.series;

import java.util.ArrayList;
import java.util.List;

import ch.hszt.kfh.rockstocks.series.historical.HistoricalSmiSimulationSeriesProvider;

/**
 * Kennt alle verfügbaren ITimeSeriesProvider.
 * 
 * @author florian
 *
 */
public class TimeSeriesProviderRegistry {
	
	private List<ITimeSeriesProvider> registry = new ArrayList<ITimeSeriesProvider>();
	
	public TimeSeriesProviderRegistry() {
		registry.add(new HistoricalSmiSimulationSeriesProvider());
	}
	
	/**
	 * Listet alle Provider.
	 * @return
	 */
	public List<ITimeSeriesProvider> getProviders() {
		return registry;
	}

}
