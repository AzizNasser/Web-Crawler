import java.io.IOException;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.*;
import org.jsoup.Jsoup;
import org.jsoup.Connection;

public class Crawler {
  public static void main(String[] args) {
    String url = "https://ccis.ksu.edu.sa/en";
    Pattern p= Pattern.compile(url.substring(0, 11));
    
    
    crawl(p, url, new ArrayList < String > (),new ArrayList < String > ());
  }
  private static void crawl(Pattern p, String url, ArrayList < String > visited, ArrayList < String > email) {
   
	  Matcher m=p.matcher(url);
	  //to search in website that start with  "https://ccis"
	  if (m.lookingAt()&&url.length()<200) {
    		
      Document doc = request(url, visited);

      if (doc != null) {
        for (Element link: doc.select("a[href]")) {
          String nextLink = link.absUrl("href");
          
      
       
       
          Pattern pattern = Pattern.compile("(\\w)*@ksu.edu.sa");
         
          
          //matching the links
          Matcher matcher = pattern.matcher(nextLink);
            if(matcher.find())
            	if(!email.contains(matcher.group())) {
            		email.add(matcher.group());
          	 System.out.println(matcher.group());}
            
            
            
            
            //matching the texts
             matcher = pattern.matcher(doc.text());
            if(matcher.find())
            	if(!email.contains(matcher.group())) {
            		email.add(matcher.group());
          	 System.out.println(matcher.group());}
            
            
            
            
          
          
      
          if (visited.contains(nextLink) == false)
            crawl(p, nextLink, visited,email);

        }
      }
    }
  }

  private static Document request(String url, ArrayList < String > v) {
    try {
      Connection con = Jsoup.connect(url);
      Document doc = con.get();

      if (con.response().statusCode() == 200) {
       
        v.add(url);
        return doc;
      }
      return null;
    } catch (IOException e) {
      return null;
    }
  }
}
