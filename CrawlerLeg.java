import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerLeg implements Runnable{
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" + "|png|tiff?|mid|mp2|mp3|mp4"
                        + "|wav|avi|mov|mpeg|ram|m4v|pdf" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	private String  mUrl;
	private Crawler mCrawler;
	private Document mHTMLDocument;

	public CrawlerLeg(String url, Crawler crawler){
		this.mUrl = url;
		this.mCrawler = crawler;
	}

	public void run(){
		crawl();
		this.mCrawler.getLatch().countDown();
	}

	private void crawl(){
		try{
			Connection conn = Jsoup.connect(this.mUrl);
			Document doc = conn.get();
			this.mHTMLDocument = doc;
			Elements linksOnPage = mHTMLDocument.select("a[href^=/wiki/]");

			for(Element link : linksOnPage){
				String absUrl = link.absUrl("href");
				if(shouldVisit(absUrl) && !this.mUrl.equals(absUrl)){
					Page p = new Page(this.mUrl, absUrl);
					if(!this.mCrawler.getPages().contains(p)){
						p.setId(mCrawler.updatePos());
						this.mCrawler.getPages().add(p);
						this.mCrawler.getUrls().add(absUrl);	
					}
				}
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	private boolean shouldVisit(String url){
		String href = url.toLowerCase();
		int count = 0;
		for(int i = 0; i < href.length();i++)
			if(href.charAt(i) == ':')
				count++;
		if(count > 1)
			return false;
		return !FILTERS.matcher(href).matches() &&
			href.startsWith("https://en.wikipedia.org/wiki/") &&
			!href.equals("https://en.wikipedia.org/wiki/main_page"); 
	}
}