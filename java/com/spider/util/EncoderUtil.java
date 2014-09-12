package com.spider.util;

public class EncoderUtil {

	public static String encode(String string) {
		
		if (string == null)
			return null;
		
		string = string.replaceAll("[?]","_");
		string = string.replaceAll("=","_");
		string = string.replaceAll("&","_");
		string = string.replaceAll("#","_");
		return string;
	}

	
}
