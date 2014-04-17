package Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import links.Searcher;
import links.searchlucene;

import org.apache.lucene.queryparser.classic.ParseException;

public class Result {
	private HashMap<Long,Double> relativeScore;
	public Result(HashMap<Long,Double> relativeScore)
	{
		this.relativeScore=relativeScore;
	}
	HashMap<Integer,HashMap<Integer,Integer>> scores=new HashMap<Integer, HashMap<Integer,Integer>>();
	static int NofPages=14040000;
	
	///public HashMap<Integer,HashMap<Integer,Double>> preComputedScores=new HashMap<Integer,HashMap<Integer,Double>>();
	public ArrayList<Anchor> lists=new ArrayList<Anchor>();
	
	public void addAnchor(mark synonym,Searcher s,int sentenceLocation)
	{
		Anchor temp=searchlucene.searching(synonym, s);
		//System.out.println(synonym.st.toString()+" "+temp.lists.size());
		if(temp.lists.size()==0||synonym.st.length()<=2)
		{
			temp.isMarked=true;
			//System.out.println(synonym.st.toString()+" "+temp.isMarked);
			return;
		}
		temp.globalStart=synonym.globalStart;
		temp.globalEnd=synonym.globalEnd;
		lists.add(temp);
		temp.sentenceNo=sentenceLocation;
	}
	//compute best Match
	
	public void computeBestMatch(int j,Searcher inLinks,HashSet<Integer> S) throws NullPointerException, IOException, ParseException
	{
		Anchor base=lists.get(j);
		Anchor current;
		if(base.lists.size()==1) //if only 1 page exists assign that page to that entity-mention
		{
			int page=base.lists.keySet().iterator().next();
			base.finalPage=page;
			return;
		}
		for(Map.Entry<Integer,PostingObject> eachPage:base.lists.entrySet())
		{
			PostingObject currentPageObject = eachPage.getValue();
			//base.isMarked=true; //mark it for scoring
			for(int i=0;i<lists.size();i++)
			{
				if(i==j)
					continue;
				current=lists.get(i);
				for(Map.Entry<Integer,PostingObject> currentPage:current.lists.entrySet())
				{
					//numerator
					currentPageObject.score+=((computeRelatedNess(eachPage.getKey(),currentPage.getKey(),inLinks))*((((double)currentPage.getValue().freq))/current.linkFreq));
					currentPageObject.score/=current.lists.size();  //divide by number of pages in current i.e current = [Pg(b)}
				}
			}
		}

		TreeMap<Integer,PostingObject> sortedResults=new TreeMap<Integer,PostingObject>(new comparator(base.lists));
		sortedResults.putAll(base.lists);
		//System.out.println(base.lists.size());
		double Max=Double.NEGATIVE_INFINITY;
		//double Max=Double.NEGATIVE_INFINITY;
		
		double bestScore=sortedResults.firstEntry().getValue().score;

		
		for(Map.Entry<Integer,PostingObject> eachEntry:sortedResults.entrySet())
		{
			if(eachEntry.getValue().score/bestScore<=1.3)
			{
				if(Max < eachEntry.getValue().freq)
				{
					Max = eachEntry.getValue().freq;
					base.finalPage= eachEntry.getKey();
				}
			}
			else
				break;
		}
		S.add(base.finalPage);
		
		/*
		
		for(Map.Entry<Integer,PostingObject> eachEntry:base.lists.entrySet())
		{
			if(eachEntry.getValue().score>Max)
			{
				Max=eachEntry.getValue().score;
				base.finalPage=eachEntry.getKey();  //find the maximum matching page
			}
		}
		*/
		//System.out.println(Max);
	}
	class comparator implements Comparator<Integer>
	{
		HashMap<Integer,PostingObject> each;
		public comparator(HashMap<Integer,PostingObject> each) {
			// TODO Auto-generated constructor stub
			this.each=each;
		}
		@Override
		public int compare(Integer o1, Integer o2) {
			// TODO Auto-generated method stub
			if(each.get(o1).score<each.get(o2).score)
				return -1;
			else
				return 1;
		}
		
	}
	//TO DO
	public double computeRelatedNess(int pageIDA,int pageIDB,Searcher inLinks) throws NullPointerException, IOException, ParseException
	{
		CountPage values=new CountPage();
		int min,max;
		if(pageIDA>pageIDB)
		{
			min=pageIDB;
			max=pageIDA;
		}
		else
		{
			min=pageIDA;
			max=pageIDB;
		}
		long finalKey=min;
		finalKey=(finalKey<<32)+max;
		
		Double relVal=relativeScore.get(finalKey);
		if(relVal!=null)
		{
			return relVal;
		}
		int intersection=searchlucene.getInterSection(pageIDA, pageIDB,inLinks, values);
		
		double value=0;
		
		if(intersection==0)
		{
			value=Math.log(Math.max(values.A,values.B));
		}
		else
		{
			value=Math.log(Math.max(values.A,values.B))-Math.log(intersection);
		}
		value=value/((Math.log(NofPages)) - Math.log(Math.min(values.A,values.B)));	
		relativeScore.put(finalKey, value);
		return value;
	}	
}