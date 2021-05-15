package com.janaldous.sponsorship.repository.postgres.util;

public class TradingAsExtractor {

	public String extractTradingAs(String name) {
		int slashIndex = name.toLowerCase().indexOf("t/a");
		if (slashIndex >= 0) {
			return name.substring(0, slashIndex).trim();
		}
		
		int longFormIndex = name.toLowerCase().indexOf("trading as");
		if (longFormIndex >= 0) {
			return name.substring(0, longFormIndex).trim();
		}
		
		return name;
	}
	
}
