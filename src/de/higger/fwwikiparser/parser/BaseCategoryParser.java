package de.higger.fwwikiparser.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import de.higger.fwwikiparser.helper.PropertieHelper;
import de.higger.fwwikiparser.vo.BaseParseItem;

public abstract class BaseCategoryParser<E extends BaseParseItem> extends BaseParser<E> {
	
	private class StartParseThreaded extends Thread {
		
		private List<String> sites;
		private BaseSingleItemParser<E> singleSiteParser;
		
		private List<E> items;
		
		public StartParseThreaded(BaseSingleItemParser<E> singleSiteParser, List<String> sites) {
			this.singleSiteParser = singleSiteParser;
			this.sites = sites;
			
		}
		
		public List<E> getItems() {
			return items;
		}
		
		@Override
		public void run() {
//			System.out.println(Thread.currentThread().getId() + " - Thread started");

			items = new LinkedList<E>();
			
			for (String itemSite : sites) {
				try {
					E obj = singleSiteParser.parseSite(itemSite);
					items.add(obj);
				} catch (IOException e) {
					System.err.println("parsing error: site="+itemSite);
				}
			}
			
//			System.out.println(Thread.currentThread().getId() + " - Thread finished");
		}
	}
	
	private List<String> sites = new LinkedList<String>();
	
	public abstract List<E> parseCategorie(String baseUrl) throws IOException;
	
	protected abstract String[] getIgnoredSubSites();
	
	protected void resetSites() {
		sites.clear();
	}
	
	protected void addSite(String url) {
		sites.add(url);
	}
	
	protected void cleanSites() {
		List<String> ignoredSite = new ArrayList<String>(Arrays.asList(getIgnoredSubSites()));
		List<String> resultList = new LinkedList<String>();
		
		for (String pageToParse : sites) {
			if(ignoredSite.contains(pageToParse) == false) {
				resultList.add(pageToParse);
			}
		}
		
		sites = resultList;
	}
	
	public List<E> debug(BaseSingleItemParser<E> singleSiteParser) {
		List<String> sites = new LinkedList<String>();
		sites.add("/index.php/Altstadtratte");
		sites.add("/index.php/Siramücken-Schwarm");
		sites.add("/index.php/Graustein-Bär");
		sites.add("/index.php/Enormer_Graustein-B%C3%A4r");
		sites.add("/index.php/Rindenhagel");
		sites.add("/index.php/Erfrorenes_Schaf");
		
		StartParseThreaded threaded = new StartParseThreaded(singleSiteParser, sites);
		threaded.start();
		
		try {
			threaded.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return threaded.getItems();
	}
	
	protected List<E> parseItems(String baseUrl, BaseSingleItemParser<E> singleSiteParser) {
//		long start = System.currentTimeMillis();
		
		List<E> items = new LinkedList<E>();
		
		short threads = Short.valueOf(PropertieHelper.getInstance().getPropertie("run.threads","1"));
		
		List<StartParseThreaded> parsingJobs = new LinkedList<StartParseThreaded>();
		
		for (int threadId = 0; threadId < threads; threadId++) {
			List<String> siteThreadList = new LinkedList<String>();
			
			for(int siteId = 0;siteId < sites.size();siteId++) {
				if (siteId%threads == threadId) {
					siteThreadList.add(sites.get(siteId));
//					break; //Test only
				}
			}
			
			parsingJobs.add(new StartParseThreaded(singleSiteParser, siteThreadList));
		}		
		
		for (StartParseThreaded startParseThreaded : parsingJobs) {
			startParseThreaded.start();
		}
		
		for (StartParseThreaded startParseThreaded : parsingJobs) {
			try {
				startParseThreaded.join();
				items.addAll(startParseThreaded.getItems());
			} catch (InterruptedException e) {
				System.err.println("thread sync. failed");
			}
		}
		
//		System.out.println(System.currentTimeMillis() - start);
		
		return items;
	}
}
