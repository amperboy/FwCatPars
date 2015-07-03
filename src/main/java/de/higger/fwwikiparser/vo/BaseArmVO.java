package de.higger.fwwikiparser.vo;

import de.higger.fwwikiparser.enums.RaceType;

public abstract class BaseArmVO extends BaseParseItem {
	
	private short strength;
	private boolean isNondurable;
	private int requiredPower;
	private int requiredIntelligence;
	private int requiredCourses;
	private RaceType requiredRace;

	public short getStrength() {
		return strength;
	}

	public void setStrength(short strength) {
		this.strength = strength;
	}

	public boolean isNondurable() {
		return isNondurable;
	}

	public void setNondurable(boolean isNondurable) {
		this.isNondurable = isNondurable;
	}

	public int getRequiredPower() {
		return requiredPower;
	}

	public void setRequiredPower(int requiredPower) {
		this.requiredPower = requiredPower;
	}

	public int getRequiredIntelligence() {
		return requiredIntelligence;
	}

	public void setRequiredIntelligence(int requiredIntelligence) {
		this.requiredIntelligence = requiredIntelligence;
	}

	public int getRequiredCourses() {
		return requiredCourses;
	}

	public void setRequiredCourses(int requiredCourses) {
		this.requiredCourses = requiredCourses;
	}

	public RaceType getRequiredRace() {
		return requiredRace;
	}

	public void setRequiredRace(RaceType requiredRace) {
		this.requiredRace = requiredRace;
	}

	@Override
	public String toString() {
		return "ArmVO [strength=" + strength + ", isNondurable=" + isNondurable
				+ ", requiredPower=" + requiredPower
				+ ", requiredIntelligence=" + requiredIntelligence
				+ ", requiredCourses=" + requiredCourses + ", requiredRace="
				+ requiredRace + ", getName()=" + getName() + ", getUrl()="
				+ getUrl() + "]";
	}

	
	

}
