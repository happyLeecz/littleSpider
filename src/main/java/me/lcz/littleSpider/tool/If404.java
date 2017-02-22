package me.lcz.littleSpider.tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by lcz on 2016/11/19.
 */
public class If404 {

    public static boolean if404(String URL) throws IOException{
        Document document = Jsoup.connect(URL).get();
        Elements elements = document.select("div[data-module]");
        for(Element element : elements){
            //System.out.println(element.attr("data-module"));
            if (element.attr("data-module").equals("404"))
                return true;
        }
        return false;

    }
}
