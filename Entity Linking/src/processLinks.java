

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import links.Searcher;
import Query.Anchor;
import Query.Result;
import Query.SentenceParser;
import Query.Synonymtagger;
import Query.Tree;
import Query.Words;
import Query.mark;
import TitleMapper.BinarySearchID;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * Servlet implementation class processLinks
 */
@WebServlet("/processLinks")
public class processLinks extends HttpServlet {
	private static final long serialVersionUID = 1L;;
	private Searcher synonyms;
	private Searcher inLinks;
	private Pattern r = Pattern.compile("n*n");
	private Tree root;
	static String[] array1={"\"","-",";",":"};
	static String[] replace={" "," "," "," "};
	//private HashMap<Integer,LinkObj> inLinks;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public processLinks() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();;
		HashMap<Long,Double> relativeScore=new HashMap<Long,Double>();
		Result result=new Result(relativeScore);
		

		ServletContext context=request.getServletContext();
    	synonyms=(Searcher) context.getAttribute("synonym");
    	inLinks=(Searcher) context.getAttribute("inLinks");
    	//System.out.println(inLinks==null);
    	//inLinks=(HashMap<Integer, LinkObj>) context.getAttribute("inLinks");
		String text=(String) session.getAttribute("text");
		SentenceParser parserSentence=new SentenceParser(text);
		
		session.removeAttribute("text");
		
		session.setAttribute("inputText",parserSentence);
		root=(Tree) context.getAttribute("stopWord");
		PrintWriter out=response.getWriter();
		try {
			
			tag(parserSentence,text,request,response,result,relativeScore);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void tag(SentenceParser parser,String text,HttpServletRequest request,HttpServletResponse response,Result result,HashMap<Long,Double> relativeScore) throws Exception {
		TreeMap<Integer,String> finalmap=new TreeMap<Integer, String>();
		int sentenceNumber=0;
		ArrayList<Words> sentence=parser.getNextSentence();
		//System.out.println(sentence);
		List<mark> finalList=Synonymtagger.getParts(sentence,synonyms,root);
		for(mark each:finalList)
		{
			result.addAnchor(each,synonyms,sentenceNumber++);
			//System.out.println(each.st.toString()+" "+each.globalStart+" "+each.globalEnd);
		}
		
		HashSet<Integer> S=new HashSet<Integer>();
		for(int i=0;i<result.lists.size();i++)
		{
			Anchor anchor=result.lists.get(i);
			result.computeBestMatch(i,inLinks,S);
		}
		
		for(int i=0;i<result.lists.size();i++)
		{
			//
			if(result.lists.get(i).isMarked)  //don't calculate score
				continue;
			double scoreSum=0;
			for(int j=0;j<result.lists.size();j++)
			{
				if(i==j)
					continue;
				if(result.lists.get(j).isMarked) //don't calculate score
					continue;
				int min,max;
				int pageIDA=result.lists.get(i).finalPage;
				int pageIDB=result.lists.get(j).finalPage;
				
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
				Double v=relativeScore.get(finalKey);
				if(v==null)
					v=result.computeRelatedNess(pageIDA, pageIDB, inLinks);
				scoreSum+=v;
			}
			scoreSum=scoreSum/(S.size()-1);
			scoreSum+=((double)result.lists.get(i).linkFreq)/result.lists.get(i).totalFreq;
			scoreSum/=2;
			double threshold=0.2;
			if(scoreSum>threshold)
				result.lists.get(i).isMarked=false;
			else
				result.lists.get(i).isMarked=true;
			System.out.println(result.lists.get(i).anchor + " "+scoreSum);

		}
	
		request.getSession().setAttribute("finalresults",result);

		response.sendRedirect("output.jsp");
		
	}
	

}
