package TitleMapper;


public class BinaryRedirectTitleSearcher {
	
}
class redirectMap implements Comparable<redirectMap>
{
	int id;
	String title;
	public redirectMap(String i,String title) {
		// TODO Auto-generated constructor stub
		id=Integer.parseInt(i);
		this.title=title;
	}
	@Override
	public int compareTo(redirectMap o) {
		// TODO Auto-generated method stub
		return this.id-o.id;
	}
	
}
