package de.higger.fwwikiparser.parser.site;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import de.higger.fwwikiparser.parser.BaseParser;
import de.higger.fwwikiparser.vo.BaseParseItem;

public abstract class BaseSiteParser<E extends BaseParseItem> extends BaseParser {
	
	public abstract List<E> parseSite(String uri) throws IOException;

	protected abstract E parseItem(Element element);
}
