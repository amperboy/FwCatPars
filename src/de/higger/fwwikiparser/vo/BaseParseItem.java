package de.higger.fwwikiparser.vo;

public abstract class BaseParseItem {
	
	private String name;
	private String url;
	

	private boolean isExportable = true;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setExportable(boolean isExportable) {
		this.isExportable = isExportable;
	}
	
	public boolean isExportable() {
		return isExportable;
	}
	
	
	public abstract String exportSQL();

}
