package de.higger.fwwikiparser.parser.category;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.AbilityVO;

public class AbilityCategoryParser extends BaseCategoryParser<AbilityVO> {
	
	public class AbilityParseJob extends ParseSiteJob {
		
		private Pattern intervalPattern = Pattern.compile("([^0-9]*)([\\.0-9]+[ -]*[\\.0-9]*)([^0-9]*)");
		
		public AbilityParseJob(List<Link> sitesToParse) {
			super(sitesToParse);
		}

		@Override
		public AbilityVO parseSite(Link siteToParse) throws IOException {

			AbilityVO abilityVO  = new AbilityVO();
			
			abilityVO.setUrl(siteToParse.getPath());
			
			String baseUrl = PropertieHelper.getPropertieHelperInstance().getBaseURL();
			Document doc = getDocument(baseUrl+siteToParse.getPath());
			
			try {
				String abilityName = doc.getElementById("firstHeading").text();
				abilityVO.setName(abilityName);
				
				if("Lerntechnik".equals(abilityName)) {
					abilityVO.setBaseTime(35000);
					
					abilityVO.setMaxLevel((short) 50);					
				}
				else {
				
					String baseTime = doc.getElementById("CFcalc").text();
					abilityVO.setBaseTime(Integer.valueOf(baseTime));
					
					String maxLevel = doc.getElementById("CFmax").text();
					abilityVO.setMaxLevel(Short.valueOf(maxLevel));
				}
				
			}
			catch(Exception e) {
				System.err.println("parsing ability failed:  " + e.getMessage()	+ " site: " + siteToParse.getPath());
				
				abilityVO.setExportable(false);
			}
			
			return abilityVO;
		}		
	}
	
	@Override
	protected String getCategoryStart() {
		return PropertieHelper.getPropertieHelperInstance().getProperty("wiki.parser.category.abilities");
	}
	
	@Override
	protected String[] getIgnoredSubSites() {
		String[] exclustions = {
				"/index.php/Auftragsbeziehungen/Nutzen"
				, "/index.php/Auftragsbeziehungen/Zelle"
				, "/index.php/Handwerkskunst/Max-Stufe"
				, "/index.php/Buch"
				, "/index.php/Charakterf%C3%A4higkeit"
		};
		return exclustions;
	}

	@Override
	protected ParseSiteJob createParseSiteJob(
			List<Link> allocatedSiteList) {
		
		return new AbilityParseJob(allocatedSiteList);
	}
}
