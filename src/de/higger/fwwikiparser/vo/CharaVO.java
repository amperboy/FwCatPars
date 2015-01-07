package de.higger.fwwikiparser.vo;

import de.higger.fwwikiparser.enums.RaceType;

public class CharaVO extends BaseParseItem {

	private Integer baseTime;
	private Short maxLevel;
	private RaceType raceBonus;

	public void setBaseTime(Integer baseTime) {
		this.baseTime = baseTime;
	}

	public void setMaxLevel(Short maxLevel) {
		this.maxLevel = maxLevel;
	}

	public void setRaceBonus(RaceType raceBonus) {
		this.raceBonus = raceBonus;
	}
	
	
	public Integer getBaseTime() {
		return baseTime;
	}
	
	public Short getMaxLevel() {
		return maxLevel;
	}
	
	public RaceType getRaceBonus() {
		return raceBonus;
	}

	@Override
	public String toString() {
		return "CharaVO [baseTime=" + baseTime + ", maxLevel=" + maxLevel
				+ ", raceBonus=" + raceBonus + ", getName()=" + getName()
				+ ", getUrl()=" + getUrl() + "]";
	}

	@Override
	public String exportSQL() {
//		short raceId = raceBonus == null ? (short) 0 : raceBonus.getId();
		return "AddChara('"+getName()+"','"+getUrl()+"','"+maxLevel+"','"+baseTime+"');";
	}
	
}
