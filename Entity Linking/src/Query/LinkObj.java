package Query;

import java.io.Serializable;

public class LinkObj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public byte[] compressed;
	public int totalInLinkCount;
	public LinkObj() {
		// TODO Auto-generated constructor stub
	}
	public LinkObj(byte[] b,int a)
	{
		this.compressed=b;
		this.totalInLinkCount=a;
	}
}
