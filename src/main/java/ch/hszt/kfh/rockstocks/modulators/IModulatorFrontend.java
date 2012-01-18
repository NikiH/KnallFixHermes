package ch.hszt.kfh.rockstocks.modulators;

import javax.swing.JComponent;
import ch.hszt.kfh.rockstocks.series.TimeSeriesProviderRegistry;

/**
 * Interface für Frontends der einzelnen Modulatoren.
 * 
 * @author The Team
 * 
 */

public interface IModulatorFrontend {
	
	/**
	 * Setzt die registrierten Provider für die grafische Auswahl.
	 * @param TimeSeriesProviderRegistry
	 *
	 */	
	public void setRegistry(TimeSeriesProviderRegistry registry);
	
	/**
	 * Gibt die aktuelle Auswahl zurück.
	 * 
	 * @return Object
	 */	
	public Object getSelectedItem();
	
	/**
	 * Gibt zurück, ob die Checkbox aktiviert wurde.
	 * 
	 * @return boolean
	 */	
	public boolean isActive();
	
	/**
	 * Gibt das ganze Frontend zurück.
	 * 
	 * @return JComponent
	 */	
	public JComponent getComponent();
	
	/**
	 * Initialisiert das Panel
	 * 
	 */
	public void init();

}
