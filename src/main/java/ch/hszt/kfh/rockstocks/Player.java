package ch.hszt.kfh.rockstocks;

import java.util.ArrayList;
import java.util.List;

import ch.hszt.kfh.rockstocks.drains.IDrain;
import ch.hszt.kfh.rockstocks.modulators.IModulator;
import ch.hszt.kfh.rockstocks.sources.ISource;

/**
 * Der Abspieler ("Client" der Patterns Chain of Responsibility bzw. Strategy).
 * 
 * @author The team
 *
 */
public class Player {
	
	private ISource source;
	
	private IDrain drain;
	
	private ArrayList<IModulator> modulators = new ArrayList<IModulator>();
	
	/**
	 * Liefert alle Modulatoren.
	 * @return
	 */
	public List<IModulator> getModulators() {
		return modulators;
	}
	
	/**
	 * Liefert die Source.
	 * @return
	 */
	public ISource getSource() {
		return source;
	}
	
	/**
	 * Setzt die Source.
	 * @param source
	 */
	public void setSource(ISource source) {
		this.source = source;
	}
	
	/**
	 * Liefert die Drain.
	 * @return
	 */
	public IDrain getDrain() {
		return drain;
	}
	
	/**
	 * Setzt die Drain.
	 */
	public void setDrain(IDrain drain) {
		this.drain = drain;
	}
	
	/**
	 * Spielt die ganze Kette durch bis keine Source-Samples mehr verfügbar sind.
	 */
	public void play() {
		
		isPause = false;
		
		while (!source.isDone() && !isPause) {
			
			double[] data = source.send();
			int time = source.getTime();
			
			for (IModulator modulator : modulators) {
				data = modulator.modulate(data, time);
			}
			
			drain.receive(data);
			
		}
		
		
	}
	
	private boolean isPause = false;
	
	public void pause() {
		isPause = true;
	}

	
}
