package javastring;

public class CharDigitcheck {

	public static void main(String[] args) {
		String str = "1. It is Work from Home not Work for Home";
		int uppercount = 0, lowercount = 0, digitcount = 0, space = 0, upper = 0, lower = 0 , number = 0, sp = 0; 
//Way 1: Using Character class
		for (int i = 0; i < str.length(); i++) {
			if(Character.isUpperCase(str.charAt(i))){
				uppercount +=  1;
			} else if (Character.isLowerCase(str.charAt(i))) {
				lowercount += 1;
			} else if (Character.isDigit(str.charAt(i))) {
				 digitcount += 1;
			} else if (Character.isSpaceChar(str.charAt(i))) {
				space += 1;
			}
		}
		System.out.println("Upper Case: "+uppercount+"\tLower Case: "+lowercount+"\tNumbers: "+digitcount+"\tSpaces: "+space);

//Way 2: Using regex
		
		System.out.print("Upper case count: "+str.replaceAll("[^A-Z]", "").length());
		System.out.print("\tLower case count: "+str.replaceAll("[^a-z]", "").length());
		System.out.print("\tDigit count: "+ str.replaceAll("[^0-9]", "").length());
		System.out.print("\tSpaces count: "+str.replaceAll("\\S", "").length()+"\n");

//Way 3: Using ASCII
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i)>=65&&str.charAt(i)<=90){
				upper++;
			} else if (str.charAt(i)>=97&&str.charAt(i)<=122) {
				lower++;
			} else if (str.charAt(i)>=48&&str.charAt(i)<=57) {
				 number++;
			} else if (str.charAt(i)==32) {
				sp++;
			}
		}
		System.out.println("Upper Case: "+upper+"\tLower Case: "+lower+"\tNumbers: "+number+"\tSpaces: "+sp);
	}
	
}
