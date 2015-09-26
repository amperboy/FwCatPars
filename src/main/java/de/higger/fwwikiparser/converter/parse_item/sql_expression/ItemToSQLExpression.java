package de.higger.fwwikiparser.converter.parse_item.sql_expression;

import de.higger.fwwikiparser.converter.parse_item.BaseParseItemExpression;
import de.higger.fwwikiparser.vo.ItemVO;

public class ItemToSQLExpression implements
		BaseParseItemExpression<ItemVO, String> {

	public String convert(ItemVO item) {
		StringBuilder sql = new StringBuilder("   CALL AddItem('");

		sql.append(item.getName());
		sql.append("','");
		sql.append(item.getUrl());
		sql.append("');");

		return sql.toString();
	}

}
