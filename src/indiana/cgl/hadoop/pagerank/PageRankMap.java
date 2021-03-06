package indiana.cgl.hadoop.pagerank;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class PageRankMap extends Mapper<LongWritable, Text, LongWritable, Text> {

	// each map task handles one line within an adjacency matrix file
	// key: file offset
	// value: <sourceUrl PageRank#targetUrls>
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		int numUrls = context.getConfiguration().getInt("numUrls", 1);
		String line = value.toString();
		StringBuffer sb = new StringBuffer("#");

		// instance an object that records the information for one webpage
		RankRecord rrd = new RankRecord(line);
		int sourceUrl, targetUrl;

		if (rrd.targetUrlsList.size() <= 0) {
			// there is no out degree for this webpage;
			// scatter its rank value to all other urls
			double rankValuePerUrl = rrd.rankValue / (double) numUrls;
			for (int i = 0; i < numUrls; i++) {
				context.write(new LongWritable(i), new Text(String.valueOf(rankValuePerUrl)));
			}
		} else {
			/* Write your code here */

			// compute the pagerank of input Source url based on its outbound
			// links
			double rankValuePerUrl = rrd.rankValue / (double) rrd.targetUrlsList.size();

			// Take each outbound links of Source url, and compute pagerank, and
			// scatter it by defining
			// each Outbound link as Key and computed pagerank as Value

			for (Integer urls : rrd.targetUrlsList) {
				sb.append(urls).append("#");

				// <key, value> =<URL, pagerank of this URL>
				context.write(new LongWritable(urls), new Text(String.valueOf(rankValuePerUrl)));
			}

		} // for

		// <key, value> =<sourceUrl, targetUrls#>
		context.write(new LongWritable(rrd.sourceUrl), new Text(sb.toString()));
	} // end map

}

