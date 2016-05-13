package fogetti.phish.storm.relatedness;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.tuple.Tuple;
import org.junit.Test;

public class URLBoltTest {

	@Test
	public void forward() throws Exception {
		// Given we create a URL bolt to replicate URLs
		URLBolt urlbolt = new URLBolt();
		OutputCollector spy = getSpy();
		urlbolt.prepare(null, null, spy);

		String URL = "URL";
		Tuple tuple = mock(Tuple.class);
		when(tuple.getStringByField("word")).thenReturn(URL);
		// When we call execute
		urlbolt.execute(tuple);

		// Then it forwards the input
		verify(tuple, atLeast(1)).getStringByField("word");
		verify(spy, atLeast(1)).emit((Tuple)anyObject(), anyObject());
		verify(spy, atLeast(1)).ack(anyObject());
	}

	protected OutputCollector getSpy() {
		OutputCollector collector = new OutputCollector(mock(OutputCollector.class));
		OutputCollector spy = spy(collector);
		return spy;
	}
}
