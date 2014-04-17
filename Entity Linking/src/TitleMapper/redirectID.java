package TitleMapper;

public class redirectID implements Comparable<redirectID> {
	int Id1,Id2;
	public redirectID(int i1,int i2) {
		// TODO Auto-generated constructor stub
		Id1=i1;
		Id2=i2;
	}
	@Override
	public int compareTo(redirectID o) {
		// TODO Auto-generated method stub
		return this.Id1-o.Id1;
	}
}
