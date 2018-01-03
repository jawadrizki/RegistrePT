package com.poissontata.metier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	public static void main(String[] args) {
		String s = "molkfklkj";
		System.out.println(getSpecialCharacterCount(s));
	}

	public static boolean getSpecialCharacterCount(String s) {
	     if (s == null || s.trim().isEmpty()) return false;
	     if(s.length() < 8) return false;
	     Pattern p = Pattern.compile("[^A-Za-z0-9]");
	     Matcher m = p.matcher(s);
	     boolean b = m.find();
	     if (b == true) return false;
	     else return true;
	 }
}
