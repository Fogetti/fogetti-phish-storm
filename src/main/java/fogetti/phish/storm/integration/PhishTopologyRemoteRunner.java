package fogetti.phish.storm.integration;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;

public class PhishTopologyRemoteRunner {

	public static void main(String[] args) throws Exception {
		StormTopology topology = PhishTopologyBuilder.build();
		
		Config config = new Config();
		config.setNumWorkers(20);
		config.setMessageTimeoutSecs(30);
		config.put(Config.TOPOLOGY_EXECUTOR_RECEIVE_BUFFER_SIZE,
	               new Integer(16384));
	    config.put(Config.TOPOLOGY_EXECUTOR_SEND_BUFFER_SIZE,
	               new Integer(16384));
	    config.put(Config.TOPOLOGY_TRANSFER_BUFFER_SIZE,
	               new Integer(32));
		
		StormSubmitter.submitTopology("phish-storm-topology", config, topology);
	}
	
}