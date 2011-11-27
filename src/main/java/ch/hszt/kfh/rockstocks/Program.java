package ch.hszt.kfh.rockstocks;

import java.io.File;

import ch.hszt.kfh.rockstocks.drains.AudioLineDrain;
import ch.hszt.kfh.rockstocks.modulators.IModulator;
import ch.hszt.kfh.rockstocks.modulators.PitchShiftModulator;
import ch.hszt.kfh.rockstocks.series.historical.HistoricalSmiSimulationSeriesProvider;
import ch.hszt.kfh.rockstocks.sources.AudioFileSource;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File file = new File("/home/florian/host/Track01.wav");
		
		Player player = new Player();
		player.setSource(new AudioFileSource(file));
		//player.setSource(new SineWaveSource());
		player.setDrain(new AudioLineDrain());
		
		IModulator mod = new PitchShiftModulator();
		mod.setTimeSeries(new HistoricalSmiSimulationSeriesProvider().getPriceSeriesForIsin("CH0012255151"));
		player.getModulators().add(mod);
		
		player.play();
	
	}

}
