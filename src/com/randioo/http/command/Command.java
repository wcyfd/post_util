package com.randioo.http.command;

import java.util.Map;

public interface Command {
	String getCmd();

	void enter(String[] params);

	void execute(Map<String, String> pairs);
}
