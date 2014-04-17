package links;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class Indexer {
	/** Creates a new instance of Indexer */
    private IndexWriter indexWriter = null;
	File dir;
    public Indexer(String outputDir) throws IOException {
    	this.dir=new File(outputDir);
    	getIndexWriter(true);
    }
    private static WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_47);
    
 

    
    public IndexWriter getIndexWriter(boolean create) throws IOException {
        if (indexWriter == null) {
            indexWriter = new IndexWriter(FSDirectory.open(dir),new IndexWriterConfig(Version.LUCENE_47, analyzer));
        }
        return indexWriter;
   }    
   
    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }
    public void addDocument(Document d) throws IOException
    {
    	indexWriter.addDocument(d);
    }
    public void close() throws IOException
    {
    	indexWriter.commit();
    }
}
