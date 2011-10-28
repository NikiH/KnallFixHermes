package ch.hszt.kfh.ubungen;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class BisectTest {
	
	private Mockery context;
	private Bisect instance;
	
	@Before
	public void setUp() {
		context = new JUnit4Mockery();
		instance = new Bisect();
	}
	
	@Test
	public void squareRootOfTwo() throws Exception {
		
		instance.setA(0);
		instance.setB(2);
		instance.setTol(1e-4);
		
		final IFunction square = context.mock(IFunction.class);
		instance.setFunction(square);
		
		// stellt sicher, dass nur die minimale Anzahl
		// an Aufrufen von f() durchgeführt wird
		// (keine toten Pfade)
		context.checking(new Expectations() {{
			oneOf (square).f(0.); will(returnValue(-2.));
			oneOf (square).f(2.); will(returnValue(2.));
			oneOf (square).f(1.); will(returnValue(-1.));
			oneOf (square).f(1.5); will(returnValue(0.25));
			oneOf (square).f(1.25); will(returnValue(-0.4375));
			oneOf (square).f(1.375); will(returnValue(-0.109375));
			oneOf (square).f(1.4375); will(returnValue(0.06640625));
			oneOf (square).f(1.40625); will(returnValue(-0.022460937));
			oneOf (square).f(1.421875); will(returnValue(0.021728516));
			oneOf (square).f(1.4140625); will(returnValue(-0.000427246));
			oneOf (square).f(1.41796875); will(returnValue(0.010635376));
			oneOf (square).f(1.416015625); will(returnValue(0.00510025));
			oneOf (square).f(1.4150390625); will(returnValue(0.002335548));
			oneOf (square).f(1.41455078125); will(returnValue(0.000953912));
			oneOf (square).f(1.414306640625); will(returnValue(0.000263273));
			oneOf (square).f(1.4141845703125); will(returnValue(-0.000082001));
		}});
		
		double r = instance.iterate();
		assertEquals(1.414, Math.floor(r * 1000.) / 1000., 0.);
	}

}
