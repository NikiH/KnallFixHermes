package ch.hszt.kfh.rockstocks.modulators;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.hszt.kfh.rockstocks.series.ITimeSeriesProvider;
import ch.hszt.kfh.rockstocks.series.TimeSeriesProviderRegistry;

public class PitchShiftModulatorFrontend extends JPanel implements IModulatorFrontend {

	private JLabel label;
	private JComboBox dataRow;
	private JComboBox data;
	private JCheckBox activated;
	private TimeSeriesProviderRegistry registry;
	
	public PitchShiftModulatorFrontend(){
		init();
	};
	
	private void init(){
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		label = new JLabel("Pitch");
		add(label);
		
		dataRow = new JComboBox();
		add(dataRow);
		dataRow.setModel(registry.getModel());
		dataRow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				data.setModel(registry.getModel((ITimeSeriesProvider)dataRow.getSelectedItem()));
			}
			
		});
		
		data = new JComboBox();
		add(data);
		
		activated = new JCheckBox("aktiviert");
		add(activated);
	}
	
	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public JComponent getComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRegistry(TimeSeriesProviderRegistry registry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

}
