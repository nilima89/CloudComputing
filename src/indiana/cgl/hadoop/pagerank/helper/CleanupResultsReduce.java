package indiana.cgl.hadoop.pagerank.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
 
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
class outputRank{
	private String link;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	private Double value;
	public outputRank(String link,Double value){
		this.link=link;
		this.value=value;
	}
	
}
class firstTen implements Comparator<outputRank>{

	@Override
	public int compare(outputRank o1, outputRank o2) {
		// TODO Auto-generated method stub
		if(o1.getValue()>o2.getValue())
		{
			return 1;
		}else if(o1.getValue()<o2.getValue())
		{
			return -1;
		}
		return 0;
	}
	
}
public class CleanupResultsReduce extends Reducer<LongWritable, Text, LongWritable, Text>{
	ArrayList<outputRank> outputList = new ArrayList<outputRank>();
	
public void reduce(LongWritable key, Iterable<Text> values,
		Context context) throws IOException, InterruptedException {
	Text t=values.iterator().next();
	Double dVal=new Double(t.toString());
	String url=key.toString();
	outputRank temp=new outputRank(url, dVal);
	outputList.add(temp);
	context.write(key, t);
}

@Override
protected void cleanup(Context context) throws IOException, InterruptedException {
   System.out.println("Sorted Result---------->");
   Collections.sort(outputList,new firstTen());
    for(int i=(outputList.size()-10);i<outputList.size();i++)
    {
        System.out.println(outputList.get(i).getLink()+" "+outputList.get(i).getValue());
    }
}
}
