import java.util.HashMap;

public class NumberBase_Stub {

	private static HashMap<Character, Integer> constants_to_int = new HashMap<Character,Integer>() {{
		put(Character.valueOf('0'), 0);
		put(Character.valueOf('1'), 1);
		put(Character.valueOf('2'), 2);
		put(Character.valueOf('3'), 3);
		put(Character.valueOf('4'), 4);
		put(Character.valueOf('5'), 5);
		put(Character.valueOf('6'), 6);
		put(Character.valueOf('7'), 7);
		put(Character.valueOf('8'), 8);
		put(Character.valueOf('9'), 9);
		put(Character.valueOf('a'), 10);
		put(Character.valueOf('b'), 11);
		put(Character.valueOf('c'), 12);
		put(Character.valueOf('d'), 13);
		put(Character.valueOf('e'), 14);
		put(Character.valueOf('f'), 15);
		put(Character.valueOf('g'), 16);
		put(Character.valueOf('h'), 17);
		put(Character.valueOf('i'), 18);
		put(Character.valueOf('j'), 19);
		put(Character.valueOf('k'), 20);
		put(Character.valueOf('l'), 21);
		put(Character.valueOf('m'), 22);
		put(Character.valueOf('n'), 23);
		put(Character.valueOf('o'), 24);
		put(Character.valueOf('p'), 25);
		put(Character.valueOf('q'), 26);
		put(Character.valueOf('r'), 27);
		put(Character.valueOf('s'), 28);
		put(Character.valueOf('t'), 29);
		put(Character.valueOf('u'), 30);
		put(Character.valueOf('v'), 31);
		put(Character.valueOf('w'), 32);
		put(Character.valueOf('x'), 33);
		put(Character.valueOf('y'), 34);
		put(Character.valueOf('z'), 35);
	}};

	private static HashMap<Integer,Character> constants_from_int = new HashMap<Integer,Character>() {{
		put(0 , Character.valueOf('0'));
		put(1 , Character.valueOf('1'));
		put(2 , Character.valueOf('2'));
		put(3 , Character.valueOf('3'));
		put(4 , Character.valueOf('4'));
		put(5 , Character.valueOf('5'));
		put(6 , Character.valueOf('6'));
		put(7 , Character.valueOf('7'));
		put(8 , Character.valueOf('8'));
		put(9 , Character.valueOf('9'));
		put(10, Character.valueOf('a'));
		put(11, Character.valueOf('b'));
		put(12, Character.valueOf('c'));
		put(13, Character.valueOf('d'));
		put(14, Character.valueOf('e'));
		put(15, Character.valueOf('f'));
		put(16, Character.valueOf('g'));
		put(17, Character.valueOf('h'));
		put(18, Character.valueOf('i'));
		put(19, Character.valueOf('j'));
		put(20, Character.valueOf('k'));
		put(21, Character.valueOf('l'));
		put(22, Character.valueOf('m'));
		put(23, Character.valueOf('n'));
		put(24, Character.valueOf('o'));
		put(25, Character.valueOf('p'));
		put(26, Character.valueOf('q'));
		put(27, Character.valueOf('r'));
		put(28, Character.valueOf('s'));
		put(29, Character.valueOf('t'));
		put(30, Character.valueOf('u'));
		put(31, Character.valueOf('v'));
		put(32, Character.valueOf('w'));
		put(33, Character.valueOf('x'));
		put(34, Character.valueOf('y'));
		put(35, Character.valueOf('z'));
	}};

	// Convert from base N to base10
	private static int toBase10(String input, int base_in) {
		int sum = 0;
		int i = 0;
		int n = input.length();

		// TODO: Fill out this method.
		for (char c : input.toCharArray()) {
			i = constants_to_int.get(c);
			sum += i * (int)Math.pow(base_in, --n);
		}

		return sum;
	}

	// Convert from base10 to base N
	private static String toBaseN(int input, int base_out) {
		int n = input;
		String sum = "";

		if (input == 0) return "0";

		// TODO: Fill out this method
        while (n > 0) {
            int r = n % base_out;
            n /= base_out;
            sum = constants_from_int.get(r) + sum;
        }

		return sum;
	}

	public static String convert(String input, int base_in, int base_out) {
		// Convert to Base 10 first
		int base_10 = toBase10(input,base_in);

		// Convert to the Output Base
		String result = toBaseN(base_10,base_out);

		return result;
	}

	public static void main(String args[]) {

		// Some examples of converting.
		System.out.println(convert("100",10,10));
		System.out.println(convert("1",2,10));
		System.out.println(convert("10",2,10));
		System.out.println(convert("f",16,10));
		System.out.println(convert("m",32,10));

		System.out.println(convert("1",10,2));
	}	
}
