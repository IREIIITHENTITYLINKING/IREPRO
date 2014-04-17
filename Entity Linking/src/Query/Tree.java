package Query;

import java.util.LinkedList;
import java.util.Queue;


public class Tree {
	Node Tree_root;
	public int globalcount=-1;
	
	public final Node insertWord(Node r,char word[],int ptr)
	{
		if (r == null)
            r = new Node(word[ptr]);
 
        if (word[ptr] < r.data)
            r.left = insertWord(r.left, word, ptr);
        else if (word[ptr] > r.data)
            r.right = insertWord(r.right, word, ptr);
        else
        {
            if (ptr + 1 < word.length)
                r.equal = insertWord(r.equal, word, ptr + 1);
            else
            {
            	if(!r.isEnd)
            	{
            		r.isEnd = true;
            		r.endIndex=(++globalcount);
            	}
            }
        }
        return r;
			
	}
		
	public void printLevelOrderTree(Node root)
	{
		if(root==null)
		{
			System.out.println("Empty tree");
			return ;
		}
		Queue<Node> queue=new LinkedList<Node>();
		queue.add(root);
		while(!queue.isEmpty())
		{
			int initialQueueCount=queue.size();
			for(int i=0;i<initialQueueCount;i++)
			{
				Node temp=queue.poll();
				System.out.print(temp.data+" ");
				if(temp.left!=null)
					queue.add(temp.left);
				if(temp.equal!=null)
				{
					queue.add(temp.equal);
				}
				if(temp.right!=null)
					queue.add(temp.right);
			}
			System.out.println();			
		}
	}
	public int insertWord(String s) 
    { 
        Tree_root=insertWord(this.Tree_root,s.toCharArray(),0); 
        return globalcount;
    }
	private final int search(Node r, char[] word, int ptr)
    {
        if (r == null)
            return -1;
 
        if (word[ptr] < r.data)
            return search(r.left, word, ptr);
        else if (word[ptr] > r.data)
            return search(r.right, word, ptr);
        else
        {
            if (r.isEnd && ptr == word.length - 1)
                return r.endIndex;
            else if (ptr == word.length - 1)
                return -1;
            else
                return search(r.equal, word, ptr + 1);
        }        
    }
	
	public final int contains(String key)
	{
		return search(Tree_root, key.toCharArray(),0);
	}
	
}
