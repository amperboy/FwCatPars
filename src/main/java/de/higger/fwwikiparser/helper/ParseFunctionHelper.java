package de.higger.fwwikiparser.helper;

public class ParseFunctionHelper {
	
	public static String getLowerValue(String requValue) {
		return getSplittedValue(requValue, (byte) 0);
	}

	public static String getUpperValue(String requValue) {
		return getSplittedValue(requValue, (byte) 1);
	}

	public static String getSplittedValue(String requValue, byte pos) {

		requValue = requValue.replaceAll("\\.", "");
		requValue = requValue.replaceAll(" ", "");

		String[] spl = requValue.split("-");
		byte maxPos = (byte) (spl.length - 1);

		requValue = requValue.split("-")[pos > maxPos ? maxPos : pos];
		return requValue;
	}
}
