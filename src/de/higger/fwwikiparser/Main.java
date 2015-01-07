package de.higger.fwwikiparser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.parser.CharaParser;
import de.higger.fwwikiparser.parser.DefenceArmsParser;
import de.higger.fwwikiparser.parser.NPCCategoryParser;
import de.higger.fwwikiparser.parser.OffenceArmsParser;
import de.higger.fwwikiparser.vo.BaseParseItem;

public class Main {
	
	
	public static void main(String[] args) throws IOException {
		
		PropertieHelper props = PropertieHelper.getInstance();
		String baseUrl = props.getPropertie("wiki.url.base");

		CharaParser charaParser = new CharaParser();
		OffenceArmsParser offenceArmsParser = new OffenceArmsParser();
		DefenceArmsParser defenceArmsParser = new DefenceArmsParser();
		NPCCategoryParser npcCategoryParser = new NPCCategoryParser();
		
		List<BaseParseItem> itemsToExport = new LinkedList<BaseParseItem>();
		
		if(Boolean.valueOf(props.getPropertie("conf.update.npc","false")).booleanValue()) {
			itemsToExport.addAll(npcCategoryParser.parseCategorie(baseUrl));
		}

		if(Boolean.valueOf(props.getPropertie("conf.update.chara","false")).booleanValue()) {
			itemsToExport.addAll(charaParser.parseSite(baseUrl));
		}
		
		if(Boolean.valueOf(props.getPropertie("conf.update.offence","false")).booleanValue()) {
			itemsToExport.addAll(offenceArmsParser.parseSite(baseUrl));
		}
		
		if(Boolean.valueOf(props.getPropertie("conf.update.defence","false")).booleanValue()) {
			itemsToExport.addAll(defenceArmsParser.parseSite(baseUrl));
		}
		
		if(itemsToExport.size() > 0) {
			String resultFileName = props.getPropertie("conf.result.file","export.sql");
			FileOutputStream fos = new FileOutputStream(resultFileName);
			PrintStream out = new PrintStream(fos);
			try {
				
				for (BaseParseItem parsedItem : itemsToExport) {
					if(parsedItem.isExportable())
						out.println("   CALL "+parsedItem.exportSQL());
				}
			}
			finally {
				out.close();
				fos.close();
			}
		}

		
		
		
		
		
	}

}
