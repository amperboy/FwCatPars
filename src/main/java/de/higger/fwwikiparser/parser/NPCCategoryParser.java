package de.higger.fwwikiparser.parser;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.NPCVO;

public class NPCCategoryParser extends BaseCategoryParser<NPCVO> {

	@Override
	protected String getCategoryStart() {
		return PropertieHelper.getInstance().getPropertie("wiki.parser.category.npcs");
	}
	
	@Override
	protected String[] getIgnoredSubSites() {
		String[] exclustions = {
			"/index.php/Auftrags-NPC",	
			"/index.php/Aggressive_NPCs",
			"/index.php/Gruppen-NPC",
			"/index.php/Haustier",
			"/index.php/Invasions-NPC",
			"/index.php/NPC",
			"/index.php/Phasenwesen",
			"/index.php/Resistenz-NPC",
			"/index.php/Schlag",
			"/index.php/Teilimmunit%C3%A4t",
			"/index.php/Unangreifbare_NPCs",
			"/index.php/Unique-NPC",
			"/index.php/Vollimmunit%C3%A4t",
			"/index.php/Diener_von_Beispieluser"				
		};
		return exclustions;
	}





}
