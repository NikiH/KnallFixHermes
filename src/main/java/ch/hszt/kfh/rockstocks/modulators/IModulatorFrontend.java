package ch.hszt.kfh.rockstocks.modulators;

import javax.swing.JComponent;

import ch.hszt.kfh.rockstocks.series.TimeSeriesProviderRegistry;

public interface IModulatorFrontend {
	
	public void setRegistry(TimeSeriesProviderRegistry registry);
	
	public Object getSelectedItem();
	
	public boolean isActive();
	
	public JComponent getComponent();

}
