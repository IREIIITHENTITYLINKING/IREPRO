package Query;
import java.util.ArrayList;


public class SentenceParser {

	private int position=0;
	private int prevPos=0;
	private int currPos=0;
	private int totalLength=0;
	static char[] punctuations={',','"','(',')','\'','-','-','.'};
	public StringBuilder st;
	public SentenceParser(String input) {
		st=new StringBuilder(input);
		totalLength=st.length();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String text="The International Institute of Information Technology, Hyderabad (IIIT Hyderabad) is an autonomous university in Hyderabad, Andhra Pradesh, India. It was established in 1998, and is one of the most prestigious institutes of the country. It emphasizes research from the undergraduate level, which makes it different from the other leading engineering institutes in India. It has been a consistent performer from India in ACM International Collegiate Programming Contest ICPC and finished at last year. Raj Reddy, the only Indian to win the Turing Award, is chairman of the board of governors.The institute runs CS courses and research projects and is focused on research. It gives the students interaction with industry, preparation in entrepreneurship and personality development courses. IIIT Hyderabad the mentor institute to Indian Institute of Information Technology, SriCity ";
		//String text="On 1998,Maradona scored the famous \" hand of god \" goal against England.";
		String text="rose family (Rosaceae). It is one of the most widely cultivated tree fruits, and the most widely known of the many members of genus Malus that are used by humans. Apples grow on small, deciduous trees. The tree originated in Central Asia, where its wild ancestor, Malus sieversii, is still found today. Apples have been grown for thousands of years in Asia and Europe, and were brought to North America by European colonists. Apples have been present in the mythology and religions of many cultures, including Norse, Greek and Christian traditions. In 2010, the fruit's genome was decoded as part of research on disease control and selective breeding in apple production. There are more than 7,500 known cultivars of apples, resulting in a range of desired characteristics. Different cultivars are bred for various tastes and uses, including cooking, fresh eating and cider production. Domestic apples are generally propagated by grafting, although wild apples grow readily from seed. Trees are prone to a number of fungal, bacterial and pest problems, which can be controlled by a number of organic and non-organic means. About 69 million tons of apples were grown worldwide in 2010, and China produced almost half of this total. The United States is the second-leading producer, with more than 6% of world production. Turkey is third, followed by Italy, India and Poland. Apples are often eaten raw, but can also be found in many prepared foods (especially desserts) and drinks. Many beneficial health effects are thought to result from eating apples; however, two forms of allergies are seen to various proteins found in the fruit. ";
		SentenceParser parser=new SentenceParser(text);
		
		ArrayList<Words> words;
		while(!(words=parser.getNextSentence()).isEmpty())
		{
			for(Words word:words)
			{
				System.out.println(word.word+ " "+word.start+" "+word.end+" "+parser.st.subSequence(word.start,word.end));
			}
		}
		
	}
	public ArrayList<Words> getNextSentence() {
		ArrayList<Words> wordsList=new ArrayList<>();
		boolean flag=false;
		boolean prevSet=false;
		while(currPos<totalLength&&!flag)
		{
			if(!prevSet)
			{
				char c=st.charAt(currPos);
				if(c=='\n')
				{
					currPos++;
					return wordsList;  ///if we get fullstop without prev set then return the previously formed words
				}
				while(currPos<totalLength)
				{
					if(matchesPunctuation(c))
					{
						if(c!='"' && c!='\'')
						{
							Words word=new Words(c+"");
							word.start=currPos;
							word.end=currPos+1;
							wordsList.add(word);
						}
						
					}
					else if(c==' ')
					{
						
					}
					else
					{
						break;
					}
					currPos++;
					if(currPos<totalLength)
						c=st.charAt(currPos);
				}
				if(currPos==totalLength)
					break;
				prevSet=true;
				prevPos=currPos;
				currPos++;
			}
			else
			{
				//previous is set
				char c=st.charAt(currPos);
				if(c=='\n')
				{
					//if we get fullstop with prevset then return the previously formed words alongwith the current word
					Words word=new Words(st.subSequence(prevPos,currPos));
					//System.out.println(word.word);
					word.start=prevPos;
					word.end=currPos;
					wordsList.add(word); //add the word
					prevSet=false; //previous is marked false
					currPos++;
					return wordsList;  
				}
				while(currPos<totalLength&&!matchesPunctuation(c))
				{
					if(c==' ') //matches space then obtain the word
					{
						Words word=new Words(st.subSequence(prevPos,currPos));
						//System.out.println(word.word);
						word.start=prevPos;
						word.end=currPos;
						wordsList.add(word); //add the word
						prevSet=false;
						currPos++;
						break;
					}
					currPos++;
					if(currPos<totalLength)
						c=st.charAt(currPos);
				}
				if(currPos<totalLength&&prevSet)
				{
					//got a punctuations
					Words word=new Words(st.subSequence(prevPos,currPos));
					//System.out.println(word.word);
					word.start=prevPos;
					word.end=currPos;
					wordsList.add(word); //add the word
					prevSet=false;
					
					//add the punctuation
					if(((c!='"')&&(c!='\'')))
					{
						Words word2=new Words(c+"");
						word2.start=currPos;
						word2.end=currPos+1;
						wordsList.add(word2);
					}
					
					
					currPos++;
					if(currPos<totalLength)
						c=st.charAt(currPos);
				}
				else if(currPos==totalLength&&prevSet)
				{
					Words word=new Words(st.subSequence(prevPos,currPos));
					//System.out.println(word.word);
					word.start=prevPos;
					word.end=currPos;
					wordsList.add(word); //add the word
					return wordsList;
				}
			}
		}
		return wordsList;
	}
	public final boolean matchesPunctuation(char ch)
	{
		boolean matches=false;
		for(int i=0;i<punctuations.length;i++)
		{
			if(punctuations[i]==ch)
			{
				matches=true;
				break;
			}
		}
		return matches;
	}

}
