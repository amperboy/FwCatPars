package de.higger.fwwikiparser.parser.deprecated;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import de.higger.fwwikiparser.vo.BaseParseItem;


public abstract class BaseSingleSiteParser<E extends BaseParseItem> extends BaseParser<E> {

	public abstract List<E> parseSite(String uri) throws IOException;
	
	protected abstract E parseItem(Element element);
}
