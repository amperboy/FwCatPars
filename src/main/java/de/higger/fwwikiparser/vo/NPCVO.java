package de.higger.fwwikiparser.vo;

import de.higger.fwwikiparser.enums.NPCType;

public class NPCVO extends BaseParseItem {
	
	private NPCType npcType;
	
	private int minStrength;
	private int maxStrength;

	private int minHealth;
	private int maxHealth;
	
	private short droppedMoney;

	public NPCType getNpcType() {
		return npcType;
	}

	public void setNpcType(NPCType npcType) {
		this.npcType = npcType;
	}

	public short getDroppedMoney() {
		return droppedMoney;
	}

	public void setDroppedMoney(short droppedMoney) {
		this.droppedMoney = droppedMoney;
	}

	public int getMinHealth() {
		return minHealth;
	}

	public void setMinHealth(int minHealth) {
		this.minHealth = minHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getMinStrength() {
		return minStrength;
	}

	public void setMinStrength(int minStrength) {
		this.minStrength = minStrength;
	}

	public int getMaxStrength() {
		return maxStrength;
	}

	public void setMaxStrength(int maxStrength) {
		this.maxStrength = maxStrength;
	}


	@Override
	public String toString() {
		return "NPCVO [npcType=" + npcType + ", droppedMoney=" + droppedMoney
				+ ", minHealth=" + minHealth + ", maxHealth=" + maxHealth
				+ ", minStrength=" + minStrength + ", maxStrength="
				+ maxStrength + ", getName()=" + getName() + ", getUrl()="
				+ getUrl() + "]";
	}

}
