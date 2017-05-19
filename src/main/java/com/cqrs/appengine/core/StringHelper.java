package com.cqrs.appengine.core;

public class StringHelper {
	
	public static boolean IsNullOrWhitespace(String text)
	{
		return text == null || text.isEmpty() || text.trim().length() == 0;
	}
}
