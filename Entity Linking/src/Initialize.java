

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import links.Searcher;
import Query.Tree;
import TitleMapper.BinarySearchID;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * Servlet implementation class Initialize
 */
@WebServlet("/Initialize")
public class Initialize extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BinarySearchID idSearcher=new BinarySearchID(10);
	//private MaxentTagger tagger = new MaxentTagger(	"/home/debarshi/Documents/" + "stanford-postagger-2014-01-04/models/english-left3words-distsim.tagger");
	private Searcher synonyms;
	//private HashMap<Integer,LinkObj> inLinks;
	private Searcher inLinks;
	private Tree root=new Tree();
	/**
     * @see HttpServlet#HttpServlet()
     */
    public Initialize() {
        super();
		
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	// TODO Auto-generated method stub
    	super.init(config);
    	ServletContext context=config.getServletContext();
    	context.setAttribute("searcher",idSearcher);
    	try {
			synonyms=new Searcher("/home/debarshi/Documents/wikipedia/new_Index");
			inLinks =new Searcher("/home/debarshi/Documents/wikipedia/link_index");
			//inLinks=InLinkMemoryCreator.getLinks("/home/debarshi/Documents/wikipedia/wikisample/temp/LinkMap");
			readStopWords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	context.setAttribute("inLinks",inLinks);
    	context.setAttribute("synonym",synonyms);
    	//context.setAttribute("inLink",inLinks);
    	context.setAttribute("stopWord",root);
    	
    }
	 private void readStopWords() throws IOException
	 {
		 File f=new File("/home/debarshi/stopwords.txt");
		 //System.out.println(f.exists());
		 BufferedReader br=new BufferedReader(new FileReader(f));
		 String read=br.readLine();
		 while(read!=null)
		 {
			 root.insertWord(read);
			 read=br.readLine();
		 }
	 }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
