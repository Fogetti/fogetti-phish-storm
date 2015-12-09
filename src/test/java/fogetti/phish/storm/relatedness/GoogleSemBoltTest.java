package fogetti.phish.storm.relatedness;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.freaknet.gtrends.api.GoogleTrendsClient;
import org.freaknet.gtrends.api.exceptions.GoogleTrendsClientException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import backtype.storm.task.OutputCollector;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.exceptions.JedisException;

public class GoogleSemBoltTest extends GoogleBoltTest {

	private String paypal;
	private JedisCommands jedis;

	private class SpyingGoogleSemBolt extends GoogleSemBolt {
		
		private static final long serialVersionUID = 3798709600022221909L;
		
		private GoogleTrendsClient client;

		public SpyingGoogleSemBolt(GoogleTrendsClient client, JedisPoolConfig config) {
			super(client, config);
			this.client = client;
		}
		
		@Override
		GoogleTrendsClient getClient() {
		    return client;
		}
		
	    @Override
		protected JedisCommands getInstance() {
	        return jedis;
	    }
		
	    @Override
		protected void returnInstance(JedisCommands instance) {
	    }
	}
	
	@Before
	public void setup() throws Exception {
		paypal = "paypal";
		jedis = mock(JedisCommands.class);
	}
	
	@Test(expected = JedisException.class)
	public void redisRequestFails() throws Exception {
		// Given we want to get related words for a keyword
		GoogleTrendsClient client = mock(GoogleTrendsClient.class);
		JedisPoolConfig config = mock(JedisPoolConfig.class);
		GoogleSemBolt bolt = new SpyingGoogleSemBolt(client, config);
		Tuple keyword = mock(Tuple.class);
		when(keyword.getStringByField("segment")).thenReturn(paypal);

		// When we send a request to redis
		when(jedis.smembers(anyString())).thenThrow(new JedisException("Error"));
		bolt.execute(keyword);

		// Then it fails
	}
	
	@Test
	public void cachedSegmentFound() throws Exception {
		// Given we want to get related words for a keyword
		GoogleTrendsClient client = mock(GoogleTrendsClient.class);
		JedisPoolConfig config = mock(JedisPoolConfig.class);
		GoogleSemBolt bolt = new SpyingGoogleSemBolt(client, config);
		Tuple keyword = mock(Tuple.class);
		when(keyword.getStringByField("segment")).thenReturn(paypal);
		Set<String> segments = new HashSet<>();
		segments.add("paypal payment");

		// When we send a request to redis
		when(jedis.smembers(anyString())).thenReturn(segments);
		OutputCollector collector = mock(OutputCollector.class);
		bolt.prepare(null, null, collector);
		bolt.execute(keyword);

		// Then it returns a cached segment
		verify(keyword, atLeast(1)).getStringByField("url");
		verify(collector).ack(keyword);
	}

	@Test
	public void cachedSegmentNotFound() throws Exception {
		// Given we want to get related words for a keyword
		GoogleTrendsClient client = mock(GoogleTrendsClient.class);
		JedisPoolConfig config = mock(JedisPoolConfig.class);
		GoogleSemBolt bolt = new SpyingGoogleSemBolt(client, config);
		Tuple keyword = mock(Tuple.class);
		when(keyword.getStringByField("segment")).thenReturn(paypal);

		// When we send a request to redis which returns no cached segment
		when(client.execute(anyObject())).thenReturn(readSearchResult());
		when(jedis.smembers(anyString())).thenReturn(null);
		OutputCollector collector = mock(OutputCollector.class);
		bolt.prepare(null, null, collector);
		bolt.execute(keyword);

		// Then we send a request to bing
		verify(keyword, atLeast(1)).getStringByField("url");
		verify(client, atLeast(1)).execute(anyObject());
		verify(collector, atLeast(1)).emit((Tuple)anyObject(), anyObject());
		verify(collector).ack(keyword);
	}
	
	@Test
	public void googleRequestFailed() throws Exception {
		// Given we want to query Google Related data
		GoogleTrendsClient client = mock(GoogleTrendsClient.class);
		JedisPoolConfig config = mock(JedisPoolConfig.class);
		GoogleSemBolt bolt = new SpyingGoogleSemBolt(client, config);

		// When the bolt sends a new query to Google
		Tuple input = mock(Tuple.class);
		when(input.getStringByField("url")).thenReturn("paypal");
		OutputCollector collector = new OutputCollector(mock(OutputCollector.class));
		OutputCollector spy = spy(collector);
		when(client.execute(anyObject())).thenThrow(new GoogleTrendsClientException());
		bolt.execute(input);

		// Then it fails
		verify(input, atLeast(1)).getStringByField("url");
		verify(client, atLeast(1)).execute(anyObject());
		verify(spy, never()).ack(input);
	}
	
	@Test
	public void googleRequestSucceeds() throws Exception {
		// Given we want to query Google Related data
		GoogleTrendsClient client = mock(GoogleTrendsClient.class);
		JedisPoolConfig config = mock(JedisPoolConfig.class);
		GoogleSemBolt bolt = new SpyingGoogleSemBolt(client, config);
		OutputCollector collector = new OutputCollector(mock(OutputCollector.class));
		OutputCollector spy = spy(collector);
		bolt.prepare(null, null, spy);

		// When the bolt sends a new query to Google and succeeds
		Tuple input = mock(Tuple.class);
		String paypal = "paypal";
		when(input.getStringByField("segment")).thenReturn(paypal);
		String url = "url";
		when(input.getStringByField("url")).thenReturn(url);
		when(client.execute(anyObject())).thenReturn(readSearchResult());
		bolt.execute(input);

		// Then it sends top searches to the intersection bolt
		verify(input, atLeast(1)).getStringByField("segment");
		verify(input, atLeast(1)).getStringByField("url");
		verify(client, atLeast(1)).execute(anyObject());
		HashSet<String> tops = readSearchesFromFile();
		Values topSearches = new Values(tops, paypal, url);
		verify(spy, atLeast(1)).emit(input, topSearches);
		verify(spy, atLeast(1)).ack(input);
	}

	@Ignore
	@Test
	public void integration() throws Exception {
		// Given we want to query Google Related data
		Scanner console = new Scanner(System.in);
		String uname = "";
		if (console.hasNext())
			uname = console.next();
		String pword = "";
		if (console.hasNext())
			pword = console.next();
		console.close();
		GoogleSemBolt bolt = new GoogleSemBolt(uname, pword, null);

		// When the bolt receives a new tuple
		Tuple input = mock(Tuple.class);
		when(input.getStringByField("segment")).thenReturn("paypal");
		when(input.getStringByField("url")).thenReturn("http://google.com");
		bolt.execute(input);

		// Then it sends a new query to Google
		verify(input, atLeast(1)).getString(0);
	}
}
