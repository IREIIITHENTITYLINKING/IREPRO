package Query;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.StringTokenizer;

import links.compress;

public class InLinkMemoryCreator {
 
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		//HashMap<Integer,LinkObj> map=getLinks("/home/debarshi/Documents/wikipedia/wikisample/temp/LinkMap");
		compile();
	}
	public static void compile() throws NumberFormatException, IOException
	{
		HashMap<Integer,LinkObj> map=new HashMap<Integer,LinkObj>(3387771);
		compress compressor=new compress();
		BufferedReader br=new BufferedReader(new FileReader("/home/debarshi/Documents/wikipedia/wikisample/temp/inLinks.txt"));
		String readString;
		StringTokenizer stringTokenizer;
		
		while((readString=br.readLine())!=null){
			stringTokenizer=new StringTokenizer(readString,"\\|");
			int id=Integer.parseInt(stringTokenizer.nextToken());
			int count=0;
			while(stringTokenizer.hasMoreTokens())
			{
				compressor.put(Integer.parseInt(stringTokenizer.nextToken()));
				count++;
			}
			map.put(id,new LinkObj(compressor.getCompressed(),count));
		}
		ObjectOutputStream fout=new ObjectOutputStream(new FileOutputStream(new File("/home/debarshi/Documents/wikipedia/wikisample/temp/LinkMap")));
		fout.writeObject(map);
		fout.close();
		br.close();
	}
	@SuppressWarnings("unchecked")
	public static  HashMap<Integer,LinkObj> getLinks(String file) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		HashMap<Integer,LinkObj> map=new HashMap<Integer,LinkObj>(3387771);
		ObjectInputStream oin=new ObjectInputStream(new FileInputStream(new File(file)));
		map=(HashMap<Integer, LinkObj>) oin.readObject();
		return map;
	}

}
