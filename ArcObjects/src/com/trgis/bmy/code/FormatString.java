package com.trgis.bmy.code;

public class FormatString {

	public static String DString(int num) {
		if (num < 10) {
			return "0" + Integer.toString(num);
		} else {
			return Integer.toString(num);
		}
	}
}
