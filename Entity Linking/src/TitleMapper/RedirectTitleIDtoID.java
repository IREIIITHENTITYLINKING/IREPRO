package TitleMapper;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RedirectTitleIDtoID {
	static BinarySearchTitle title=new BinarySearchTitle();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br=new BufferedReader(new FileReader("/home/debarshi/Documents/wikipedia/wikisample/Important_File/redirectMap"));
		String read=null;
		PrintWriter fout=new PrintWriter(new BufferedOutputStream(new FileOutputStream("/home/debarshi/Documents/wikipedia/wikisample/Important_File/redirectIDID")));
		PrintWriter ferror=new PrintWriter(new BufferedWriter(new FileWriter("/home/debarshi/Documents/wikipedia/wikisample/Important_File/error")));
		
		while((read=br.readLine())!=null)
		{
			String temp[]=read.split(" ",2);
			int id1=Integer.parseInt(temp[0]);
			
			temp[1] = temp[1].split("#",2)[0];
			temp[1]=temp[1].toLowerCase().trim();
			temp[1]=temp[1].replaceAll("_"," ");
			temp[1]=temp[1].replaceAll("&amp;","");
			temp[1]=temp[1].replaceAll(" +", " ");
			if(temp[1].length()>1&&temp[1].charAt(0)==':')
				temp[1]=temp[1].substring(1);
			temp[1]=temp[1].trim();
			if(temp[1].equals(""))
				continue;
			if(temp[1].contains("%"))
			{	
				temp[1] = temp[1].replaceAll("%21", "!");
				temp[1] = temp[1].replaceAll("%23", "#");
				temp[1] = temp[1].replaceAll("%24", "$");
				temp[1] = temp[1].replaceAll("%26", "&");
				temp[1] = temp[1].replaceAll("%27", "'");
				temp[1] = temp[1].replaceAll("%28", "(");
				temp[1] = temp[1].replaceAll("%29", ")");
				temp[1] = temp[1].replaceAll("%2A", "*");
				temp[1] = temp[1].replaceAll("%2B", "+");
				temp[1] = temp[1].replaceAll("%2C", ",");
				temp[1] = temp[1].replaceAll("%2F", "/");
				temp[1] = temp[1].replaceAll("%3A", ":");
				temp[1] = temp[1].replaceAll("%3B", ";");
				temp[1] = temp[1].replaceAll("%3D", "=");
				temp[1] = temp[1].replaceAll("%3F", "?");
				temp[1] = temp[1].replaceAll("%40", "@");
				temp[1] = temp[1].replaceAll("%5B", "[");
				temp[1] = temp[1].replaceAll("%5D", "]");
				
				temp[1] = temp[1].replaceAll("%20", " ");
				temp[1] = temp[1].replaceAll("%22", "\"");
				temp[1] = temp[1].replaceAll("%25", "%");
				temp[1] = temp[1].replaceAll("%2D", "-");
				temp[1] = temp[1].replaceAll("%2E", ".");
				temp[1] = temp[1].replaceAll("%3C", "<");
				temp[1] = temp[1].replaceAll("%3E", ">");
				temp[1] = temp[1].replaceAll("%5C", "\\");
				temp[1] = temp[1].replaceAll("%5E", "^");
				temp[1] = temp[1].replaceAll("%5F", "_");
				temp[1] = temp[1].replaceAll("%60", "`");
				temp[1] = temp[1].replaceAll("%7B", "{");
				temp[1] = temp[1].replaceAll("%7C", "|");
				temp[1] = temp[1].replaceAll("%7D", "}");
				temp[1] = temp[1].replaceAll("%7E", "~");
			}
			int id2=title.Search(temp[1]);
			if(id2==-1)
			{
				ferror.println(temp[0]+" "+temp[1]);
			}
			else
			{
				fout.println(id1+" "+id2);
			}
		}
		br.close();
		fout.flush();
		ferror.flush();fout.close();ferror.close();
	}

}
