package com.randioo.http;

import java.util.HashMap;
import java.util.Map;

public class FileCommand implements ICommandDecoder {

	private Map<String, String> valueMap = new HashMap<>();

	@Override
	public void decode(String cmd) {
		String[] key_values = cmd.split(",");
		for (int i = 0; i < key_values.length; i++) {
			String key_value = key_values[i];
			String[] pair = key_value.split(",");
			String name = pair[0];
			String value = pair[1];
			valueMap.put(name, value);
		}
	}

	@Override
	public PostData execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
