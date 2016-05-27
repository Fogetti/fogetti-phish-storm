package fogetti.phish.storm.integration;

import java.io.File;

import org.apache.storm.generated.StormTopology;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import fogetti.phish.storm.client.WrappedRequest;
import fogetti.phish.storm.relatedness.AsynchronousURLSpout;
import fogetti.phish.storm.relatedness.ClientBuildingGoogleSemBolt;
import fogetti.phish.storm.relatedness.MatcherBolt;
import fogetti.phish.storm.relatedness.intersection.IntersectionAction;
import fogetti.phish.storm.relatedness.intersection.IntersectionBolt;

public class PhishTopologyBuilder {

    public static final String REDIS_SEGMENT_PREFIX = "segment:"; 
    
	public static StormTopology build() throws Exception {
		String countDataFile = System.getProperty("count.data.file");
		String psDataFile = System.getProperty("ps.data.file");
		String urlDataFile = System.getProperty("url.data.file");
		String proxyDataFile = System.getProperty("proxy.data.file");
		String resultDataFile = System.getProperty("result.data.file");
		return build(countDataFile, psDataFile, urlDataFile, proxyDataFile, resultDataFile, "petrucci", 6379, "Macska12");
	}

	public static StormTopology build(
	        String countDataFile,
	        String psDataFile,
	        String urlDataFile,
	        String proxyDataFile,
	        String resultDataFile,
	        String redishost,
	        Integer redisport,
	        String redispword) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();

		JedisPoolConfig poolConfig = new JedisPoolConfig.Builder()
	        .setHost(redishost).setPort(redisport).setPassword(redispword).build();
		builder
			.setSpout("urlsource", new AsynchronousURLSpout(urlDataFile, poolConfig), 1)
			.setMaxSpoutPending(25);
        builder.setBolt("urlmatch", new MatcherBolt(countDataFile, psDataFile, poolConfig), 1)
            .fieldsGrouping("urlsource", new Fields("url"))
            .setNumTasks(1);
		builder.setBolt("googletrends", new ClientBuildingGoogleSemBolt(poolConfig, new File(proxyDataFile), new WrappedRequest()), 32)
		    .addConfiguration("timeout", 30000)
		    .fieldsGrouping("urlmatch", new Fields("word", "url"))
			.setNumTasks(6144);
		builder.setBolt("intersection", intersectionBolt(poolConfig, resultDataFile))
			.globalGrouping("googletrends");
		StormTopology topology = builder.createTopology();
		return topology;
	}

	private static IntersectionBolt intersectionBolt(JedisPoolConfig poolConfig, String resultDataFile) throws Exception {
		IntersectionAction action = new IntersectionAction() { private static final long serialVersionUID = 5105509799523060930L; @Override public void run() {} };
		IntersectionBolt callback = new IntersectionBolt(action, poolConfig, resultDataFile);
		return callback;
	}

}