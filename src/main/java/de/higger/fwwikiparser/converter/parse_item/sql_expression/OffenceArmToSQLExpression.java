package de.higger.fwwikiparser.converter.parse_item.sql_expression;

import de.higger.fwwikiparser.vo.OffenceArmVO;

public class OffenceArmToSQLExpression extends ArmToSQLExpression<OffenceArmVO> {

	@Override
	protected String getProcedureName() {
		return "   CALL AddOffenceArm";
	}

}
