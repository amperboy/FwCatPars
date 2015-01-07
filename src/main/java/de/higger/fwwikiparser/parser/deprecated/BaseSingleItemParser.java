package de.higger.fwwikiparser.parser.deprecated;

import java.io.IOException;

import de.higger.fwwikiparser.vo.BaseParseItem;

public abstract class BaseSingleItemParser<E extends BaseParseItem> extends BaseParser<E> {

	public abstract E parseSite(String uri) throws IOException;
}
