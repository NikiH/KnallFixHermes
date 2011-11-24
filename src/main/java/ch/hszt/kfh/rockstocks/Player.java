package ch.hszt.kfh.rockstocks;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private ISource source;
	
	private IDrain drain;
	
	private ArrayList<IModulator> modulators = new ArrayList<IModulator>();
	
	public List<IModulator> getModulators() {
		return modulators;
	}
	
	public void stop() {
		
	}
	
	public void play() {
		
		while (true) {
			
			double[] data = source.send();
			
			for (IModulator modulator : modulators) {
				data = modulator.modulate(data);
			}
			
			drain.receive(data);
			
		}
		
		
	}

}
