package TitleMapper;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.jhu.nlp.wikipedia.PageCallbackHandler;
import edu.jhu.nlp.wikipedia.WikiPage;
import edu.jhu.nlp.wikipedia.WikiXMLParser;
import edu.jhu.nlp.wikipedia.WikiXMLParserFactory;

public class ExtractTitle {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		File inputDir=new File(args[0]);
		String[] files=inputDir.list();
		for(String each:files) {
			parse(each,args[0]);
			System.out.println(each);
		}

	}
	public static void parse(String inputFile,String dir) throws IOException
	{
		final ArrayList<Node> list=new ArrayList<Node>();
		try {
			WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser(dir+File.separatorChar+inputFile);
			wxsp.setPageCallback(new PageCallbackHandler() { 
				public void process(WikiPage page) {
					list.add(new Node(page.getTitle().toLowerCase(),Long.parseLong(page.getID())));
				}
			});
			wxsp.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeFile(inputFile, dir, list);
		
	}
	
	public static void writeFile(String fileName,String dir,ArrayList<Node> list) throws IOException
	{
		//PrintWriter foutString=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dir+File.separatorChar+fileName+"_s")));
		//DataOutputStream foutID=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dir+File.separatorChar+fileName+"_i")));
		PrintWriter foutString=new PrintWriter(new BufferedOutputStream(new FileOutputStream("E:\\IRE_Project\\titles\\titles"+File.separatorChar+fileName+"_s")));
		PrintWriter foutid=new PrintWriter(new BufferedOutputStream(new FileOutputStream("E:\\IRE_Project\\titles\\titles"+File.separatorChar+fileName+"_i")));
		
		for(Node each:list)
		{
			foutid.println(each.id+" "+each.title.trim().toLowerCase());
		}

		Collections.sort(list); //sort by String
		for(Node each:list)
		{
			foutString.println(each.id+" "+each.title.trim().toLowerCase());
		}
		
		//Collections.sort(list,new external()); //sort using external comparator
		
		
		foutString.flush();
		foutString.close();
		
		foutid.flush();
		foutid.close();
	}
}
class Node implements Comparable<Node>
{
	String title;
	long id;
	

	public Node(String t,long i) {
		// TODO Auto-generated constructor stub
		title=t;id=i;
	}
	
	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		return this.title.compareTo(o.title);
	}
}
class external implements Comparator<Node>
{

	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub
		return (int)(o1.id-o2.id);
	}
	
}