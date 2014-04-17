package TitleMapper;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class RedirectTitle {
	public static TreeMap<Integer, Integer> mapit = new TreeMap<>();
	public RedirectTitle() {
		
	}
	public static void main(String[] args) throws IOException {
		BufferedReader re = new BufferedReader(new FileReader("E:\\IRE_Project\\titles\\RedirectID-TitleMapper.txt"));
		//PrintWriter fout=new PrintWriter(new BufferedWriter(new FileWriter("E:\\IRE_Project\\titles\\redirectShit")));
		DataOutputStream fout = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("E:\\IRE_Project\\titles\\redirectShit")));
		PrintWriter Pout=new PrintWriter(new BufferedWriter(new FileWriter("E:\\IRE_Project\\titles\\Pshit")));
		BufferedReader rmshitFile = new BufferedReader(new FileReader("C:\\Users\\dilipvamsi\\Dropbox\\sem8\\ire\\project\\SpecialPages.txt"));
		//DataOutputStream off = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("/home/dilipvamsi/Dropbox/sem8/ire/project/reoff.txt")));
		HashSet<String> rmshit = new HashSet<>();
		String line;
		while ((line = rmshitFile.readLine())!=null) rmshit.add(line.toLowerCase());
		rmshitFile.close();
		BinarySearchTitle st = new BinarySearchTitle(2);
		BinarySearchID si = new BinarySearchID(2);
		System.out.println("storing shit is done");
		int val;
		while ((line = re.readLine())!= null) {
			String l[] = line.split(" ", 2);
			//mapit.put(st.Search(l[0]), st.Search(l[1]));
			String c[] = l[1].split(":",2);
			if (rmshit.contains(c[0])) continue;
			l[1] = l[1].split("#")[0];
			l[1] = l[1].replaceAll("_", " ");
			if(l[1].contains("%")) {
				l[1] = l[1].replaceAll("%21", "!");
				l[1] = l[1].replaceAll("%23", "#");
				l[1] = l[1].replaceAll("%24", "$");
				l[1] = l[1].replaceAll("%26", "&");
				l[1] = l[1].replaceAll("%27", "'");
				l[1] = l[1].replaceAll("%28", "(");
				l[1] = l[1].replaceAll("%29", ")");
				l[1] = l[1].replaceAll("%2A", "*");
				l[1] = l[1].replaceAll("%2B", "+");
				l[1] = l[1].replaceAll("%2C", ",");
				l[1] = l[1].replaceAll("%2F", "/");
				l[1] = l[1].replaceAll("%3A", ":");
				l[1] = l[1].replaceAll("%3B", ";");
				l[1] = l[1].replaceAll("%3D", "=");
				l[1] = l[1].replaceAll("%3F", "?");
				l[1] = l[1].replaceAll("%40", "@");
				l[1] = l[1].replaceAll("%5B", "[");
				l[1] = l[1].replaceAll("%5D", "]");
				
				l[1] = l[1].replaceAll("%20", " ");
				l[1] = l[1].replaceAll("%22", "\"");
				l[1] = l[1].replaceAll("%25", "%");
				l[1] = l[1].replaceAll("%2D", "-");
				l[1] = l[1].replaceAll("%2E", ".");
				l[1] = l[1].replaceAll("%3C", "<");
				l[1] = l[1].replaceAll("%3E", ">");
				l[1] = l[1].replaceAll("%5C", "\\");
				l[1] = l[1].replaceAll("%5E", "^");
				l[1] = l[1].replaceAll("%5F", "_");
				l[1] = l[1].replaceAll("%60", "`");
				l[1] = l[1].replaceAll("%7B", "{");
				l[1] = l[1].replaceAll("%7C", "|");
				l[1] = l[1].replaceAll("%7D", "}");
				l[1] = l[1].replaceAll("%7E", "~");
			}
			val = st.Search(l[1]);
			if (val == -1) {
				Pout.println(l[0] + " "  + si.Search(Integer.parseInt(l[0])) + " | " + l[1]);
				continue;
			}
			//System.exit(0);
			//fout.println(l[0]+ " " + val);
			fout.writeInt(Integer.parseInt(l[0]));
			fout.writeInt(val);
		}
		/*
		re.close();
		System.out.println("storing shit is done");
		for (Map.Entry<Integer, Integer> ent: mapit.entrySet()) {
			off.writeInt(ent.getKey());
			off.writeInt(ent.getValue());
		}
		off.flush();
		off.close();
		*/
		Pout.flush();Pout.close();
		fout.flush();fout.close();
	}
}
