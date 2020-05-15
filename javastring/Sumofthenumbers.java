package javastring;

public class Sumofthenumbers {

	public static void main(String[] args) {
		String str = "asdf1qwer9as8d7";
		int s = 0, inp1, digit = 0, sum = 0;
		
//Way 1: Using regex
		int inp = Integer.parseInt(str.replaceAll("[^0-9]", ""));
		while(inp > 0) {
		inp1=inp%10;
		s=s+inp1;
		inp=inp/10;
		}
		System.out.println("Sum of the numbers is "+s);
//Way 2: Using Character class
		for (int i = 0; i < str.length(); i++) {
			String sd = "";
			if(Character.isDigit(str.charAt(i))) {
				sd = sd + str.charAt(i);
				digit = digit + Integer.parseInt(sd);
			}	
		}
		System.out.println("Sum of the numbers: "+digit);
//Way 3: Using ASCII
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i)>=48&&str.charAt(i)<=57) {
				sum +=  Integer.parseInt(String.valueOf(str.charAt(i)));
			}
		}
		System.out.println("Sum of the numbers: "+sum);
	}

}
