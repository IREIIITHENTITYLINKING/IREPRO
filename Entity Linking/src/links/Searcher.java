package links;


import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searcher 
{
	public IndexSearcher insearch = null;
	private IndexReader inreader;
	private File indir;
	private WhitespaceAnalyzer analyzer;
	@SuppressWarnings("deprecation")
	public Searcher(String input) throws IOException {
		analyzer=new WhitespaceAnalyzer(Version.LUCENE_47);
		this.indir = new File(input);
		if (this.insearch == null) {
        	Directory dir = FSDirectory.open(this.indir);
        	this.inreader =IndexReader.open(dir);
    		this.insearch = new IndexSearcher(this.inreader);
        }
		
	}
	
	public ScoreDoc[] search(String queryin,int num_pages,boolean isTerm) throws IOException, ParseException, NullPointerException{
		//TopScoreDocCollector collector = TopScoreDocCollector.create(num_pages, true);
		//Query q = new QueryParser(Version.LUCENE_47, "synonym", analyzer).parse(query);
		//this.insearch.search(q, collector);
		//ScoreDoc[] topDocs = collector.topDocs().scoreDocs;
		//return topDocs;
		/*
		query = query.toLowerCase();
		PhraseQuery q = new PhraseQuery();
		String[] words = query.split(" ");q.setSlop(1);
		for (String word:words)	q.add(new Term("synonym", word));
		BooleanQuery bq = new BooleanQuery();
		bq.add(q, BooleanClause.Occur.MUST);
		TopDocs topDocs = this.insearch.search(bq, num_pages);
		return topDocs.scoreDocs;
		*/
		QueryParser parser=new QueryParser(Version.LUCENE_47,"synonym", analyzer);
		BooleanQuery querybool=new BooleanQuery();
		try{
			PhraseQuery queryP=(PhraseQuery) parser.createPhraseQuery("synonym",queryin);
			if(!isTerm)
				querybool.add(queryP, Occur.SHOULD);
		}catch(Exception e){ }	
		if(isTerm)
			for(String t:queryin.split(" "))
			{
				querybool.add(new TermQuery(new Term("synonym",t)),Occur.SHOULD);
			}
		return this.insearch.search(querybool,null, num_pages).scoreDocs;
	}
	
	public ScoreDoc[] Linksearch(int queryin) throws IOException, ParseException, NullPointerException{
		QueryParser parser=new QueryParser(Version.LUCENE_47,"synonym", analyzer);
		Query querybool=NumericRangeQuery.newIntRange("page",queryin,queryin,true,true);
		return this.insearch.search(querybool,null,1).scoreDocs;
	}
	
}
