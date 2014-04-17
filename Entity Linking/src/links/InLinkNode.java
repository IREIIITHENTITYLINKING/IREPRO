package links;

public class InLinkNode implements Comparable<InLinkNode> {
	int pid;
	int incount;
	int fNo;
	String posting=new String();
	public InLinkNode(int p,int i,int fNO)
	{
		this.pid=p;
		this.incount=i;
		this.fNo=fNO;
	}
	@Override
	public int compareTo(InLinkNode o) {
		// TODO Auto-generated method stub
		return this.pid-o.pid;
	}
}
