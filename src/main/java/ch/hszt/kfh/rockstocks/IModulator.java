package ch.hszt.kfh.rockstocks;

/**
 * Kann eine Reihe von aufeinanderfolgenden normalisierten Samples modulieren.S
 * 
 * @author The Team
 *
 */
public interface IModulator {
	
	double[] modulate(double[] data);

}
