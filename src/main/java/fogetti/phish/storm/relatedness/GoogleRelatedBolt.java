package fogetti.phish.storm.relatedness;

import org.freaknet.gtrends.api.GoogleTrendsClient;

import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;

public class GoogleRelatedBolt extends GoogleBolt {

	private static final long serialVersionUID = -2812645097124425765L;

	public GoogleRelatedBolt(GoogleTrendsClient client) {
		super(client);
	}

	@Override
	protected String section() {
		return "Top searches";
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("topsearches"));
	}

}
