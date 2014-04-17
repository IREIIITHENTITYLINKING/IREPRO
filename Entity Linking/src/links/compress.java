package links;

import java.util.ArrayList;

public class compress {
	ArrayList<Byte> bytes=new ArrayList<>();
	public final void put(int abc)
	{
		while(true)
		{		
			if(abc>>7>0)
			{
				//System.out.print((abc&127)+" ");
				bytes.add((byte) (abc&127));
				abc=abc>>7;		
			}
			else
			{
				//System.out.println((abc&127)*-1);
				bytes.add((byte) ((abc&127)*-1));
				break;
			}
		}
	}
	
	public final byte[] getCompressed()
	{
		byte[] bytes1=new byte[bytes.size()];;
		for(int i=0;i<bytes1.length;i++)
			bytes1[i]=bytes.get(i);
		bytes.clear();
		return bytes1;
	}
}
