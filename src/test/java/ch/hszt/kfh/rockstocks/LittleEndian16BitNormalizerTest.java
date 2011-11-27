package ch.hszt.kfh.rockstocks;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ch.hszt.kfh.rockstocks.normalizers.ISampleNormalizer;
import ch.hszt.kfh.rockstocks.normalizers.LittleEndian16BitNormalizer;

public class LittleEndian16BitNormalizerTest {
	
	private ISampleNormalizer instance;
	
	@Before
	public void setUp() {
		instance = new LittleEndian16BitNormalizer();
	}
	
	@Test
	@Ignore
	public void normalizeDenormalize() {
		
		byte[] data = new byte[] { -126,-127,-128,125,126,127,1,2,3,4,5,6,7,8,9,10,11,12,0,22 };
		
		double[] samples = instance.normalize(data);
		
		byte[] data2 = instance.denormalize(samples);
		
		assertArrayEquals(data, data2);
		
	}

}
