package ch.hszt.kfh.rockstocks;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.hszt.kfh.rockstocks.drains.IDrain;
import ch.hszt.kfh.rockstocks.sources.ISource;

@RunWith(JMock.class)
public class PlayerTest {
	
	private Mockery context;
	
	private Player instance;
	
	@Before
	public void setUp() {
		context = new JUnit4Mockery();
		instance = new Player();
	}
	
	@Test
	public void directlyFromSourceToDrain() {
		instance.getModulators().clear();
		
		final ISource source = context.mock(ISource.class);
		final IDrain drain = context.mock(IDrain.class);
		
		instance.setDrain(drain);
		instance.setSource(source);

		final Sequence sendReceiveSequence = context.sequence("sendReceiveSequence");
		
		context.checking(new Expectations() {{
			// getTime() wird hier nicht sinnvoll getestet
			allowing(source).getTime();
			
			// erster Durchgang
			oneOf(source).isDone(); will(returnValue(false)); inSequence(sendReceiveSequence);
			oneOf(source).send(); will(returnValue(new double[] {-1, -0.5, 0})); inSequence(sendReceiveSequence);
			oneOf(drain).receive(new double[] {-1, -0.5, 0}); inSequence(sendReceiveSequence);
			
			// Zweiter Durchgang
			oneOf(source).isDone(); will(returnValue(false)); inSequence(sendReceiveSequence);
			oneOf(source).send(); will(returnValue(new double[] {0.25, -0.3, 0.001})); inSequence(sendReceiveSequence);
			oneOf(drain).receive(new double[] {0.25, -0.3, 0.001}); inSequence(sendReceiveSequence);

			// Dritter Durchgang
			oneOf(source).isDone(); will(returnValue(false)); inSequence(sendReceiveSequence);
			oneOf(source).send(); will(returnValue(new double[] {0, -0.5, 0})); inSequence(sendReceiveSequence);
			oneOf(drain).receive(new double[] {0, -0.5, 0}); inSequence(sendReceiveSequence);

			// Vertig
			oneOf(source).isDone(); will(returnValue(true)); inSequence(sendReceiveSequence);
		}});
		
		instance.play();
		
		context.assertIsSatisfied();
		
	}
	

}
