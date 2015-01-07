package de.higger.fwwikiparser.parser;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.NPCVO;

public class OffenceCategoryParser extends BaseCategoryParser<NPCVO> {

	@Override
	protected String getCategoryStart() {
		return PropertieHelper.getInstance().getPropertie("wiki.parser.category.arms.offence");
	}
	
	@Override
	protected String[] getIgnoredSubSites() {
		String[] exclustions = {
			"/index.php/Angriffswaffe",
			"/index.php/Angriffswaffe/St%C3%A4rkste"
		};
		return exclustions;
	}





}
