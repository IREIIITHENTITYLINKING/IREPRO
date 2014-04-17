package TitleMapper;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BinarySearchID {
	
	static RandomAccessFile foffset;
	static RandomAccessFile fmain;
	static DataInputStream finput;
	static long off[];
	static boolean check;
	public BinarySearchID() {
		check = false;
		try {
			foffset=new RandomAccessFile("/home/debarshi/Documents/wikipedia/wikisample/Important_File/idoff.txt","r");
			fmain=new RandomAccessFile("/home/debarshi/Documents/wikipedia/wikisample/Important_File/idsort.txt","r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BinarySearchID(int val) {
		off = new long[14041180];
		check = true;
		try {
			finput=new DataInputStream(new BufferedInputStream(new FileInputStream("/home/debarshi/Documents/wikipedia/wikisample/Important_File/idoff.txt")));
			for (int iter = 0; iter < off.length; iter++) off[iter] = finput.readLong();
			fmain=new RandomAccessFile("/home/debarshi/Documents/wikipedia/wikisample/Important_File/idsort.txt","r"); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public String Search(int id) throws IOException {
		if (check)
			return SearchMemory(id);
		else
			return SearchDisc(id);
	}
	
	private String SearchMemory(int id) throws IOException {
		return binarySearchMemory(0, 14041178, id);
	}
	
	private String SearchDisc(int id) throws IOException {
		return binarySearchDisc(0, 14041178, id);
	}

	public static void main(String[] args) throws IOException {
		BinarySearchID id=new BinarySearchID(10);
		int val = 856;
		System.out.println(val + " " + id.Search(val));
	}

	private static String binarySearchDisc(long low,long high,long id) throws IOException
	{
		if (high < low) return null;
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
		String readLine[] = new String(temp).trim().split(" ", 2);
		//System.out.println(mid + " " + readLine[0]);
		long val = Long.parseLong(readLine[0]);
		if (id < val) {
			return binarySearchDisc(low, mid - 1, id);
		} else if (id > val) {
			return binarySearchDisc(mid + 1, high, id);
		} else {
			//return readLine[0] +" " + readLine[1];
			return readLine[1];
		}
	}

	private static String binarySearchMemory(long low,long high,long id) throws IOException
	{
		if (high < low) return null;
		long mid=low+(high-low)/2;
		long midoffset=off[(int)mid];
		long next=off[(int)mid + 1];
		byte[] temp=new byte[(int)(next-midoffset-1)];
		fmain.seek(midoffset);
		fmain.read(temp);
		String readLine[] = new String(temp).trim().split(" ", 2);
		//System.out.println(mid + " " + readLine[1]);
		long val = Long.parseLong(readLine[0]);
		if (id < val) {
			return binarySearchMemory(low, mid - 1, id);
		} else if (id > val) {
			return binarySearchMemory(mid + 1, high, id);
		} else {
			//return readLine[0] +" " + readLine[1];
			return readLine[1];
		}
	}
}