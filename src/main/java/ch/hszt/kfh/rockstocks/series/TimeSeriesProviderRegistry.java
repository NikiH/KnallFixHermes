package ch.hszt.kfh.rockstocks.series;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import ch.hszt.kfh.rockstocks.series.historical.HistoricalSmiPriceSimulationSeriesProvider;
import ch.hszt.kfh.rockstocks.series.historical.HistoricalSmiTurnoverSimulationSeriesProvider;

/**
 * Kennt alle verfügbaren ITimeSeriesProvider.
 * 
 * @author florian
 *
 */
public class TimeSeriesProviderRegistry {
	
	private List<ITimeSeriesProvider> registry = new ArrayList<ITimeSeriesProvider>();
	
	public TimeSeriesProviderRegistry() {
		registry.add(new HistoricalSmiPriceSimulationSeriesProvider());
		registry.add(new HistoricalSmiTurnoverSimulationSeriesProvider());
	}
	
	/**
	 * Listet alle Provider.
	 * @return
	 */
	public List<ITimeSeriesProvider> getProviders() {
		return registry;
	}
	
	public ComboBoxModel getModel(ITimeSeriesProvider provider) {
		return new SeriesModel(provider);
	}
	
	public ComboBoxModel getModel() {
		return new Model();
	}

	private class SeriesModel implements ComboBoxModel {

		private ITimeSeriesProvider provider;
		
		public SeriesModel(ITimeSeriesProvider provider) {
			this.provider = provider;
		}
		
		@Override
		public void addListDataListener(ListDataListener arg0) {
		}

		@Override
		public Object getElementAt(int arg0) {
			return provider.getAvailableSeries().get(arg0);
		}

		@Override
		public int getSize() {
			return provider.getAvailableSeries().size();
		}

		@Override
		public void removeListDataListener(ListDataListener arg0) {
		}

		@Override
		public Object getSelectedItem() {
			return null;
		}

		@Override
		public void setSelectedItem(Object anItem) {
		}
		
	}
	
	private class Model implements ComboBoxModel {

		private Object selected;
		
		@Override
		public void addListDataListener(ListDataListener arg0) {
		}

		@Override
		public Object getElementAt(int arg0) {
			return TimeSeriesProviderRegistry.this.getProviders().get(arg0);
		}

		@Override
		public int getSize() {
			return TimeSeriesProviderRegistry.this.getProviders().size();
		}

		@Override
		public void removeListDataListener(ListDataListener arg0) {
		}

		@Override
		public Object getSelectedItem() {
			return selected;
		}

		@Override
		public void setSelectedItem(Object arg0) {
			selected = arg0;
		}
		
	}

}
