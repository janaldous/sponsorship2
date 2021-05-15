package com.janaldous.sponsorship.repository.postgres.util;

public class NameNormalizer {

	public String normalize(String originalName) {
		String trimmedName = originalName.trim();
		trimmedName = trimmedName.replaceAll("[\\(\\).!?\\\\-]", "");
		boolean allCaps = originalName.equals(originalName.toUpperCase());
		String[] arr = trimmedName.split(" ");
		String typeOfCompany = arr[arr.length-1].toLowerCase();
		if (trimmedName.toLowerCase().endsWith("ltd.")) {
			return trimmedName.substring(0, trimmedName.length()-1);
		}
		if ("limited".equals(typeOfCompany) || "ltd.".equals(typeOfCompany)) {
			if (allCaps) {
				arr[arr.length-1] = "LTD";
			} else {
				arr[arr.length-1] = "Ltd";
			}
			return String.join(" ", arr);
		}
		
		return trimmedName;
	}
	
}
