package com.janaldous.sponsorship.repository.tfl;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import lombok.NonNull;


public class TflAddressUtil {
	
	public final static Pattern POST_CODE_DISTRICT_PATTERN = Pattern.compile("^[A-Z][A-HJ-Y]?[0-9][A-Z0-9]?$", Pattern.CASE_INSENSITIVE);
	
	public final static Pattern POST_CODE_PATTERN = Pattern.compile("^([A-Z][A-HJ-Y]?[0-9][A-Z0-9]? ?[0-9][A-Z]{2}|GIR)$", Pattern.CASE_INSENSITIVE);
	
	public static String getPostCodeDistrictFromPostCode(@NonNull String postCode) {
		String[] split = postCode.split(" ");
		String postCodeDistrict = null;
		if (split.length == 1) {
			postCodeDistrict = split[0];
		} else if (split.length == 2) {
			postCodeDistrict = split[0];
		} else {
			throw new IllegalArgumentException("Post code is in the wrong format");
		}
		if (!isValidPostCodeDistrict(postCodeDistrict)) throw new IllegalArgumentException("Post code format is invalid: " + postCode);
		return postCodeDistrict;
	}
	
	public static boolean isValidPostCodeDistrict(@NonNull String postCodeDistrict) {
		Matcher matcher = POST_CODE_DISTRICT_PATTERN.matcher(postCodeDistrict);
		return matcher.find();
	}
	
	public static boolean isValidPostCode(@NonNull String postCode) {
		Matcher matcher = POST_CODE_PATTERN.matcher(postCode);
		return matcher.find();
	}
	
	public static String getPostCode(@NonNull String address) {
		String[] arr = address.split(",");
		if (arr.length >= 1) {
			String postCode = arr[arr.length - 1];
			if (!(isValidPostCodeDistrict(postCode) || isValidPostCode(postCode))) 
				throw new IllegalArgumentException("Post code format is invalid: " + postCode);
			return postCode;
		}
		return null;
	}

	public static String getPostCodeDistrict(@NonNull String address) {
		String postCode = getPostCode(address);
		
		String[] split = postCode.split(" ");
		String postCodeDistrict = null;
		if (split.length == 1 || split.length == 2) {
			postCodeDistrict = split[0].trim();
		} else {
			throw new IllegalArgumentException("Post code format is invalid: " + Arrays.toString(split));
		}
		if (!isValidPostCodeDistrict(postCodeDistrict)) throw new IllegalArgumentException("Post code district format is invalid: " + postCode);
		return postCodeDistrict;
	}

}
