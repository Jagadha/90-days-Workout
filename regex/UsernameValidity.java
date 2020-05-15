package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidity {
	public static void main(String[] args) {
		String inp = "Balaji_1" ;
		String pat = "[A-Za-z0-9._@$]{8,}";
		
		Pattern compile = Pattern.compile(pat);
		Matcher matcher = compile.matcher(inp);
		System.out.println(matcher.matches());
	}

}
