package application.testData.correctRandomAddresses;

public class WebCrawlerLoader {
    public static void main(String[] args) {
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.createANewSetForEachCountryFromToDoFile(5); // un set cu 5 * 20 adrese random
    }
}
