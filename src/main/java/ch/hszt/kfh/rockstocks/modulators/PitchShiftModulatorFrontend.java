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
	
	public PitchShiftModulatorFrontend(TimeSeriesProviderRegistry reg){
		setRegistry(reg);
		init();
	};
	
	/**
	 * Initialisiert das Panel
	 * 
	 */
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
	
	/**
	 * Gibt zurück, ob die Checkbox aktiviert wurde.
	 * 
	 * @return boolean
	 */	
	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return activated.isEnabled();
	}

	/**
	 * Gibt das ganze Frontend zurück.
	 * 
	 * @return JComponent
	 */
	@Override
	public JComponent getComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	/**
	 * Setzt die registrierten Provider für die grafische Auswahl.
	 * @param TimeSeriesProviderRegistry
	 *
	 */	
	@Override
	public void setRegistry(TimeSeriesProviderRegistry registry) {
		this.registry = registry;
		
	}

	/**
	 * Gibt die aktuelle Auswahl zurück.
	 * 
	 * @return Object
	 */	
	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return data.getSelectedItem();
	}

}
