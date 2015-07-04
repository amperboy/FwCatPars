package de.higger.fwwikiparser.converter.parse_item.sql_expression;

import de.higger.fwwikiparser.converter.parse_item.BaseParseItemExpression;
import de.higger.fwwikiparser.vo.AbilityVO;

public class AbilityToSQLExpression implements BaseParseItemExpression<AbilityVO, String> {

	public String convert(AbilityVO ability) {
		
		StringBuilder sql = new StringBuilder("   CALL AddChara('");
		sql.append(ability.getName());
		sql.append("','");
		sql.append(ability.getUrl());
		sql.append("','");
		sql.append(ability.getMaxLevel());
		sql.append("','");
		sql.append(ability.getBaseTime());
		sql.append("');");
		
		return sql.toString();
	}

}
