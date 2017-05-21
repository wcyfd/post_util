package com.randioo.http.command;

import java.awt.image.ColorConvertOp;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class FileCommand implements Command {

	private List<File> files = new ArrayList<>();

	@Override
	public String getCmd() {
		return "-file";
	}

	@Override
	public void enter(String[] params) {
		for (String path : params) {
			files.add(new File(path));
		}
	}

	@Override
	public void execute(Map<String, String> pairs) {
		byte[] b = new byte[1024];
		int fileIndex = 1;
		for (File file : files) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
				int len = 0;
				while ((len = bis.read(b)) != -1) {
					baos.write(b, 0, len);
				}

				byte[] bytes = baos.toByteArray();
				String str = Base64.encodeBase64String(bytes);				
				pairs.put("file" + fileIndex, str);
				pairs.put("filename" + +fileIndex, file.getName());
				fileIndex++;

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
