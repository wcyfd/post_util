package com.randioo.http.command;

import java.util.Map;

public class URLCommand implements Command {

	private String url;

	public String getUrl() {
		return url;
	}

	@Override
	public String getCmd() {
		// TODO Auto-generated method stub
		return "-url";
	}

	@Override
	public void enter(String[] params) {
		// TODO Auto-generated method stub
		this.url = params[0];
	}

	@Override
	public void execute(Map<String, String> pairs) {
		// TODO Auto-generated method stub

	}

}
