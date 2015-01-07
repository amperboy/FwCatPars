package de.higger.fwwikiparser.parser;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.OffenceArmVO;

public class OffenceArmsParser extends BaseArmsParser<OffenceArmVO> {
	
	public String getSiteToParse() {
		String offenceArmsSite = PropertieHelper.getInstance().getPropertie("wiki.parser.arms.offence");
		
		return offenceArmsSite;
	}
}
