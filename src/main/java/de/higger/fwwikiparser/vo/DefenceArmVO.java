package de.higger.fwwikiparser.vo;

public class DefenceArmVO extends BaseArmVO {
	public String exportSQL() {
		return "AddDefenceArm"+super.exportSQL();
	}
}
