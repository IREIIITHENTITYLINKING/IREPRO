package TitleMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeTitles {
	static String contains="";

	public static void main(String[] args) throws IOException {  //args[0] = input directory //args[1]= K  //args[2] type
		// TODO Auto-generated method stub
		String inputDirectory=args[0];
		int K=Integer.parseInt(args[1]);

		int type=Integer.parseInt(args[2]);
		if(type==1)
			contains="_s";
		else
			contains="_i";
		File inputDir=new File(inputDirectory);
		String[] fileList=inputDir.list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.contains(contains);
			}
		});
		if(K==-1)
			K=fileList.length;
		int count=0;

			Merge merger=new Merge(Arrays.copyOf(fileList,fileList.length),inputDirectory,inputDirectory+File.separatorChar+(++count),Integer.parseInt(args[2]));
			merger.merge();
			fileList=inputDir.list(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					// TODO Auto-generated method stub
					return name.contains(contains);
				}
			});
	}

}

class Merge
{
	PrintWriter fout;
	PriorityQueue<Node> minHeap;
	String inputDirectory;;
	File files[];
	String fileList[];
	File outputFile;
	BufferedReader br[];
	int type;
	Merge(String fileList[],String input,String outputFile,int t) throws IOException
	{
		type=t;
		if(type==1)
			minHeap = new PriorityQueue<Node>();
		else
			minHeap = new PriorityQueue<Node>(1,new external());
		this.inputDirectory=input;
		this.fileList=fileList;
		files=new File[this.fileList.length];
		br=new BufferedReader[files.length];
		for(int i=0;i<fileList.length;i++)
		{
			files[i]=new File(input+File.separatorChar+fileList[i]);
		}
		this.outputFile=new File(outputFile);
		this.fout=new PrintWriter(new BufferedWriter(new FileWriter(this.outputFile)));
		for(int i=0;i<files.length;i++)
			br[i]=new BufferedReader(new FileReader(files[i]));
	}
	public void merge() throws IOException
	{
		for(int i=0;i<fileList.length;i++)
		{
			String read=readLine(i);
			while(read!=null&&read.equals(""))
				read=readLine(i);
			
			if(read==null)
				continue;
			String temp[]=read.split(" ",2);
			minHeap.add(new Node(temp[1],Long.parseLong(temp[0]),i));
		}
		while(!minHeap.isEmpty())
		{
			Node read=minHeap.poll();
			
			fout.println(read.id+" "+read.title);
			int fileNo=read.fileId;
			
			String readString=readLine(fileNo);
			while(readString!=null&&readString.equals(""))
				readString=readLine(fileNo);
			
			if(readString==null)
				continue;
			String temp[]=readString.split(" ",2);
			minHeap.add(new Node(temp[1],Long.parseLong(temp[0]),fileNo));
			
		}
		fout.flush();
		fout.close();
	}
	public String readLine(int i) throws IOException
	{
		if(!files[i].exists())
			return null;
		String read=br[i].readLine();
		if(read==null)
		{
			br[i].close();
			//files[i].renameTo(new File("/home/dilipvamsi/vamsi/project_titles/"+files[i].getName()));
			//files[i].delete();
		}
		return read;
	}
	
	class Node implements Comparable<Node>
	{
		String title;
		long id;
		int fileId;

		public Node(String t,long i,int fID) {
			// TODO Auto-generated constructor stub
			title=t;id=i;
			this.fileId=fID;
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
}