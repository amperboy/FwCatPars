package de.higger.fwwikiparser.parser;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.higger.fwwikiparser.enums.RaceType;
import de.higger.fwwikiparser.vo.BaseArmVO;

public abstract class BaseArmsParser<E extends BaseArmVO> extends BaseSingleSiteParser<E> {
	
	
	@Override
	public List<E> parseSite(String baseUrl) throws IOException {
		String site = getSiteToParse();
		
		List<E> items = new LinkedList<E>();
		
		Document doc = getDocument(baseUrl + site);
		Elements tables = doc.select("div#mw-content-text table.prettytable tbody");
		
		Elements itemElements = tables.get(0).select("tr");
		
		for (int i = 1; i < itemElements.size(); i++) {
			E item = parseItem(itemElements.get(i));
			items.add(item);
		}
		
		return items;
	}
	
	public abstract String getSiteToParse();

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected E parseItem(Element element) {
		
		Elements cells = element.getElementsByTag("td");
		cells.select("span").remove();
		
		Element itemLink = cells.get(0).select("a").first();
		
		String name = itemLink.text();
		String strength = cells.get(1).text();
		String requiredPower = cells.get(2).text();
		String requiredIntelligence = cells.get(3).text();
		String requiredCourses = cells.get(4).text();
		String requiredRace = cells.get(5).text();
		String nondurable = cells.get(7).text();
		
		
		strength = getUpperValue(strength);
		requiredCourses = getLowerValue(requiredCourses);
		requiredIntelligence = getLowerValue(requiredIntelligence);
		requiredPower = getLowerValue(requiredPower);
		
		boolean isNondurable = true;
		if (nondurable.trim().equals("")) {
			isNondurable = false;
		}
		
		E parsedItem = null;
		try {
			parsedItem = (E) ((Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
			parsedItem.setName(name);
			parsedItem.setUrl(itemLink.attr("href"));
			parsedItem.setStrength(Short.valueOf(strength));
			
			if (requiredCourses != null && !"—".equals(requiredCourses)) {
				try {
					parsedItem.setRequiredCourses(Integer.parseInt(requiredCourses));
				}
				catch (Exception e) {}
			}
			if (requiredIntelligence != null && !"—".equals(requiredIntelligence)) {
				try {
					parsedItem.setRequiredIntelligence(Integer.parseInt(requiredIntelligence));
				}
				catch (Exception e) {}
			}
			if (requiredPower != null && !"—".equals(requiredPower)) {
				try {
					parsedItem.setRequiredPower(Integer.parseInt(requiredPower));
				}
				catch (Exception e) {}
			}
			
			parsedItem.setNondurable(isNondurable);
			
			if("alle Rassen".equals(requiredRace) == false) {
				parsedItem.setRequiredRace(RaceType.getTypeByName(requiredRace));
			}
		} catch (Exception e) {
			
		} 
		
		
		return parsedItem;
	}
	
}
