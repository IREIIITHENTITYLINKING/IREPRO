package TitleMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class BinarySearchRedirectTitle {
	private ArrayList<redirectID> list=new ArrayList<>();
	private String file="/home/debarshi/Documents/wikipedia/wikisample/Important_File/redirectIDID";
	
	public BinarySearchRedirectTitle(){
		// TODO Auto-generated constructor stub
		try{
			BufferedReader br=new BufferedReader(new FileReader(file));
			String read;
			while((read=br.readLine())!=null)
			{
				String temp[]=read.split(" ");
				int id1=Integer.parseInt(temp[0]);
				int id2=Integer.parseInt(temp[1]);
				list.add(new redirectID(id1, id2));
			}		
		}catch(Exception e)
		{
			System.out.println("error in loading");
			e.printStackTrace();
		}
		
	}
	public int Search(int ID)
	{
		//System.out.println(list.size());
		int val= Collections.binarySearch(list,new redirectID(ID, 0));	
		if(val<0)
			return -1;
		else
			return list.get(val).Id2;
	}
}

