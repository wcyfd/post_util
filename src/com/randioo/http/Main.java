package com.randioo.http;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.randioo.http.command.Command;
import com.randioo.http.command.FileCommand;
import com.randioo.http.command.PropCommand;
import com.randioo.http.command.URLCommand;

public class Main {
	public static void main(String args[]) {
		FileCommand fileCommand = new FileCommand();
		PropCommand propCommand = new PropCommand();
		URLCommand urlCommand = new URLCommand();

		Map<String, Command> commands = new HashMap<>();
		commands.put(fileCommand.getCmd(), fileCommand);
		commands.put(propCommand.getCmd(), propCommand);
		commands.put(urlCommand.getCmd(), urlCommand);

		Set<Command> executeCommand = new HashSet<>();

		int i = 0;
		Command command = null;
		List<String> paramStack = new ArrayList<>();
		while (i < args.length) {
			String param = args[i++];
			if (commands.containsKey(param)) {
				if (command != null) {
					String[] paramStringArray = new String[paramStack.size()];
					paramStack.toArray(paramStringArray);
					command.enter(paramStringArray);
					paramStack.clear();
				}
				command = commands.get(param);
				executeCommand.add(command);
			} else {
				paramStack.add(param);
			}
		}
		String[] paramStringArray = new String[paramStack.size()];
		paramStack.toArray(paramStringArray);
		command.enter(paramStringArray);
		paramStack.clear();

		Map<String, String> pairs = new HashMap<>();
		for (Command cmd : executeCommand) {
			cmd.execute(pairs);
		}
		System.out.println(pairs);

		try {
			doPost(urlCommand.getUrl(), pairs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void doPost(String urlString, Map<String, String> pairs) throws Exception {
		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);

		boolean first = false;
		try (PrintWriter out = new PrintWriter(conn.getOutputStream())) {
			for (Map.Entry<String, String> entrySet : pairs.entrySet()) {
				if (!first)
					out.print('&');

				String name = entrySet.getKey();
				String value = entrySet.getValue();
				out.print(name);
				out.print("=");
				out.print(value);
			}

		}

		StringBuilder response = new StringBuilder();
		try (Scanner in = new Scanner(conn.getInputStream())) {
			while (in.hasNextLine()) {
				response.append(in.nextLine());
				response.append("\n");
			}
		} catch (Exception e) {
			if (!(conn instanceof HttpURLConnection))
				throw e;
			InputStream err = ((HttpURLConnection) conn).getErrorStream();
			if (err == null)
				throw e;
			Scanner in = new Scanner(err);
			response.append(in.nextLine());
			response.append("\n");
		}

		System.out.println(response.toString());
	}

}
