package fogetti.phish.storm.relatedness;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import backtype.storm.spout.SpoutOutputCollector;

public class URLSpoutTest {

	@Test
	public void segmentOne() throws Exception {
		// Given we want to segment a word before calculating relatedness
		URLSpout spout = new URLSpout();
		spout.open(null, null, null);

		// When we submit "itwasabrightcolddayinaprilandtheclockswerestrikingthirteen"
		List<String> segment = spout.segment("itwasabrightcolddayinaprilandtheclockswerestrikingthirteen");
		
		// Then it returns [it, was, a, bright, cold, day, in, april, and, the, clocks, were, striking, thirteen]
		System.out.println(segment);
		List<String> expected = Arrays.asList(new String[]{"it", "was", "a", "bright", "cold", "day", "in", "april", "and", "the", "clocks", "were", "striking", "thirteen"});
		assertEquals("The segmentation was wrong", expected.toString(), segment.toString());
	}

	@Test
	public void segmentTwo() throws Exception {
		// Given we want to segment a word before calculating relatedness
		URLSpout spout = new URLSpout();
		spout.open(null, null, null);

		// When we submit "inaholeinthegroundtherelivedahobbitnotanastydirtywetholefilledwiththeendsofwormsandanoozysmellnoryetadrybaresandyholewithnothinginittositdownonortoeatitwasahobbitholeandthatmeanscomfort"
		List<String> segment = spout.segment("inaholeinthegroundtherelivedahobbitnotanastydirtywetholefilledwiththeendsofwormsandanoozysmellnoryetadrybaresandyholewithnothinginittositdownonortoeatitwasahobbitholeandthatmeanscomfort");
		
		// Then it returns [in, a, hole, in, the, ground, there, lived, a, hobbit, not, a, nasty, dirty, wet, hole, filled, with, the, ends, of, worms, and, an, oozy, smell, nor, yet, a, dry, bare, sandy, hole, with, nothing, in, it, to, sitdown, on, or, to, eat, it, was, a, hobbit, hole, and, that, means, comfort]
		System.out.println(segment);
		List<String> expected = Arrays.asList(new String[]{"in", "a", "hole", "in", "the", "ground", "there", "lived", "a", "hobbit", "not", "a", "nasty", "dirty", "wet", "hole", "filled", "with", "the", "ends", "of", "worms", "and", "an", "oozy", "smell", "nor", "yet", "a", "dry", "bare", "sandy", "hole", "with", "nothing", "in", "it", "to", "sitdown", "on", "or", "to", "eat", "it", "was", "a", "hobbit", "hole", "and", "that", "means", "comfort"});
		assertEquals("The segmentation was wrong", expected.toString(), segment.toString());
	}
	
	@Test
	public void nextTuple() throws Exception {
		// Given we want to measure relatedness in an URL
		URLSpout spout = new URLSpout();
		SpoutOutputCollector spy = getSpy();
		spout.open(null, null, spy);

		// When we call nextTuple
		spout.nextTuple();

		// Then it calculates the public suffix of the given URL
		// and segments the words found in the URL
		verify(spy, atLeast(1)).emit(anyObject(), anyString());
	}

	protected SpoutOutputCollector getSpy() {
		SpoutOutputCollector collector = new SpoutOutputCollector(mock(SpoutOutputCollector.class));
		SpoutOutputCollector spy = spy(collector);
		return spy;
	}
}
