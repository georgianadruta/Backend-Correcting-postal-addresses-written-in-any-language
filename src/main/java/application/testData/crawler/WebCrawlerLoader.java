package application.testData.crawler;

public class WebCrawlerLoader {
    public static void main(String[] args) {
        // trebuie sa reduc numele la ascii name in adresele corecte. si solutia sa fie tot n ascii name
        WebCrawler webCrawler = new WebCrawler();
        webCrawler.createANewSetForEachCountryFromToDoFile(1); // un set cu 5 * 20 adrese random
    }
}
