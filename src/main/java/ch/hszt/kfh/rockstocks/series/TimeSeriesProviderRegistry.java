package ch.hszt.kfh.rockstocks.series;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

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
	
	public ComboBoxModel getModel() {
		return new Model();
	}
	
	private class Model implements ComboBoxModel {

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
			return null;
		}

		@Override
		public void setSelectedItem(Object arg0) {
		}
		
	}

}
