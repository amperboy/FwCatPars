package de.higger.fwwikiparser.parser.category;

public class Link implements Comparable<Link> {
	private String name;
	private String path;

	public Link(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int compareTo(Link o) {
		
		return this.path.compareTo(o.path);
	}

}
