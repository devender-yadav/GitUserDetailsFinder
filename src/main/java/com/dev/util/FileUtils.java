package com.dev.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

	public static void createFile(List<String> lines, String filePath) {
		Path file = Paths.get(filePath);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
