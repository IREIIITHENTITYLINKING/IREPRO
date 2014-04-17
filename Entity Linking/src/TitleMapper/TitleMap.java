package TitleMapper;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class TitleMap {

	public TitleMap() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		BufferedReader br=new BufferedReader(new FileReader(args[0])); //input Reader file
		DataOutputStream foffset=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(args[1])));//offset file
		String read;
		long offset=0;
		foffset.writeLong(offset);
		while((read=br.readLine())!=null)
		{
			byte[] b=(read+"\n").getBytes();
			offset+=b.length;
			foffset.writeLong(offset);
		}
		foffset.close();
		br.close();
	}
}
