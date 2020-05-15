package javastring;

public class Evenreverse {

	public static void main(String[] args) {
		String str = "When the world realise its own mistake, corona will dissolve automatically";
		String[] split = str.split(" ");

		for (int i = 0; i < split.length; i++) {
			if(i%2 != 0) {
				char[] ch = split[i].toCharArray();
				String evenrev ="";
				for (int j = ch.length-1; j >= 0; j--) {
					evenrev = evenrev+ch[j];
				}
				System.out.print(evenrev);
			}
			else {
				System.out.print(split[i]);
			}
			System.out.print("  ");
		}
	}

}
