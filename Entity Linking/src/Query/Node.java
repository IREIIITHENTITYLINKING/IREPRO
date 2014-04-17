package Query;


public class Node {
	char data;
	int endIndex;
	Node left;
	Node right;
	Node equal;
	boolean isEnd;
	public int count=0;
	Node(char d)
	{
		data=d;
		endIndex=-1;
	}
}
