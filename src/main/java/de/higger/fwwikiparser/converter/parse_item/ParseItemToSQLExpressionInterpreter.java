package de.higger.fwwikiparser.converter.parse_item;

import java.util.HashMap;
import java.util.Map;

import de.higger.fwwikiparser.converter.Interpreter;
import de.higger.fwwikiparser.converter.parse_item.sql_expression.AbilityToSQLExpression;
import de.higger.fwwikiparser.converter.parse_item.sql_expression.DefenceArmTOSQLExpression;
import de.higger.fwwikiparser.converter.parse_item.sql_expression.NPCToSQLExpression;
import de.higger.fwwikiparser.converter.parse_item.sql_expression.OffenceArmTOSQLExpression;
import de.higger.fwwikiparser.vo.AbilityVO;
import de.higger.fwwikiparser.vo.BaseParseItem;
import de.higger.fwwikiparser.vo.DefenceArmVO;
import de.higger.fwwikiparser.vo.NPCVO;
import de.higger.fwwikiparser.vo.OffenceArmVO;

public class ParseItemToSQLExpressionInterpreter implements Interpreter {
	
	final Map<Class<? extends BaseParseItem>, BaseParseItemExpression<?,?>> expressionMap;
	
	private final static ParseItemToSQLExpressionInterpreter instance = new ParseItemToSQLExpressionInterpreter();
	
	private ParseItemToSQLExpressionInterpreter()  {
		
		Map<Class<? extends BaseParseItem>, BaseParseItemExpression<?,?>> expressionMap = new HashMap<Class<? extends BaseParseItem>, BaseParseItemExpression<?,?>>();
		
		expressionMap.put(NPCVO.class, new NPCToSQLExpression());
		expressionMap.put(AbilityVO.class, new AbilityToSQLExpression());
		expressionMap.put(DefenceArmVO.class, new DefenceArmTOSQLExpression());
		expressionMap.put(OffenceArmVO.class, new OffenceArmTOSQLExpression());
				
		this.expressionMap = expressionMap;
		
	}
	
	public static ParseItemToSQLExpressionInterpreter getInstance() {
		return instance;
	}
	
	public <I extends BaseParseItem, O> O interpretBaseItem(I in) {
		
		if(in == null)
			return null;
		
		@SuppressWarnings("unchecked")
		BaseParseItemExpression<I, O> objectInterpreter = (BaseParseItemExpression<I, O>) expressionMap.get(in.getClass());
		
		if(objectInterpreter == null) {
			throw new RuntimeException("Class '"+in.getClass()+"' is not supported to convert to SQLExpression");
		}
		
		
		return objectInterpreter.convert(in);	
	}
	
	public <I, O> O interpret(I in) {
		
		return interpretBaseItem((BaseParseItem) in);
	}
}
