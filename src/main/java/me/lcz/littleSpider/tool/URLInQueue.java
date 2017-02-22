package me.lcz.littleSpider.tool;

import me.lcz.littleSpider.url.LinkedQueue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by lcz on 2016/11/14.
 */
public class URLInQueue {
   public static void urlInQueue(String url) throws IOException{
       Document document = Jsoup.connect(url).timeout(5000).get();
       //在歌单列表页下：a.msk歌单链接，a.zpgi索引链接，a.a2最新板块歌单集
       //在具体歌单页下：ul.f-hide a查找歌曲链接
       Elements elements = document.select(
               "a[href~=/discover/artist/cat\\?id=\\d+]," +
               "a[href~=/artist\\?id=\\d+]," +
                       "a[href~=/artist/album\\?id=\\d+],"+
                       "a[href~=/song\\?id=\\d+],"+
                        "a[href~=/album\\?id=\\d+],"+
       "a[href~=/playlist\\?id=\\d+]," +
       "a[href~=/discover/playlist/\\?order=.+]");

//               "a.msk," +
//               "a.zpgi," +
//               "a.a2," +
//               "ul.f-hide a," +
//               "a.cat-flag," +
//               "ul#initial-selector a,"+
//               "a.nm," +
//               "ul.nav f-cb a," +
//               "ul.m-tabs f-cb a," +
//               "a.tit f-thide s-fc0");
//               "a.zpgi"
//       Pattern pattern = Pattern.compile(
//               "http://music\\.163\\.com/playlist\\?id=.+|" +
//               "http://music\\.163\\.com/song\\?id=.+|" +
//               "http://music\\.163\\.com/discover/playlist/\\?order=.+|" +
//               "http://music\\.163\\.com/discover/artist/cat\\?id=.+|" +
//               "http://music\\.163\\.com/artist\\?id=.+|" +
//               "http://music\\.163\\.com/artist/album\\?id=.+|" +
//               "http://music\\.163\\.com/album\\?id=.+");
       for(Element element : elements){
           String newUrl = element.absUrl("href");
//             if(pattern.matcher(newUrl).find()) {
                 LinkedQueue.addUnVisitedUrl(newUrl);
               // System.out.println(newUrl);
//             }

             }
       }
   }

