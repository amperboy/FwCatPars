package de.higger.fwwikiparser;

import static de.higger.fwwikiparser.helper.PropertieHelper.getPropertieHelperInstance;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import de.higger.fwwikiparser.converter.parse_item.ParseItemToSQLExpressionInterpreter;
import de.higger.fwwikiparser.parser.category.BaseCategoryParser;
import de.higger.fwwikiparser.parser.category.OffenceCategoryParser;
import de.higger.fwwikiparser.vo.BaseParseItem;

public class Main {

	public static void main(String[] args) throws IOException {
		
//		parseItems(new NPCCategoryParser());
//		parseItems(new AbilityCategoryParser());
		parseItems(new OffenceCategoryParser());
		
		
		List<String> sites = new LinkedList<String>();
		sites.add("/index.php/Doppelaxt");
		
		
	}

	private static <E extends BaseParseItem> void parseItems(BaseCategoryParser<E> categoryParser) throws IOException {
		
		List<E> parsedItems = categoryParser.parseCategory(getPropertieHelperInstance().getBaseURL());
		
		ParseItemToSQLExpressionInterpreter sqlInterpreter = ParseItemToSQLExpressionInterpreter.getInstance();
		
		for (E item : parsedItems) {
			System.out.println(sqlInterpreter.interpret(item));
		}
	}	

}
