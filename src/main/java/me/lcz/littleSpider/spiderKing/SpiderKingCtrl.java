package me.lcz.littleSpider.spiderKing;

import me.lcz.littleSpider.downLoad.DownLoad;

/**
 * Created by lcz on 2016/11/8.
 */
public class SpiderKingCtrl implements Runnable {
    String startURL = null;
    DownLoad downLoad = null;

    public SpiderKingCtrl(String startURL) {

        this.startURL = startURL;
        downLoad = new DownLoad(startURL);
    }

    public void run() {
//            DownLoad downLoad = new DownLoad(startURL);
//            downLoad.startTask();
        downLoad.startTask();
    }

    public static void main(String[] args) throws Exception {
//        SpiderKingCtrl t = new SpiderKingCtrl("http://music.163.com/discover/artist");
        SpiderKingCtrl t = new SpiderKingCtrl("http://music.163.com/discover/playlist");
        for (int i = 0; i < 100; i++) {
            new Thread(t).start();
        }
    }
}
