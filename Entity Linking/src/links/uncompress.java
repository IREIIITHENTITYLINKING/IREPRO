package links;

public class uncompress {
	int iter=0;
	byte[] val;

	public uncompress() {
		// TODO Auto-generated constructor stub
	}
	
	public void uncompressit(byte[] v) {
		this.val = v;
		this.iter = 0;
	}
	
	public final int getNext()
	{
		if(iter==val.length)
			return -1; //array ended
		int ret=0, pos = 0;
		while(val[iter]>0){
			ret+=(val[iter++])<<pos;
			pos+=7;
		}
		ret += (-1*val[iter++])<<pos;
		return ret;
	}	
}