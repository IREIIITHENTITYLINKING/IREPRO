package links;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;

import Query.Anchor;
import Query.CountPage;
import Query.PostingObject;
import Query.mark;
import TitleMapper.BinarySearchID;

public class searchlucene 
{
	public static void main (String args[]) throws IOException, NullPointerException, ParseException {
		Anchor an;
		BinarySearchID idSearcher=new BinarySearchID(10);
		Searcher s=new Searcher("/home/debarshi/Documents/wikipedia/new_Index");
		//Searcher s1=new Searcher("/home/debarshi/Documents/inlink");
		CountPage p=new CountPage();
		//int val=getInterSection(41248913,41248920, s1,p);
		System.out.println(1);
		//System.out.println(p.A+" "+p.B);
		ScoreDoc[] docs=s.search("apple",1,true);
		//System.out.println(docs.length);
		uncompress uncompressor=new uncompress();
		for(ScoreDoc each:docs)
		{
			uncompressor.uncompressit(s.insearch.doc(each.doc).getBinaryValue("posting").bytes);
			int val=0;
			Document doc=s.insearch.doc(each.doc);
			System.out.print(doc.get("synonym")+"|"+doc.getField("linkf").numericValue().doubleValue()+"|"+doc.getField("totalf").numericValue().doubleValue()+"      ");
			while((val=uncompressor.getNext())!=-1)
			{

				System.out.print(val+" "+idSearcher.Search(val)+"|");
				val=uncompressor.getNext();
				System.out.print(val+"|");
			}
			System.out.println("");
		}
		
		//System.out.println(docs.length);
		//System.out.println(s1.insearch.doc(docs[0].doc).getField("totalCount").numericValue().intValue());
		

		
		
	}
	public static Anchor searching(mark obj,Searcher s)
	{
		Anchor anchorobj = new Anchor();
		anchorobj.anchor=obj.st.toString();
		try
		{	
			uncompress u = new uncompress();
			Document d = s.insearch.doc(obj.documentID);
			anchorobj.linkFreq=d.getField("linkf").numericValue().doubleValue();
		    anchorobj.totalFreq=d.getField("totalf").numericValue().doubleValue();
		    anchorobj.numPage=d.getField("numPages").numericValue().intValue();
		    u.uncompressit(d.getBinaryValue("posting").bytes);
		    int val ;
		    int v;
		    PostingObject x;
		    while((val = u.getNext()) > 0) {
		    	v=u.getNext();
		    	/*
		    	if(val==187668||val==856)
		    	{
		    		v=50;
		    	}
		    	*/
	    		x = new PostingObject();
	    		x.freq=v;
	    		if(((double)v/(anchorobj.linkFreq))<.02)
	    		{
	    			break;
	    		}
	    		anchorobj.lists.put(val, x);
		    }
		}
		catch(IOException  e) {
			e.printStackTrace();
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		//System.out.println(anchorobj);
		return anchorobj;
	}
	
	public static int getInterSection(int queryA,int queryB,Searcher s,CountPage values) throws NullPointerException, IOException, ParseException
	{
			int count=0;
			ScoreDoc[] hit1 =s.Linksearch(queryA);
			ScoreDoc[] hit2=s.Linksearch(queryB);
			uncompress uncompress1=new uncompress();
			uncompress uncompress2=new uncompress();
			
			
			if(hit1.length>0)
			{
				Document doc1=s.insearch.doc(hit1[0].doc);
				uncompress1.uncompressit(doc1.getBinaryValue("posting").bytes);
				values.A=doc1.getField("totalCount").numericValue().intValue();
			}
			if(hit2.length>0)
			{
				Document doc2=s.insearch.doc(hit2[0].doc);
				uncompress2.uncompressit(doc2.getBinaryValue("posting").bytes);
				values.B=doc2.getField("totalCount").numericValue().intValue();
			}
			
			if(hit1.length==0)
			{
				values.A=1;
				return 0;
			}
			else if(hit2.length==0)
			{
				values.B=1;
				return 0;
			}

		
			
			boolean flagA=true;
			boolean flagB=true;
			//System.out.println(true);
			int v1=uncompress1.getNext();
			if(v1==-1)
				flagA=false;
			int v2=uncompress2.getNext();
			if(v2==-1)
				flagB=false;
			
			while(flagA&&flagB)
			{
				if(v1==v2)
				{
					count++;
					v1=uncompress1.getNext();
					if(v1==-1)
						flagA=false;
					v2=uncompress2.getNext();
					if(v2==-1)
						flagB=false;
				}
				else if(v1<v2)
				{
					v1=uncompress1.getNext();
					if(v1==-1)
						flagA=false;
				}
				else
				{
					v2=uncompress2.getNext();
					if(v2==-1)
						flagB=false;
				}
			}
			return count;
			
	}
		
}