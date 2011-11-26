package ch.hszt.kfh.rockstocks.series.historical;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.hszt.kfh.rockstocks.series.ITimeSeries;

@RunWith(JMock.class)
public class HistoricalSmiSimulationSeriesProviderTest {
	
	private HistoricalSmiSimulationSeriesProvider instance;
	
	private IHistory history;
	
	private Mockery context;
	
	private HashSet<String> isins = new HashSet<String>();
	
	private HistoryCollection fooCollection;
	private HistoryCollection barCollection;
	
	@Before
	public void setUp() {
		context = new JUnit4Mockery();
		
		isins.add("foo");
		isins.add("bar");
		
		fooCollection = new HistoryCollection(0, "Foo Name");
		barCollection = new HistoryCollection(0, "Bar Name");
		
		fooCollection.add(0, 10, 100);
		fooCollection.add(1, 12, 70);
				
		history = context.mock(IHistory.class);
		
		context.checking(new Expectations() {{
			
			allowing(history).getIsins(); will(returnValue(isins));
			
			allowing(history).get("foo"); will(returnValue(fooCollection));
			allowing(history).get("bar"); will(returnValue(barCollection));
			
		}});
		
		instance = new HistoricalSmiSimulationSeriesProvider(history);
		
	}
	
	@Test
	public void testTwoSeriesPerIsin() {
		assertEquals(instance.getAvailableSeries().size(), 4);
	}
	
	@Test
	public void testFooCollectionPrices() {
		List<ITimeSeries> allSeries = instance.getAvailableSeries();
		
		// find my series (prices)
		boolean found = false;
		for (ITimeSeries series : allSeries) {
			if (series.getName().contains("Foo") && series.getName().contains("Preise")) {
				found = true;
				double actual = series.getValue(1);
				assertEquals(1.2, actual, 0);
				break;
			}
		}
		if (!found) {
			Assert.fail();
		}
	}
	
	@Test
	public void testFooCollectionTurnovers() {
		List<ITimeSeries> allSeries = instance.getAvailableSeries();

		// find my series (turnovers)
		boolean found = false;
		for (ITimeSeries series : allSeries) {
			if (series.getName().contains("Foo") && series.getName().contains("Vol")) {
				found = true;
				double actual = series.getValue(1);
				assertEquals(0.7, actual, 0);
				break;
			}
		}
		if (!found) {
			Assert.fail();
		}
}
	
}
