package de.higger.fwwikiparser.parser.deprecated;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import de.higger.fwwikiparser.vo.BaseParseItem;

public abstract class BaseParser<E extends BaseParseItem> {
	

	protected Document getDocument(String uri) throws IOException {
		Document doc = Jsoup.connect(uri)
				.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0")
				.timeout(10000)
				.get();
	
		return doc;
	}
	
	protected String getLowerValue(String requValue) {
		return getSplittedValue(requValue, (byte) 0);
	}
	protected String getUpperValue(String requValue) {
		return getSplittedValue(requValue, (byte) 1);
	}
	
	private String getSplittedValue(String requValue, byte pos) {
		
		requValue = requValue.replaceAll("\\.", "");
		requValue = requValue.replaceAll(" ", "");
		
		String[] spl = requValue.split("-");
		byte maxPos = (byte) (spl.length - 1);
		
		requValue = requValue.split("-")[pos > maxPos ? maxPos : pos];
		return requValue;
	}
}
