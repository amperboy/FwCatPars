package de.higger.fwwikiparser.enums;

public enum RaceType implements IType {
	
	ARBEITER((short) 1, "Mensch/Arbeiter"),
	KAEMPFER((short) 2, "Mensch/Kämpfer"),
	ZAUBERER((short) 3, "Mensch/Zauberer"),
	ONLO((short) 4, "Onlo"),
	NATLA((short) 5, "Natla-Händler"),
	DUNKLER_MAGIER((short) 6, "Dunkler Magier"),
	SERUM_GEIST((short) 7, "Serum-Geist"),
	TARUNER((short) 8, "Taruner")
	;
	
	private short id;
	private String name;
	
	private RaceType(short id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public short getId() {
		return id;
	}
	
	public String getTypeName() {
		return name;
	}
	
	public static RaceType getTypeByName(String nameToFind) {
		RaceType foundType = null;
		
		if (null != nameToFind && !nameToFind.trim().equals("")) {
			
			RaceType[] types = RaceType.values();
			for (RaceType aType : types) {
				String typeName = aType.getTypeName();
				
				if (typeName.toLowerCase().equals(nameToFind.toLowerCase())) {
					foundType = aType;
					break;
				}
				
			}
		}
		
		return foundType;
	}
	
}
