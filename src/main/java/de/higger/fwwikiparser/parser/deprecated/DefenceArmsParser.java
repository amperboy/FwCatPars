package de.higger.fwwikiparser.parser.deprecated;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.DefenceArmVO;

public class DefenceArmsParser extends BaseArmsParser<DefenceArmVO> {
	
	public String getSiteToParse() {
		String offenceArmsSite = PropertieHelper.getInstance().getPropertie("wiki.parser.arms.defence");
		
		return offenceArmsSite;
	}
}

