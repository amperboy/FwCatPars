package de.higger.fwwikiparser.parser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.higger.fwwikiparser.enums.RaceType;
import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.CharaVO;

public class CharaParser extends BaseSingleSiteParser<CharaVO> {
	@Override
	public List<CharaVO> parseSite(String uri) throws IOException {
		String charasite = PropertieHelper.getInstance().getPropertie("wiki.parser.chara");
		
		List<CharaVO> charas = new LinkedList<CharaVO>();
		
		Document doc = getDocument(uri + charasite);
		Elements tables = doc.select("#mw-content-text table.prettytable tbody");
		
		Elements charaElements = tables.get(1).select("tr");
		
		for (int i = 1; i < charaElements.size(); i++) {
			CharaVO chara = parseItem(charaElements.get(i));
			charas.add(chara);
		}
		
		return charas;
	}

	
	protected CharaVO parseItem(Element element) {
		
		Elements cells = element.getElementsByTag("td");
		Element charaLink = cells.get(0).select("a").first();
		
		String name = charaLink.html();
		String uri = charaLink.attr("href");
		String baseTime = cells.get(3).html();
		String maxLevel = cells.get(6).html();
		String raceBonus = cells.get(7).select("a").html();
		
		CharaVO charaVO = new CharaVO();
		charaVO.setName(name);
		charaVO.setUrl(uri);
		charaVO.setBaseTime(Integer.valueOf(baseTime));
		charaVO.setMaxLevel(Short.valueOf(maxLevel));
		charaVO.setRaceBonus(RaceType.getTypeByName(raceBonus));
		
		return charaVO;
	}
}
