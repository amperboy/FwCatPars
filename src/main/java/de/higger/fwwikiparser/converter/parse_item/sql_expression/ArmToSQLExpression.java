package de.higger.fwwikiparser.converter.parse_item.sql_expression;

import de.higger.fwwikiparser.converter.parse_item.BaseParseItemExpression;
import de.higger.fwwikiparser.enums.RaceType;
import de.higger.fwwikiparser.vo.BaseArmVO;

public abstract class ArmToSQLExpression<E extends BaseArmVO> implements BaseParseItemExpression<E, String> {

	protected abstract String getProcedureName();
	
	public String convert(E arm) {
		RaceType requiredRace = arm.getRequiredRace();
		
		String raceId = requiredRace == null ? "null" : "'"+requiredRace.getId()+"'";
		
		StringBuilder sql = new StringBuilder(getProcedureName());
		
		sql.append("('");
		sql.append(arm.getName());
		sql.append("','");
		sql.append(arm.getUrl());
		sql.append("','");
		sql.append(arm.getStrength());
		sql.append("','");
		sql.append((arm.isNondurable() ? 1 : 0));
		sql.append("','");
		sql.append(arm.getRequiredPower());
		sql.append("','");
		sql.append(arm.getRequiredIntelligence());
		sql.append("','");
		sql.append(arm.getRequiredCourses());
		sql.append("',");
		sql.append(raceId);
		sql.append(");");
		
		return sql.toString();
		
	}
	


}
