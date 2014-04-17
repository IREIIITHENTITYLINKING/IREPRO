package Query;

import java.util.HashMap;

public class Anchor {
	int sentenceNo;
	public String anchor;
	public boolean isMarked=false;
	public int finalPage;
	public double totalFreq=0;
	public double linkFreq=0;
	public int numPage=0;
	public int start=0,end=0,globalStart,globalEnd;
	public HashMap<Integer,PostingObject> lists=new HashMap<>();
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String val=anchor+"|";
		for(int each:lists.keySet())
		{
			val+=(each+"|");
		}
		val+="\n";
		return val;
	}
}
