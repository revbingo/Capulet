package com.revbingo.capulet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CapuletParser {

	JsonElement json; 
	
	public CapuletParser(String jsonStr) {
		JsonParser parser = new JsonParser();
		json = parser.parse(jsonStr);
	}

	public String get(String expr) {
		return get(json.getAsJsonObject(), expr);
	}
	
	private String get(JsonObject json, String expr) {
		String[] pieces = expr.split("\\.");

		JsonElement currentObj = json.getAsJsonObject();
		for(String key : pieces) {
			
			if(key.contains("[")) {
				String keyName = key.substring(0, key.indexOf("["));
				String index = key.substring(key.indexOf("[") + 1, key.indexOf("]"));
				
				currentObj = ((JsonObject) currentObj).get(keyName);
				if(currentObj == null) throw new ParseException("No such key: " + key);
				
				try {
					currentObj = currentObj.getAsJsonArray().get(Integer.valueOf(index));
				} catch(IndexOutOfBoundsException e) {
					throw new ParseException("Index " + index + " is out of bounds for this array");
				}
			} else {
				currentObj = ((JsonObject) currentObj).get(key);
			}
			if(currentObj == null) throw new ParseException("No such key: " + key);
		}
		return currentObj.getAsString();
	}

}
