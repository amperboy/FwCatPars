package de.higger.fwwikiparser.converter.parse_item.sql_expression;

import de.higger.fwwikiparser.vo.DefenceArmVO;

public class DefenceArmTOSQLExpression extends ArmToSQLExpression<DefenceArmVO> {

	@Override
	protected String getProcedureName() {
		return "AddDefenceArm";
	}

}
