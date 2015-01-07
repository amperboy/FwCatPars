package de.higger.fwwikiparser.enums;

public enum NPCType implements IType {
	
	NORMAL((byte) 1, "NPC"),
	UNIQUE((byte) 2, "Unique-NPC"),
	GROUP((byte) 3, "Gruppen-NPC"),
	RESITENENCE((byte) 4, "Resistenz-NPC"),
	SUPER_RESITENENCE((byte) 5, "Superresistenz-NPC");
	
	private byte id;
	private String name;
	
	private NPCType(byte id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public byte getId() {
		return id;
	}
	
	public String getTypeName() {
		return name;
	}
	
	public static NPCType getTypeByName(String nameToFind) {
		NPCType foundType = null;
		
		if (null != nameToFind && !nameToFind.trim().equals("")) {
			
			NPCType[] types = NPCType.values();
			for (NPCType aType : types) {
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
