package com.randioo.http;

import java.util.HashMap;
import java.util.Map;

public class CommandIdentity {
	private Map<String, ICommandDecoder> commanderMap = new HashMap<>();
	private String value;

	public void setValue(String value) {
		this.value = value;
	}

	public void setCommander(String name, Class<ICommandDecoder> clazz) {
		try {
			ICommandDecoder cmd = clazz.newInstance();
			commanderMap.put(name, cmd);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void identity() {
		String[] params = value.split(" ");
		int i = 0;
		while (i < params.length) {
			String option = params[i++];
			String value = params[i++];

			ICommandDecoder cmd = commanderMap.get(option);
			if (cmd != null) {
				cmd.decode(value);
			} else {
				System.exit(0);
			}
		}
	}

	public void execute() {
		StringBuilder sb = new StringBuilder();
		for (ICommandDecoder cmd : commanderMap.values()) {
			PostData param = cmd.execute();
			if(param!=null){
				
			}
		}
	}
}
