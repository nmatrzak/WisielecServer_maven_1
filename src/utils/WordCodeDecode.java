package utils;

import java.util.Arrays;
import java.util.List;

/**
 * klasa odpowiedzialna za dekodowanie polskich znakow/class responsible for
 * decoding Polish characters
 * 
 * @author Piotr Podgórski
 * @version 1.0
 * @since 2019-01-01
 */
public class WordCodeDecode {

	/** znak separatora/The Constant SEP. */
	private final static String SEP = "@";

	/** polskie znaki/The Constant specPolishChars. */
	private final static String specPolishChars = "ĄĆĘŁŃÓŚŹŻąćęłńóśźż";

	/** lista kodujaca polskie znakiThe Constant coded. */
	private final static List<Integer> coded = Arrays.asList(260, 262, 280, 321, 323, 0, 346, 379, 377, 261, 263, 281,
			322, 324, 0, 347, 380);

	/**
	 * dekodowanie /Decode.
	 *
	 * @param word - slowo/the word
	 * @return slowo/the word
	 */
	public static String decode(String word) {
		for (int i = 0; i < coded.size(); i++) {
			word = word.replace("&#" + i + ";", String.valueOf(specPolishChars.charAt(i)));
		}
		return word;
	}

	/**
	 * wstawienie polsich znakow/insert polish char
	 *
	 * @param c znak/the c
	 * @return the string
	 */
	private static String charToSpec(char c) {
		for (int i = 0; i < specPolishChars.length(); i++) {
			if (c == specPolishChars.charAt(i)) {
				return SEP + i + SEP;
			}
		}
		return String.valueOf(c);
	}

	/**
	 * kodowanie polskiego slowa do slowa ze polskimi znakami/Code polish word to
	 * word with specs.
	 *
	 * @param word - slowo/the word
	 * @return the string
	 */
	public static String codePolishWordToWordWithSpecs(String word) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < word.length(); i++) {
			result.append(charToSpec(word.charAt(i)));
		}
		return result.toString();
	}

	/**
	 * dekodowanie slowa z poskimi znakami do slowa po polsku/Decode word with specs
	 * to polish word.
	 *
	 * @param word slowo/the word
	 * @return the string
	 */
	public static String decodeWordWithSpecsToPolishWord(String word) {
		for (int i = 0; i < specPolishChars.length(); i++) {
			word = word.replace(SEP + i + SEP, specPolishChars.substring(i, i + 1));
		}
		return word;
	}

}
