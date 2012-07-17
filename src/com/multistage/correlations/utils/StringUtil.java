package com.multistage.correlations.utils;

// do text formating
import java.util.StringTokenizer;
import java.util.*;

/**
 * StringUtil - String utility class
 */

public class StringUtil {

	static private Map<String, String> s_mime;

	static private Map<String, String> s_java;

	static {
		s_mime = new TreeMap<String, String>();
		s_mime.put("UTF-8", "UTF8");
		s_mime.put("US-ASCII", "8859_1");
		s_mime.put("ISO-8859-1", "8859_1");
		s_mime.put("ISO-8859-2", "8859_2");
		s_mime.put("ISO-8859-3", "8859_3");
		s_mime.put("ISO-8859-4", "8859_4");
		s_mime.put("ISO-8859-5", "8859_5");
		s_mime.put("ISO-8859-6", "8859_6");
		s_mime.put("ISO-8859-7", "8859_7");
		s_mime.put("ISO-8859-8", "8859_8");
		s_mime.put("ISO-8859-9", "8859_9");
		s_mime.put("ISO-2022-JP", "JIS");
		s_mime.put("SHIFT_JIS", "SJIS");
		s_mime.put("EUC-JP", "EUCJIS");
		s_mime.put("GB2312", "GB2312");
		s_mime.put("BIG5", "Big5");
		s_mime.put("EUC-KR", "KSC5601");
		s_mime.put("ISO-2022-KR", "ISO2022KR");
		s_mime.put("KOI8-R", "KOI8_R");

		s_mime.put("EBCDIC-CP-US", "CP037");
		s_mime.put("EBCDIC-CP-CA", "CP037");
		s_mime.put("EBCDIC-CP-NL", "CP037");
		s_mime.put("EBCDIC-CP-DK", "CP277");
		s_mime.put("EBCDIC-CP-NO", "CP277");
		s_mime.put("EBCDIC-CP-FI", "CP278");
		s_mime.put("EBCDIC-CP-SE", "CP278");
		s_mime.put("EBCDIC-CP-IT", "CP280");
		s_mime.put("EBCDIC-CP-ES", "CP284");
		s_mime.put("EBCDIC-CP-GB", "CP285");
		s_mime.put("EBCDIC-CP-FR", "CP297");
		s_mime.put("EBCDIC-CP-AR1", "CP420");
		s_mime.put("EBCDIC-CP-HE", "CP424");
		s_mime.put("EBCDIC-CP-CH", "CP500");
		s_mime.put("EBCDIC-CP-ROECE", "CP870");
		s_mime.put("EBCDIC-CP-YU", "CP870");
		s_mime.put("EBCDIC-CP-IS", "CP871");
		s_mime.put("EBCDIC-CP-AR2", "CP918");

		s_java = new TreeMap<String, String>();
		s_java.put("UTF8", "UTF-8");
		s_java.put("8859_1", "ISO-8859-1");
		s_java.put("8859_2", "ISO-8859-2");
		s_java.put("8859_3", "ISO-8859-3");
		s_java.put("8859_4", "ISO-8859-4");
		s_java.put("8859_5", "ISO-8859-5");
		s_java.put("8859_6", "ISO-8859-6");
		s_java.put("8859_7", "ISO-8859-7");
		s_java.put("8859_8", "ISO-8859-8");
		s_java.put("8859_9", "ISO-8859-9");
		s_java.put("JIS", "ISO-2022-JP");
		s_java.put("SJIS", "Shift_JIS");
		s_java.put("EUCJIS", "EUC-JP");
		s_java.put("GB2312", "GB2312");
		s_java.put("BIG5", "Big5");
		s_java.put("KSC5601", "EUC-KR");
		s_java.put("ISO2022KR", "ISO-2022-KR");
		s_java.put("KOI8_R", "KOI8-R");

		s_java.put("CP037", "EBCDIC-CP-US");
		s_java.put("CP037", "EBCDIC-CP-CA");
		s_java.put("CP037", "EBCDIC-CP-NL");
		s_java.put("CP277", "EBCDIC-CP-DK");
		s_java.put("CP277", "EBCDIC-CP-NO");
		s_java.put("CP278", "EBCDIC-CP-FI");
		s_java.put("CP278", "EBCDIC-CP-SE");
		s_java.put("CP280", "EBCDIC-CP-IT");
		s_java.put("CP284", "EBCDIC-CP-ES");
		s_java.put("CP285", "EBCDIC-CP-GB");
		s_java.put("CP297", "EBCDIC-CP-FR");
		s_java.put("CP420", "EBCDIC-CP-AR1");
		s_java.put("CP424", "EBCDIC-CP-HE");
		s_java.put("CP500", "EBCDIC-CP-CH");
		s_java.put("CP870", "EBCDIC-CP-ROECE");
		s_java.put("CP870", "EBCDIC-CP-YU");
		s_java.put("CP871", "EBCDIC-CP-IS");
		s_java.put("CP918", "EBCDIC-CP-AR2");
	}

	/*
	 * ...
	 * 
	 */

	/**
	 * Return a String with all occurrences of the "from" String within
	 * "original" replaced with the "to" String. If the "original" string
	 * contains no occurrences of "from", "original" is itself returned, rather
	 * than a copy.
	 * 
	 * @param original
	 *            the original String
	 * @param from
	 *            the String to replace within "original"
	 * @param to
	 *            the String to replace "from" with
	 * 
	 * @returns a version of "original" with all occurrences of the "from"
	 *          parameter being replaced with the "to" parameter.
	 */
	public static String replace(String original, String from, String to) {
		int from_length = from.length();

		if (from_length != to.length()) {
			if (from_length == 0) {
				if (to.length() != 0) {
					throw new IllegalArgumentException(
							"Replacing the empty string with something was attempted");
				}
			}

			int start = original.indexOf(from);

			if (start == -1) {
				return original;
			}

			char[] original_chars = original.toCharArray();

			StringBuffer buffer = new StringBuffer(original.length());

			int copy_from = 0;
			while (start != -1) {
				buffer.append(original_chars, copy_from, start - copy_from);
				buffer.append(to);
				copy_from = start + from_length;
				start = original.indexOf(from, copy_from);
			}

			buffer.append(original_chars, copy_from, original_chars.length
					- copy_from);

			return buffer.toString();
		} else {
			if (from.equals(to)) {
				return original;
			}

			int start = original.indexOf(from);

			if (start == -1) {
				return original;
			}

			StringBuffer buffer = new StringBuffer(original);

			// Use of the following Java 2 code is desirable on performance
			// grounds...

			/*
			 * // Start of Java >= 1.2 code... while (start != -1) {
			 * buffer.replace(start, start + from_length, to); start =
			 * original.indexOf(from, start + from_length); } // End of Java >=
			 * 1.2 code...
			 */

			// The *ALTERNATIVE* code that follows is included for backwards
			// compatibility with Java 1.0.2...

			// Start of Java 1.0.2-compatible code...
			char[] to_chars = to.toCharArray();
			while (start != -1) {
				for (int i = 0; i < from_length; i++) {
					buffer.setCharAt(start + i, to_chars[i]);
				}

				start = original.indexOf(from, start + from_length);
			}
			// End of Java 1.0.2-compatible code...

			return buffer.toString();
		}
	}

	/**
	 * Return a String with all occurrences of the "search" String within
	 * "original" removed. If the "original" string contains no occurrences of
	 * "search", "original" is itself returned, rather than a copy.
	 * 
	 * @param original
	 *            the original String
	 * @param search
	 *            the String to be removed
	 * 
	 * @returns a version of "original" with all occurrences of the "from"
	 *          parameter removed.
	 */
	public static String remove(String original, String search) {
		return replace(original, search, "");
	}

	/**
	 * Return the first String found sandwiched between "leading" and "trailing"
	 * in "string", or null if no such string is found.
	 * 
	 * @param string
	 *            the original String
	 * @param leading
	 *            the String to replace within "original"
	 * @param trailing
	 *            the String to replace "from" with
	 * 
	 * @returns the first String sandwiched between "leading" and "trailing" in
	 *          "string" - or null if no such string is found.
	 */
	static String getSandwichedString(String string, String leading,
			String trailing) {
		int i_start = string.indexOf(leading);

		if (i_start < 0) {
			return null;
		}

		i_start += leading.length();

		int i_end = string.indexOf(trailing, i_start);

		if (i_end < 0) {
			return null;
		}

		return string.substring(i_start, i_end);
	}

	/**
	 * Takes a list of objects and concatenates the toString() representation of
	 * each object and returns the result.
	 * 
	 * @param objects
	 *            an array of objects
	 * 
	 * @returns a string formed by concatenating string representations of the
	 *          objects in the array.
	 */
	static public String concat(Object[] objects) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < objects.length; i++) {
			buffer.append(objects[i].toString());
		}

		return buffer.toString();
	}

	/**
	 * Creates a string of length "length" composed of the character "c", or the
	 * null string if c <= 0.
	 * 
	 * @param length
	 *            the length of the returned string
	 * @param c
	 *            the character is solely consists of
	 * 
	 * @returns a string of length "length" composed of the character "c".
	 */
	public static String fill(char c, int length) {
		if (length <= 0) {
			return "";
		}

		char[] chars = new char[length];
		for (int i = 0; i < length; i++) {
			chars[i] = c;
		}

		return new String(chars);
	}

	/**
	 * Return true if "string" contains "find".
	 * 
	 * @param string
	 *            the string whose contents are searched
	 * @param find
	 *            the string to be located as a substring
	 * 
	 * @returns true if "string" contains "find".
	 */
	static boolean contains(String string, String find) {
		return (string.indexOf(find) >= 0);
	}

	/**
	 * Return reversed version of "string".
	 * 
	 * @param string
	 *            the string to be reversed
	 * @param find
	 *            the string to be located as a substring
	 * 
	 * @returns reversed version of "string"
	 */
	static String reverse(String string) {
		return new StringBuffer(string).reverse().toString();
	}

	// another examplew
	static String replaceAllWords1(String original, String find,
			String replacement) {
		String result = "";
		String delimiters = "+-*/(),. ";
		StringTokenizer st = new StringTokenizer(original, delimiters, true);
		while (st.hasMoreTokens()) {
			String w = st.nextToken();
			if (w.equals(find)) {
				result = result + replacement;
			} else {
				result = result + w;
			}
		}
		return result;
	}

	public static String toJava(String mimeCharset) {
		return (String) s_mime.get(mimeCharset.toUpperCase());
	}

	public static String toMIME(String encoding) {
		return (String) s_java.get(encoding.toUpperCase());
	}

}
