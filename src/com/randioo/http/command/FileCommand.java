package com.randioo.http.command;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class FileCommand implements Command {

	private Map<String, File> files = new HashMap<>();

	@Override
	public String getCmd() {
		return "-file";
	}

	@Override
	public void enter(String[] params) {
		int i = 0;
		while (i < params.length) {
			String name = params[i++];
			String path = params[i++];
			files.put(name, new File(path));
		}
	}

	@Override
	public void execute(Map<String, String> pairs) {
		byte[] b = new byte[1024];
		for (Map.Entry<String, File> entrySet : files.entrySet()) {
			String name = entrySet.getKey();
			File file = entrySet.getValue();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
				int len = 0;
				while ((len = bis.read(b)) != -1) {
					baos.write(b, 0, len);
				}

				byte[] bytes = baos.toByteArray();
				String str = Base64.encodeBase64String(bytes);
				pairs.put(name, str);

				System.out.println("file name=" + file.getName());

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
