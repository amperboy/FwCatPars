package de.higger.fwwikiparser.parser.category;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.OffenceArmVO;

public class OffenceCategoryParser extends BaseCategoryParser<OffenceArmVO> {

	public class OffenceParseJob extends BaseCategoryParser<OffenceArmVO>.ParseSiteJob {

		public OffenceParseJob(List<String> sitesToParse) {
			super(sitesToParse);
		}

		@Override
		public OffenceArmVO parseSite(String siteToParse) throws IOException {

			OffenceArmVO offenceArmVO = new OffenceArmVO();
			offenceArmVO.setUrl(siteToParse);
			
			String baseUrl = PropertieHelper.getPropertieHelperInstance().getBaseURL();
			Document doc = getDocument(baseUrl+siteToParse);
			
			try {
				
				
				String weaponName = doc.getElementById("firstHeading").text();
				offenceArmVO.setName(weaponName);
				
//				offenceArmVO.setNondurable(isNondurable);
//				offenceArmVO.setRequiredCourses(requiredCourses);
//				offenceArmVO.setRequiredIntelligence(requiredIntelligence);
//				offenceArmVO.setRequiredPower(requiredPower);
//				offenceArmVO.setRequiredRace(requiredRace);
//				offenceArmVO.setStrength(strength);
				
			} catch (Exception e) {
				
				offenceArmVO.setExportable(false);
			}
			
			
			
			return offenceArmVO;
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
	protected BaseCategoryParser<OffenceArmVO>.ParseSiteJob createParseSiteJob(
			List<String> allocatedSiteList) {
		
		return new OffenceParseJob(allocatedSiteList);
	}





}
