package de.higger.fwwikiparser.converter.parse_item.sql_expression;

import de.higger.fwwikiparser.converter.parse_item.BaseParseItemExpression;
import de.higger.fwwikiparser.enums.NPCType;
import de.higger.fwwikiparser.vo.NPCVO;

public class NPCToSQLExpression implements BaseParseItemExpression<NPCVO, String> {

	public String convert(NPCVO npc) {
		
		NPCType npcType = npc.getNpcType();
		
		byte npcTypeId = npcType == null ? (byte) 0 : npcType.getId();
		
		StringBuilder sql = new StringBuilder("   CALL AddNPC('");
		
		sql.append(npc.getName());
		sql.append("','");
		sql.append(npc.getUrl());
		sql.append("','");
		sql.append(npcTypeId);
		sql.append("','");
		sql.append(npc.getMinStrength());
		sql.append("','");
		sql.append(npc.getMaxStrength());
		sql.append("','");
		sql.append(npc.getMinHealth());
		sql.append("','");
		sql.append(npc.getMaxHealth());
		sql.append("','");
		sql.append(npc.getDroppedMoney());
		sql.append("');");

		return sql.toString();
	}

}
