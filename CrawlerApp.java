public class CrawlerApp{
    public static void main(String[] args){
        Crawler crawler = new Crawler();
		long startTime = System.nanoTime();
    	crawler.search("https://en.wikipedia.org/wiki/Block_(data_storage)");
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000; 
        System.out.println("PAGES: " + crawler.getPages().size() + " IN: " + duration + "ms");
    }
}