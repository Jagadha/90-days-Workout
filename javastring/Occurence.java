package javastring;

import java.util.HashMap;
import java.util.Map;

public class Occurence {

	public static void main(String[] args) {
		String inp = "You have no choice other than following me!";
		int count = 0, cnt=0;
		char[] ch = inp.toCharArray();
//Way 1:		
		for (int i = 0; i < ch.length; i++) {
			if(ch[i]=='o') {
				count = count + 1;
			}
		}
		System.out.println("Occurence of 'o' is " + count);
		
//Way 2:
		
		for (int i = 0; i < inp.length(); i++) {
			if(inp.charAt(i)=='o') {
				cnt = cnt + 1;
			}
		}
		System.out.println("Occurence of 'o' is " + count);
//Way 3:
		
		System.out.println("Occurence of 'o' is "+inp.replaceAll("[^o]", "").length());
		
//Way 4:
		Map<Character, Integer> m = new HashMap<>();
		for (int i = 0; i < ch.length; i++) {
			if(m.containsKey(ch[i])) {
				m.put(ch[i], m.get(ch[i])+1);
			}else{
				m.put(ch[i], 1);
			}
		}
		System.out.println("Occurence of 'o' is "+m.get('o'));
	}

}
