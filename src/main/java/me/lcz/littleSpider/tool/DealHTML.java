package me.lcz.littleSpider.tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by lcz on 2016/11/14.
 */
public class DealHTML {

    public String[] dealHTML(String URL) throws IOException{
         String[] data = new String[3];
        Document document = Jsoup.connect(URL).timeout(5000).get();
        //System.out.println(Jsoup.connect(URL).get().toString());
        //获取歌名
        Elements elements1 = document.select("a[data-res-name]");
        //获取歌手名字
        Elements elements2 = document.select("a[data-res-author]");
        //获取专辑名字
        Elements elements3 = document.select("a[href~=/album\\?id=\\d{0,10}]");
        data[0]=elements1.first().attr("data-res-name");
        data[1]=elements2.first().attr("data-res-author");
        data[2]=elements3.first().text();
        return  data;

    }




}
