package de.higger.fwwikiparser;

import static de.higger.fwwikiparser.helper.PropertieHelper.getPropertieHelperInstance;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FileWriter {
	
	
	private final PrintStream out;

	public FileWriter() throws FileNotFoundException {
		String resultFileName = getPropertieHelperInstance().getProperty("conf.result.file","export.sql");
		FileOutputStream fos = new FileOutputStream(resultFileName);
		out = new PrintStream(fos);
	}
	
	public PrintStream getPrintStream() {
		return out;
	}
	
	public void close() {
		out.close();
	}
}
