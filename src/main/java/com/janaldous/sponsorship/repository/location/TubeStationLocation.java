package com.janaldous.sponsorship.repository.location;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TubeStationLocation {

	public List<String> readTubeStations() throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/tfl/stations.geojson"));
		JsonElement json = JsonParser.parseReader(reader);
		JsonObject main = json.getAsJsonObject();
		JsonArray features = main.get("features").getAsJsonArray();
		List<String> output = new ArrayList<>();
		features.forEach(x -> {
			JsonObject feature = x.getAsJsonObject();
			JsonObject properties = feature.get("properties").getAsJsonObject();
			String name = properties.get("Name").getAsString();
			String address = properties.get("description").getAsString();
			String postCode = getPostCode(address);
			String postCodeDistrict = getPostCodeDistrict(address);
			if (postCode.equals("SW1H 0BD")) {
				
				output.add(postCode + "\t" + name);
			}
//			output.add(name + ",\"" + address + "\"," + postCode + "," + postCodeDistrict);
		});
		
		return output;
	}
	
	public static String getPostCode(String address) {
		String[] arr = address.split(", ");
		if (arr.length >= 1) {
			return arr[arr.length - 1];
		}
		return null;
	}

	public static String getPostCodeDistrict(String address) {
		String postCode = getPostCode(address);
		String[] split = postCode.split(" ");
		if (split.length == 1) {
			return postCode;
		} else if (split.length != 2) {
			throw new IllegalArgumentException("Post code format is invalid: " + address);
		}
		return split[0];
	}

}
