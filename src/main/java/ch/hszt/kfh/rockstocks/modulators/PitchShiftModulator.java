package ch.hszt.kfh.rockstocks.modulators;

import ch.hszt.kfh.rockstocks.modulators.fourier.PitchShift;
import ch.hszt.kfh.rockstocks.series.ITimeSeries;

public class PitchShiftModulator implements IModulator {

	private PitchShift shift;
	
	public PitchShiftModulator() {
		shift = new PitchShift(8192);
		shift.setFftFrameSize(2048);
		shift.setOversampling(2);
		shift.setSampleRate(44100);	
	}
	
	@Override
	public double[] modulate(double[] data, int time) {
		
		double[] result = new double[data.length];
		
		double pitch = timeSeries.getValue(time);
		
		shift.setPitchShift(pitch);
		shift.smbPitchShift(data, result, 0, data.length);
		
		return result;
	}
	
	private ITimeSeries timeSeries;

	@Override
	public ITimeSeries getTimeSeries() {
		return timeSeries;
	}

	@Override
	public void setTimeSeries(ITimeSeries series) {
		this.timeSeries = series;

	}
	
}
