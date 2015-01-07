package de.higger.fwwikiparser.vo;

public class OffenceArmVO extends BaseArmVO {
	public String exportSQL() {
		return "AddOffenceArm"+super.exportSQL();
	}
}
