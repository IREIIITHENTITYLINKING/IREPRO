package TitleMapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BinarySearchTitle {
	
	static RandomAccessFile foffset;
	static RandomAccessFile fmain;
	static DataInputStream finput;
	static BufferedReader ftitle;
	static long off[];
	static String titles[];
	static int check;
	static int num_lines = 14041180;
	public BinarySearchTitle() {
		check = 0;
		try {
			foffset=new RandomAccessFile("/home/debarshi/Documents/wikipedia/wikisample/Important_File/titleoff.txt","r");
			fmain=new RandomAccessFile("/home/debarshi/Documents/wikipedia/wikisample/Important_File/titlesort.txt","r"); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public BinarySearchTitle(int val) {
		off = new long[num_lines];
		check = 1;
		try {
			finput=new DataInputStream(new BufferedInputStream(new FileInputStream("/home/debarshi/Documents/wikipedia/wikisample/Important_File/titleoff.txt")));
			for (int iter = 0; iter < off.length; iter++) off[iter] = finput.readLong();
			fmain=new RandomAccessFile("/home/debarshi/Documents/wikipedia/wikisample/Important_File/titlesort.txt","r"); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public BinarySearchTitle(String val) {
		//off = new long[14041180];
		titles=new String[num_lines-1];
		check = 2;
		try {
			//finput=new DataInputStream(new BufferedInputStream(new FileInputStream("/home/dilipvamsi/Dropbox/sem8/ire/project/titles/titleoff.txt")));
			ftitle=new BufferedReader(new FileReader("/home/debarshi/Documents/wikipedia/wikisample/Important_File/titlesort.txt"));
			
			//for (int iter = 0; iter < off.length; iter++) off[iter] = finput.readLong();
			for(int iter=0;iter<titles.length;iter++) titles[iter]=ftitle.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int Search (String title) {
		if (check == 2) return SearchCompleteMemory(title);
		else if (check==1) return SearchMemory(title);
		else return SearchDisc(title);
	}
	
	private int SearchCompleteMemory(String title) {
		int low = 0, high = num_lines-2;
		while (low < high) {
			int mid = low+(high - low)/2;
			String readLine[] = titles[mid].split(" ", 2);
			int val = title.compareToIgnoreCase(readLine[1].trim());
			if (val < 0) high = mid;
			else if (val > 0) low = mid;
			else return Integer.parseInt(readLine[0]);
		}
		return -1;
	}
	
	private int SearchMemory (String title) {
		try {
			return binarySearchMemory(0, num_lines-2, title);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	
	private static int binarySearchMemory(long low,long high,String title) throws IOException
	{
		if (high < low) return -1;
		long mid=low+(high-low)/2;
		long midoffset=off[(int)mid];
		long next=off[(int)mid + 1];
		byte[] temp=new byte[(int)(next-midoffset-1)];
		fmain.seek(midoffset);
		fmain.read(temp);
		String readLine[] = new String(temp).split(" ", 2);
		//System.out.println(mid + " " + readLine[1]);
		int val = title.compareToIgnoreCase(readLine[1].trim());
		if ( val < 0) {
			return binarySearchMemory(low, mid - 1, title);
		} else if (val > 0) {
			return binarySearchMemory(mid + 1, high, title);
		} else {
			//return readLine[0] + " " + readLine[1];
			return Integer.parseInt(readLine[0]);
		}
	}
	
	private int SearchDisc (String title) {
		try {
			return binarySearchDisc(0,num_lines-2, title);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void close () {
		try {
			if (check == 2) ftitle.close();
			else if (check == 1) finput.close();
			else foffset.close();
			fmain.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		BinarySearchTitle title=new BinarySearchTitle(100);
		String findit = "the new york times";
		System.out.println(title.Search(findit)+ " " +findit);
	}
	
	private static int binarySearchDisc(long low,long high,String title) throws IOException
	{
		if (high < low) return -1;
		long mid=low+(high-low)/2;
		long midoffset=mid*8;
		long next=midoffset+8;
		foffset.seek(midoffset);
		long offsetStart=foffset.readLong();
		foffset.seek(next);
		long offsetEnd=foffset.readLong();
		byte[] temp=new byte[(int)(offsetEnd-offsetStart-1)];
		fmain.seek(offsetStart);
		fmain.read(temp);
		String readLine[] = new String(temp).split(" ", 2);
		//System.out.println(mid + " " + readLine[1]);
		int val = title.compareToIgnoreCase(readLine[1].trim());
		if ( val < 0) {
			return binarySearchDisc(low, mid - 1, title);
		} else if (val > 0) {
			return binarySearchDisc(mid + 1, high, title);
		} else {
			//return readLine[0] + " " + readLine[1];
			return Integer.parseInt(readLine[0]);
		}
	}
}