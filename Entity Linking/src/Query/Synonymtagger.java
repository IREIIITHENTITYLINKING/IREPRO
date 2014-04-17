package Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import links.Searcher;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import Query.Words;
import edu.stanford.nlp.ling.HasWord;


public class Synonymtagger {

	//static Searcher searcher;
	static char[] punchuations={',','.','!'};
	public static void getSynonymlP(List<Words> sentence,int j,ArrayList<mark> pair,TreeMap<Integer,ArrayList<mark>> graph,Searcher searcher) throws NullPointerException, IOException, ParseException
	{
		StringBuilder st=new StringBuilder();
		mark each=pair.get(j);
		for(int i=each.start;i<each.end;i++)
		{
			String word=sentence.get(i).Word;
			if(StringUtils.containsAny(word,punchuations))
			{
				st=null;
				break;
			}
			st.append(word+ " ");
		}
		if(st==null)
		{
			pair.remove(j);
		}
		else
		{
			each.st=st;
			getProbabilites(each,searcher); //get the frequencies
			if(each.documentID==0)
				return;
			ArrayList<mark> values=graph.get(each.start); //to get all sentences starting from the same word to minimize the intersection check 
			if(values==null)
			{
				values=new ArrayList<mark>();
				values.add(each);
				graph.put(each.start, values);
			}
			else
				values.add(each);
		}
	}
	public static List<mark> getParts(List<Words> sentence,Searcher searcher,Tree root) throws NullPointerException, IOException, ParseException
	{

		List<mark> finalSynonyms =new ArrayList<mark>();
		TreeMap<Integer,ArrayList<mark>> graph=new TreeMap<Integer,ArrayList<mark>>();
		List<ArrayList<mark>> Nsequence=new ArrayList<ArrayList<mark>>(6);
		for(int n=1;n<=6;n++)
		{
			ArrayList<mark> pair=new ArrayList<mark>();
			for(int start=0;start+n<=sentence.size();start++)
			{
				if(root.contains(sentence.get(start).Word)==-1&&(n==1||root.contains(sentence.get(start+n-1).Word)==-1))
				{/*
					String endword=sentence.get(start+n-1).word();
					if(endword.equals("a")||endword.equals("an")||endword.equals("the"))
						continue;
				*/	mark m=new mark(start, start+n);
					m.globalStart=sentence.get(start).start;
					m.globalEnd=sentence.get(start+n-1).end;
					pair.add(m);
					/*
					for(int t=start;t<=start+n-1;t++)
					{
						System.out.print(sentence.get(t).word()+" ");
					}
					System.out.println();
					*/
				}
				
			}
			Nsequence.add(pair);
		}
		for(ArrayList<mark> each:Nsequence)
		{
			for(int i=0;i<each.size();i++)
			{
				getSynonymlP(sentence,i,each,graph,searcher);				
			}
		}
		boolean isFirst=true;
		mark previous=null;
		for(Map.Entry<Integer,ArrayList<mark>> each:graph.entrySet())
		{
			ArrayList<mark> array=each.getValue();
			/*for(mark eachM:array)
			{
				System.out.print(eachM.st.toString()+"|");
			}
			System.out.println();
			*/
			mark current=array.get(array.size()-1);
			
			if(isFirst)
			{
				finalSynonyms.add(current);
				previous=current;
				isFirst=false;
			}
			else
			{
				if(current.start<previous.end)
				{
					if((previous.end-previous.start)<(current.end-current.start))
					//if(((double)current.linkf/current.totalf)>=((double)previous.linkf/previous.totalf))
					{
						previous=current;
						finalSynonyms.add(current);
					}
				}
				else
				{
					finalSynonyms.add(current);
					previous=current;
				}
			}
		}
		return finalSynonyms;
		
		
	}
	
	//collect all the probabilities corresponding to a synonym here
	public static void getProbabilites(mark m,Searcher searcher) throws NullPointerException, IOException, ParseException
	{
		try
		{
			String value=m.st.toString().trim();
			ScoreDoc[] top;
			if(m.end-m.start==1)
			{
				top=searcher.search(value, 1,true);
				if(top.length>0)
				{
					m.documentID = top[0].doc;
					m.linkf=searcher.insearch.doc(top[0].doc).getField("linkf").numericValue().intValue();
					m.totalf=searcher.insearch.doc(top[0].doc).getField("totalf").numericValue().intValue();
				}
			}
			else
			{
				top=searcher.search(value, 1,false);
				if(top.length>0)
				{
					m.documentID = top[0].doc;
					m.linkf=searcher.insearch.doc(top[0].doc).getField("linkf").numericValue().intValue();
					m.totalf=searcher.insearch.doc(top[0].doc).getField("totalf").numericValue().intValue();
				}
			}
		}catch(Exception e)
		{
			//System.out.println(m.st.toString());
			throw e;
		}
		
	}

}
