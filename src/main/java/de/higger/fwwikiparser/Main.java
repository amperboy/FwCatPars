package de.higger.fwwikiparser;

import static de.higger.fwwikiparser.helper.PropertieHelper.getPropertieHelperInstance;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import de.higger.fwwikiparser.converter.parse_item.ParseItemToSQLExpressionInterpreter;
import de.higger.fwwikiparser.parser.category.AbilityCategoryParser;
import de.higger.fwwikiparser.parser.category.BaseCategoryParser;
import de.higger.fwwikiparser.parser.category.DefenceCategoryParser;
import de.higger.fwwikiparser.parser.category.NPCCategoryParser;
import de.higger.fwwikiparser.parser.category.OffenceCategoryParser;
import de.higger.fwwikiparser.vo.BaseParseItem;

public class Main {

	public static void main(String[] args) throws IOException {
		
		FileWriter fileWriter = new FileWriter();
		
		parseItems(fileWriter.getPrintStream(), new NPCCategoryParser());
		parseItems(fileWriter.getPrintStream(), new AbilityCategoryParser());
		parseItems(fileWriter.getPrintStream(), new OffenceCategoryParser());
		parseItems(fileWriter.getPrintStream(), new DefenceCategoryParser());
		
		fileWriter.close();
		
	}

	private static <E extends BaseParseItem> void parseItems(PrintStream out, BaseCategoryParser<E> categoryParser) throws IOException {
		
		List<E> parsedItems = categoryParser.parseCategory(getPropertieHelperInstance().getBaseURL());
		
		ParseItemToSQLExpressionInterpreter sqlInterpreter = ParseItemToSQLExpressionInterpreter.getInstance();
		
		for (E item : parsedItems) {
			out.println(sqlInterpreter.interpret(item));
		}
	}	

}
