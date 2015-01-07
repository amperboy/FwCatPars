package de.higger.fwwikiparser;

import java.io.IOException;
import java.util.List;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.parser.BaseCategoryParser;
import de.higger.fwwikiparser.parser.NPCCategoryParser;
import de.higger.fwwikiparser.parser.OffenceCategoryParser;

public class Main {
	public static void main(String[] args) throws IOException {
		PropertieHelper props = PropertieHelper.getInstance();
		String baseUrl = props.getPropertie("wiki.url.base");
		
		parseCategory(baseUrl, new NPCCategoryParser());
		parseCategory(baseUrl, new OffenceCategoryParser());
		
		
	}

	private static void parseCategory(String baseUrl,
			BaseCategoryParser npcCategoryParser) throws IOException {
		npcCategoryParser.parseCategory(baseUrl);
	}
}
