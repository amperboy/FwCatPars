package de.higger.fwwikiparser.parser.category;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.higger.fwwikiparser.enums.RaceType;
import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.BaseArmVO;

public abstract class BaseWeaponCategoryParser<E extends BaseArmVO> extends BaseCategoryParser<E> {

	public abstract class BaseWeaponParseJob extends BaseCategoryParser<E>.ParseSiteJob {

		private static final String REQUIRED_POWER_PATTERN = "(Mindeststärke: )([0-9]+[.0-9]*)";
		private static final String REQUIRED_COURSES_PATTERN = "(Mindestakademielimit: )([0-9]+[.0-9]*)";
		private static final String REQUIRED_INTELLIGENCE_PATTERN = "(Mindestintelligenz: )([0-9]+[.0-9]*)";
		private static final String REQUIRED_RACE_PATTERN = "(Rasse: )(.*)";
		private static final String STRENGTH_PATTERN = "(.*Stärke:[^0-9]*)([0-9]+[.0-9]*)(.*)";

		public BaseWeaponParseJob(List<String> sitesToParse) {
			super(sitesToParse);
		}

		protected abstract E createObject();	
		
		@Override
		public E parseSite(String siteToParse) throws IOException {

			E baseWeaponVO = createObject();
			baseWeaponVO.setUrl(siteToParse);
			
			String baseUrl = PropertieHelper.getPropertieHelperInstance().getBaseURL();
			Document doc = getDocument(baseUrl+siteToParse);
			
			Element weaponLayout = doc.select("div#mw-content-text").first();
			
			try {
				
				
				String weaponName = doc.getElementById("firstHeading").text();
				baseWeaponVO.setName(weaponName);
				
				boolean isNondurable = isNondurable(weaponLayout);				
				baseWeaponVO.setNondurable(isNondurable);
				
				Elements requirements = weaponLayout.select(".layout_requirements").first().getElementsByTag("li");
				
				if(requirements.size() > 0) {
				
					int requiredPower = getRequiredPower(requirements);
					baseWeaponVO.setRequiredPower(requiredPower);
					
					int requiredCourses = getRequiredCourses(requirements);
					baseWeaponVO.setRequiredCourses(requiredCourses);

					int requiredIntelligence = getRequiredIntelligence(requirements);
					baseWeaponVO.setRequiredIntelligence(requiredIntelligence);
					
					RaceType requiredRace = getRequiredRace(requirements);
					baseWeaponVO.setRequiredRace(requiredRace);
					
				}
				
				short minimumStrength = getMinimumStrength(weaponLayout);
				baseWeaponVO.setStrength(minimumStrength);
				
				
			} catch (Exception e) {
				
				baseWeaponVO.setExportable(false);
			}
			
			if(baseWeaponVO.getStrength() == 0) {
				System.err.println(baseWeaponVO);
			}
			
			return baseWeaponVO;
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
			
			short minimumStrength = 0;
			
			Elements layouts = weaponLayout.select("div.layout_desc");
			
			outer:
			for (Element layout : layouts) {
				
				Elements cursiveTexts = layout.getElementsByTag("i");
				
				for (Element element : cursiveTexts) {
					
					String content = element.text();
					
					Pattern p = Pattern.compile(STRENGTH_PATTERN);
					Matcher m = p.matcher(content);
					
					if (m.matches()) {
						
						String requiredValueGroup = m.group(2);
						
						requiredValueGroup = requiredValueGroup.replaceAll("\\.", "");
						
						minimumStrength = Short.valueOf(requiredValueGroup);
						
						
						break outer;
					}
				}
			}
			
			return minimumStrength;
		}
		
	}

}
