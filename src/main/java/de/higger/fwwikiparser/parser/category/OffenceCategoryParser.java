package de.higger.fwwikiparser.parser.category;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.higger.fwwikiparser.enums.RaceType;
import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.OffenceArmVO;

public class OffenceCategoryParser extends BaseCategoryParser<OffenceArmVO> {

	public class OffenceParseJob extends BaseCategoryParser<OffenceArmVO>.ParseSiteJob {

		private static final String REQUIRED_POWER_PATTERN = "(Mindeststärke: )([0-9]+[.0-9]*)";
		private static final String REQUIRED_COURSES_PATTERN = "(Mindestakademielimit: )([0-9]+[.0-9]*)";
		private static final String REQUIRED_INTELLIGENCE_PATTERN = "(Mindestintelligenz: )([0-9]+[.0-9]*)";
		private static final String REQUIRED_RACE_PATTERN = "(Rasse: )(.*)";

		public OffenceParseJob(List<String> sitesToParse) {
			super(sitesToParse);
		}

		@Override
		public OffenceArmVO parseSite(String siteToParse) throws IOException {

			OffenceArmVO offenceArmVO = new OffenceArmVO();
			offenceArmVO.setUrl(siteToParse);
			
			String baseUrl = PropertieHelper.getPropertieHelperInstance().getBaseURL();
			Document doc = getDocument(baseUrl+siteToParse);
			
			Element weaponLayout = doc.select("div#mw-content-text").first();
			
			try {
				
				
				String weaponName = doc.getElementById("firstHeading").text();
				offenceArmVO.setName(weaponName);
				
				boolean isNondurable = isNondurable(weaponLayout);				
				offenceArmVO.setNondurable(isNondurable);
				
				Elements requirements = weaponLayout.select(".layout_requirements").first().getElementsByTag("li");
				
				if(requirements.size() > 0) {
				
					int requiredPower = getRequiredPower(requirements);
					offenceArmVO.setRequiredPower(requiredPower);
					
					int requiredCourses = getRequiredCourses(requirements);
					offenceArmVO.setRequiredCourses(requiredCourses);

					int requiredIntelligence = getRequiredIntelligence(requirements);
					offenceArmVO.setRequiredIntelligence(requiredIntelligence);
					
					RaceType requiredRace = getRequiredRace(requirements);
					offenceArmVO.setRequiredRace(requiredRace);
					
				}
				
				short minimumStrength = getMinimumStrength(weaponLayout);
				offenceArmVO.setStrength(minimumStrength);
				
				
			} catch (Exception e) {
				
				offenceArmVO.setExportable(false);
			}
			
			if(offenceArmVO.getStrength() == 0) {
				System.err.println(offenceArmVO);
			}
			
			return offenceArmVO;
		}


		private int getRequiredPower(Elements requirements) {
			return getRequirementIntegerValue(REQUIRED_POWER_PATTERN, requirements);
		}
		
		private int getRequiredCourses(Elements requirements) {
			return getRequirementIntegerValue(REQUIRED_COURSES_PATTERN, requirements);
		}
		
		private int getRequiredIntelligence(Elements requirements) {
			
			return getRequirementIntegerValue(REQUIRED_INTELLIGENCE_PATTERN, requirements);
		}
		
		private RaceType getRequiredRace(Elements requirements) {
			
			String requirementValue = getRequirementValue(REQUIRED_RACE_PATTERN, requirements);

			RaceType requiredRace = null;

			if (requirementValue != null) {
				requiredRace = RaceType.getTypeByName(requirementValue);
			}
			
			return requiredRace;
		}

		private int getRequirementIntegerValue(String pattern, Elements requirements) {
			String requiredValueResult = getRequirementValue(pattern, requirements);
			
			int result = 0;
			
			if(requiredValueResult != null) {
				result = Integer.parseInt(requiredValueResult);
			}
			
			return result;
		}
		
		private String getRequirementValue(String pattern, Elements requirements) {
			
			for (Element requirement : requirements) {
				
				String requirementText = requirement.text();
				
				Pattern p = Pattern.compile(pattern);
				Matcher m = p.matcher(requirementText);
				
				if(m.matches()) {
					String requiredValueGroup = m.group(2);
					requiredValueGroup = requiredValueGroup.replaceAll("\\.", "");
					
					return requiredValueGroup;
					
				}
			}
			
			return null;
		}

		private boolean isNondurable(Element weaponLayout) {
			boolean isNondurable = false;
			Elements spans = weaponLayout.getElementsByTag("span");
			
			for (Element span : spans) {
				String title = span.attr("title");
				
				if(title.contains("magische Item") && "(M)".equals(span.text())) {
					isNondurable = true;
					break;
				}
			}
			return isNondurable;
		}
		
		private short getMinimumStrength(Element weaponLayout) {
			
			
			
			return 0;
		}
		
	}
	
	protected List<String> getItemsOfCategory(String baseUrl) throws IOException {
		
		List<String> sites = new LinkedList<String>();
		sites.add("/index.php/Doppelaxt");
		
		return sites;
		
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
