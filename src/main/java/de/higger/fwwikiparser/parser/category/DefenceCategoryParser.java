package de.higger.fwwikiparser.parser.category;

import java.util.List;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.DefenceArmVO;

public class DefenceCategoryParser extends BaseWeaponCategoryParser<DefenceArmVO> {

	public class DefenceParseJob extends BaseWeaponParseJob {

		public DefenceParseJob(List<Link> sitesToParse) {
			super(sitesToParse);
		}

		@Override
		protected DefenceArmVO createObject() {
			return new DefenceArmVO();
		}
		
	}
	
	@Override
	protected String getCategoryStart() {
		return PropertieHelper.getPropertieHelperInstance().getProperty("wiki.parser.category.arms.defence");
	}
	
	@Override
	protected String[] getIgnoredSubSites() {
		
		String[] exclustions = {
			"/index.php/Verteidigungswaffe"
			, "/index.php/Verteidigungswaffe/St%C3%A4rkste"
			, "/index.php/Deutschland-Trikot_(W11)"
		};
		
		return exclustions;
	}
	
	@Override
	protected ParseSiteJob createParseSiteJob(List<Link> allocatedSiteList) {
		return new DefenceParseJob(allocatedSiteList);
	}

}
