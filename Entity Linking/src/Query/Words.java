package Query;

public class Words {
	public CharSequence word;
	public String Word;
	Words(CharSequence w)
	{
		this.word=w;
		this.Word=word.toString().toLowerCase();
	}
	public int start,end;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return word.toString();
	}
}
