package de.higger.fwwikiparser.parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BaseParser {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0";
	
	public static final int DEFAULT_TIMEOUT = 10000;

	protected Document getDocument(String uri) throws IOException {
		Document doc = Jsoup.connect(uri)
				.userAgent(BaseParser.USER_AGENT)
				.timeout(BaseParser.DEFAULT_TIMEOUT)
				.get();
		
		return doc;
	}
	
}
