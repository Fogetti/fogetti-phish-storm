package fogetti.phish.storm.relatedness;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the following real time phishing classifier:
 * <a href="https://orbilu.uni.lu/bitstream/10993/20053/1/phishStorm-revised.pdf">
 * https://orbilu.uni.lu/bitstream/10993/20053/1/phishStorm-revised.pdf
 * </a>
 * @author gergely.nagy
 *
 */
public abstract class URLSpout extends BaseRichSpout {

	private static final long serialVersionUID = -6424905468176142975L;
	static final Logger logger = LoggerFactory.getLogger(URLSpout.class);
	private SpoutOutputCollector collector;
	private List<String> urllist;
	private ListIterator<String> iterator;
	private String urlDataFile;
	private JedisPoolConfig config;
    private IAcker acker;

	public URLSpout(String urlDataFile, JedisPoolConfig config) {
		this.urlDataFile = urlDataFile;
		this.config = config;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
		this.urllist = readURLListFromFile();
		this.iterator = urllist.listIterator();
        this.acker = buildAcker(collector, config);
	}

    public abstract IAcker buildAcker(SpoutOutputCollector collector, JedisPoolConfig config);

	private List<String> readURLListFromFile() {
		List<String> urls = new ArrayList<>();
		loadUrls(urls);
		return urls;
	}

	private void loadUrls(List<String> urls) {
		try(Scanner scanner = new Scanner(new FileReader(urlDataFile));) {
			while (scanner.hasNextLine()) {
				urls.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void nextTuple() {
		// Check the case when we read first seen elements
		if (iterator.hasNext()) {
			String URLWithScheme = iterator.next();
			doNextTuple(URLWithScheme);
		}
		// Check the case when we read failed elements
		else if (iterator.hasPrevious()) {
			String URLWithScheme = iterator.previous();
			doNextTuple(URLWithScheme);
		}
	}

	private void doNextTuple(String URLWithScheme) {
		collector.emit(new Values(URLWithScheme), URLWithScheme);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("url"));
	}

	@Override
	public void ack(Object msgId) {
	    logger.info("Acking [{}]", msgId);
	    acker.enqueue(msgId.toString());
	}

	@Override
	public void fail(Object msgId) {
		logger.debug("Message [msg={}] failed", msgId);
		logger.warn("Requeueing [msg={}]", msgId);
		iterator.add(msgId.toString());
	}

}
