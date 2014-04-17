package Query;

public class mark 
{
	public int start;
	public int end;
	public int globalStart=0;
	public int globalEnd=0;
	public int documentID=0;
	public double linkf;
	public double totalf;
	public StringBuilder st;
	mark(int s,int e)
	{
		start=s;
		end=e;
	}
	public void increment(int val)
	{
		start+=val;
		end+=val;
	}
}
