package javastring;

public class Palindrome {

	public static void main(String[] args) {
		String s = "malayalam";
		char[] ch = s.toCharArray();
		String rev = ""; String rev1 = "";
//Way 1:
		for (int i = s.length()-1; i >=0; i--) {
			rev = rev + s.charAt(i);
		}
		if(rev.equals(s)) {
			System.out.println("Yes Palindrome");
		}
		else {
			System.out.println("No, not a Palindrome");
		}
//Way 2:
		for (int i = ch.length-1; i >= 0; i--) {
			rev1 = rev1 + ch[i];
		}
		if(rev1.equals(s)) {
			System.out.println("Yes Palindrome");
		}
		else {
			System.out.println("No, not a Palindrome");
		}
		
//Way 3:
		StringBuffer sb = new StringBuffer(s);
		String strev = sb.reverse().toString();
		System.out.println(strev.equals(s)?"Yes Palindrome":"No, not a Palindrome");
	}

}
