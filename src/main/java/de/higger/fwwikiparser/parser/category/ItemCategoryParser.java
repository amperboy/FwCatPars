package de.higger.fwwikiparser.parser.category;

import java.io.IOException;
import java.util.List;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.ItemVO;

public class ItemCategoryParser extends BaseCategoryParser<ItemVO> {

	public class ItemParseJob extends ParseSiteJob {

		public ItemParseJob(List<Link> sitesToParse) {
			super(sitesToParse);
		}

		@Override
		public ItemVO parseSite(Link siteToParse) throws IOException {

			ItemVO item = new ItemVO();
			item.setName(siteToParse.getName());
			item.setUrl(siteToParse.getPath());
			
			return item;
		}
		
	}
	
	@Override
	protected String getCategoryStart() {
		return PropertieHelper.getPropertieHelperInstance().getProperty(
				"wiki.parser.category.items");
	}

	@Override
	protected String[] getIgnoredSubSites() {
		String[] exclustions = { "/index.php/Auftrags-NPC",
				"/index.php/Aggressive_NPCs", "/index.php/Gruppen-NPC",
				"/index.php/Haustier", "/index.php/Invasions-NPC",
				"/index.php/NPC", "/index.php/Phasenwesen",
				"/index.php/Resistenz-NPC", "/index.php/Schlag",
				"/index.php/Teilimmunit%C3%A4t",
				"/index.php/Unangreifbare_NPCs", "/index.php/Unique-NPC",
				"/index.php/Vollimmunit%C3%A4t",
				"/index.php/Diener_von_Beispieluser" };
		return exclustions;
	}

	@Override
	protected BaseCategoryParser<ItemVO>.ParseSiteJob createParseSiteJob(
			List<Link> allocatedSiteList) {

		return new ItemParseJob(allocatedSiteList);
	}

}
