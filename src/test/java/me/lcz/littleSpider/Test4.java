package me.lcz.littleSpider;

import me.lcz.littleSpider.downLoad.DownLoad;

/**
 * Created by lcz on 2016/11/25.
 */
public class Test4 {

    public static void main(String[] args) {
        DownLoad downLoad = new DownLoad("aaa");
        DownLoad downLoad1 = new DownLoad("bbb");
        System.out.println(downLoad.equals(downLoad1));

    }
}
