package de.higger.fwwikiparser.parser;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import de.higger.fwwikiparser.vo.BaseParseItem;

public abstract class BaseSiteParser<E extends BaseParseItem> {
	public abstract List<E> parseSite(String uri) throws IOException;
	
	protected abstract E parseItem(Element element);
}
