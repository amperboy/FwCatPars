package de.higger.fwwikiparser.parser.category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.parser.BaseParser;
import de.higger.fwwikiparser.vo.BaseParseItem;

public abstract class BaseCategoryParser<E extends BaseParseItem> extends
		BaseParser {

	public abstract class ParseSiteJob extends Thread {

		private final List<Link> sitesToParse;
		private List<E> parsedObjects;

		public ParseSiteJob(List<Link> sitesToParse) {
			this.sitesToParse = sitesToParse;
		}

		@Override
		public void run() {

			List<E> parsedObjects = new LinkedList<E>();

			for (Link siteToParse : sitesToParse) {

				E parsedItem;
				try {

					parsedItem = parseSite(siteToParse);
					parsedObjects.add(parsedItem);

				} catch (IOException e) {
					System.err.println("Failed to parse site: " + siteToParse.getPath() +" cause='"+e.getMessage()+"' "+getClass());
				}
			}

			this.parsedObjects = parsedObjects;

		}

		public abstract E parseSite(Link siteToParse) throws IOException;

		public Collection<? extends E> getParsedObjects() {
			return parsedObjects;
		}

	}

	public List<E> parseCategory(String baseUrl) throws IOException {

		List<Link> links = getItemsOfCategory(baseUrl);

		List<E> parsedItems = parseSiteInThreads(baseUrl, links);

		return parsedItems;
	}

	protected List<Link> getItemsOfCategory(String baseUrl) throws IOException {

		List<Link> scannedLinks = new LinkedList<Link>();

		scanLinksRecursive(baseUrl, getCategoryStart(), scannedLinks);
		
		Collections.sort(scannedLinks);
		applyFilter(scannedLinks);

		if(scannedLinks.size() == 0) {
			System.err.println("No parsable links found in class: "+this.getClass().getSimpleName());
		}
		
		return scannedLinks;

	}

	protected abstract String getCategoryStart();

	private void scanLinksRecursive(String baseUrl, String pageLinkRelative,
			List<Link> scannedLinks) throws IOException {
		Document doc = getDocument(baseUrl + pageLinkRelative);
		Element pagesElement = doc.select("div#mw-pages").first();

		Elements links = pagesElement.select("ul li a");
		Elements navigationLinks = pagesElement.select("a");
		navigationLinks.removeAll(links);

		for (Element element : links) {
			String linkToParseHref = element.attr("href");
			String linkName = element.text();
			Link linkToParse = new Link(linkName, linkToParseHref);
			scannedLinks.add(linkToParse);
		}

		for (Element element : navigationLinks) {
			if (element.text().contains("nächste")) {
				String nextPage = element.attr("href");
				scanLinksRecursive(baseUrl, nextPage, scannedLinks);
				break;
			}
		}
	}

	private void applyFilter(List<Link> scannedLinks) {
		List<String> ignoredSite = new ArrayList<String>(
				Arrays.asList(getIgnoredSubSites()));
		
		List<Link> linksToIgnore = new LinkedList<Link>();

		for (Link link : scannedLinks) {
			if (ignoredSite.contains(link.getPath())) {
				linksToIgnore.add(link);
			}
		}
		
		scannedLinks.removeAll(linksToIgnore);

	}

	protected abstract String[] getIgnoredSubSites();

	private List<E> parseSiteInThreads(String baseUrl, List<Link> scannedLinks) {

		long start = System.currentTimeMillis();

		short threads = Short.valueOf(PropertieHelper
				.getPropertieHelperInstance().getProperty("run.threads", "1"));

		List<ParseSiteJob> parsingJobs = new LinkedList<ParseSiteJob>();

		for (int threadId = 0; threadId < threads; threadId++) {

			List<Link> allocatedSiteList = new LinkedList<Link>();

			for (int siteId = 0; siteId < scannedLinks.size(); siteId++) {

				if (siteId % threads == threadId) {
					allocatedSiteList.add(scannedLinks.get(siteId));

					if (allocatedSiteList.size() > 0) {
						// break;
					}
				}
			}

			parsingJobs.add(createParseSiteJob(allocatedSiteList));
		}

		for (ParseSiteJob thread : parsingJobs) {
			thread.start();
		}

		List<E> items = new LinkedList<E>();

		for (ParseSiteJob thread : parsingJobs) {

			try {

				thread.join();
				items.addAll(thread.getParsedObjects());

			} catch (InterruptedException e) {

				System.err.println("thread sync. failed");
			}
		}

		System.out.println(System.currentTimeMillis() - start);

		return items;
	}

	protected abstract ParseSiteJob createParseSiteJob(
			List<Link> allocatedSiteList);
}
