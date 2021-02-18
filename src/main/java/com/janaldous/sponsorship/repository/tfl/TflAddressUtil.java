package com.janaldous.sponsorship.repository.tfl;

import java.util.Arrays;

import lombok.NonNull;


public class TflAddressUtil {
	
	public static String getPostCode(@NonNull String address) {
		String[] arr = address.split(",");
		if (arr.length >= 1) {
			return arr[arr.length - 1];
		}
		return null;
	}

	public static String getPostCodeDistrict(@NonNull String address) {
		String postCode = getPostCode(address);
		String[] split = postCode.split(" ");
		if (split.length == 1) {
			return postCode;
		} else if (split.length != 2) {
			throw new IllegalArgumentException("Post code format is invalid: " + Arrays.toString(split));
		}
		return split[0];
	}

}
