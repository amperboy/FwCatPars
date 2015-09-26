package de.higger.fwwikiparser.parser.category;

import java.util.List;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.OffenceArmVO;

public class OffenceCategoryParser extends BaseWeaponCategoryParser<OffenceArmVO> {

	public class OffenceParseJob extends BaseWeaponParseJob {

		public OffenceParseJob(List<Link> sitesToParse) {
			super(sitesToParse);
		}

		@Override
		protected OffenceArmVO createObject() {
			return new OffenceArmVO();
		}
		
	}
	
	@Override
	protected String getCategoryStart() {
		return PropertieHelper.getPropertieHelperInstance().getProperty("wiki.parser.category.arms.offence");
	}
	
	@Override
	protected String[] getIgnoredSubSites() {
		
		String[] exclustions = {
			"/index.php/Waffe_der_Furcht"
			, "/index.php/Angriffswaffe"
			, "/index.php/Angriffswaffe/St%C3%A4rkste"
		};
		
		return exclustions;
	}
	
	@Override
	protected ParseSiteJob createParseSiteJob(List<Link> allocatedSiteList) {
		return new OffenceParseJob(allocatedSiteList);
	}

}
