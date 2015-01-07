package de.higger.fwwikiparser.parser;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.higger.fwwikiparser.enums.NPCType;
import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.NPCVO;

public class NPCCategoryParser extends BaseCategoryParser<NPCVO> {
	
	public static class NPCSiteParser extends BaseSingleItemParser<NPCVO> {

		private String baseUrl;
		
		private Pattern intervalPattern;
		
		public NPCSiteParser(String baseUrl) {
			this.baseUrl = baseUrl;
			this.intervalPattern = Pattern.compile("([^0-9]*)([\\.0-9]+[ -]*[\\.0-9]*)([^0-9]*)");
		}

		@Override
		public NPCVO parseSite(String siteToParse) throws IOException {
			NPCVO npcvo = new NPCVO();
			
			Document doc = getDocument(baseUrl+siteToParse);
			Element npcLayout = doc.select("div#mw-content-text").first();
			
			Element npcDesc = npcLayout.select("div.layout_desc").first();
			if(npcDesc != null) {
				
				Elements details = npcDesc.select("p");
				
				Element nameElement = details.first();
				String name = nameElement.select("b").text();
				String type = nameElement.select("a").text();
				
				Element strengthDetails = details.last();
				Matcher strengthMatcher = intervalPattern.matcher(strengthDetails.html());
				
				if(strengthMatcher.find()) {
					String minStrength = getLowerValue(strengthMatcher.group(2));
					String maxStrength = getUpperValue(strengthMatcher.group(2));
					
					if (minStrength != null && !minStrength.equals("")) {
						npcvo.setMinStrength(Integer.parseInt(minStrength));
					}
					
					if (maxStrength != null && !maxStrength.equals("")) {
						npcvo.setMaxStrength(Integer.parseInt(maxStrength));
					}
				}
				else {
					npcvo.setMinStrength(0);
					npcvo.setMaxStrength(999999);
					npcvo.setExportable(false);
				}
				
				Element moneyDesc = npcLayout.select("div.layout_money p").first();
				if(moneyDesc != null && moneyDesc.textNodes().size() > 0) {
					String money = moneyDesc.textNodes().get(0).text();
					Matcher moneyMatcher = intervalPattern.matcher(money);
					
					if(moneyMatcher.find()) {
						String moneyVal = getLowerValue(moneyMatcher.group(2));
						
						npcvo.setDroppedMoney(Short.parseShort(moneyVal));
					}
					else {
						npcvo.setDroppedMoney((short) 0);
					}
				}
				
				Element lpDesc = npcLayout.select("div.layout_lp p").first();
				if(lpDesc.textNodes().size() > 0) {
					String health = lpDesc.textNodes().get(0).text();
					Matcher healthMatcher = intervalPattern.matcher(health);
					
					if(healthMatcher.find()) {
						String minHealth = getLowerValue(healthMatcher.group(2));
						String maxHealth = getUpperValue(healthMatcher.group(2));
						
						npcvo.setMinHealth(Integer.parseInt(minHealth));
						npcvo.setMaxHealth(Integer.parseInt(maxHealth));
					}
					else {
						npcvo.setMinHealth(0);
						npcvo.setMaxHealth(999999);
						npcvo.setExportable(false);
					}
				}
				
				
				npcvo.setUrl(siteToParse);
				npcvo.setName(name);
				npcvo.setNpcType(NPCType.getTypeByName(type));
			}
			else {
				npcvo.setExportable(false);
			}

			return npcvo;
		}
		
	}

	@Override
	public List<NPCVO> parseCategorie(String baseUrl) throws IOException {

		resetSites();
		String categoryStart = PropertieHelper.getInstance().getPropertie("wiki.parser.category.npcs");
		
		parsePageOfCat(baseUrl, categoryStart);	
		
		cleanSites();
		
		List<NPCVO> npcs= parseItems(baseUrl, new NPCSiteParser(baseUrl));

		return npcs;
	}

	private void parsePageOfCat(String baseUrl, String pageLinkRelative)
			throws IOException {
		Document doc = getDocument(baseUrl+pageLinkRelative);
		Element pagesElement = doc.select("div#mw-pages").first();
		
		Elements links = pagesElement.select("tbody a");
		Elements navigationLinks = pagesElement.select("a");
		navigationLinks.removeAll(links);

		for (Element element : links) {
			String linkToParse = element.attr("href");
			addSite(linkToParse);
		}	
		
		for (Element element : navigationLinks) {
			if(element.text().contains("nächste")) {
				String nextPage = element.attr("href");
				parsePageOfCat(baseUrl, nextPage);
				break;
			}
		}
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
			"/index.php/Diener_von_Beispieluser",
				
		};
		return exclustions;
	}

}
