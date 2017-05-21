package com.randioo.http.command;

import java.util.HashMap;
import java.util.Map;

public class PropCommand implements Command {

	private Map<String, String> map = new HashMap<>();

	@Override
	public String getCmd() {
		return "-prop";
	}

	@Override
	public void enter(String[] params) {
		int i = 0;
		while (i < params.length) {
			String name = params[i++];
			String value = params[i++];
			map.put(name, value);
		}
	}

	@Override
	public void execute(Map<String, String> pairs) {
		pairs.putAll(map);
	}

}
