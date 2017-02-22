package me.lcz.littleSpider.url;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lcz on 2016/10/25.
 */
public class  LinkedQueue {
    private static Set<String> visitedUrl = new HashSet<String>();
    private static Queue unVisitedUrl = new Queue();
    public static Queue getUnVisitedUrl() {
        return unVisitedUrl;
    }
    public static synchronized void addVisitedUrl(String url){
        visitedUrl.add(url);
    }
    public static synchronized void removeVisitedUrl(String url){
        visitedUrl.remove(url);
    }
    //取出未访问的URL必须是和将URL放入访问队列一起而且加上synchronized关键字，在多线程中如果不一起的话有可能线程1刚取出A，运行权给了线程2，线程2取出B，在B页面又抓取到了
    //A要将A放入未访问队列中，这时由于线程1没有及时地讲A放入访问队列，所以就又将已经在处理的A又放入了未访问队列
    public static synchronized String unVisitedUrloutQueue(){
        String url = unVisitedUrl.outQueue();
        addVisitedUrl(url);
        return url;
    }
    public static synchronized void addUnVisitedUrl(String url){
        if(url != null && !url.trim().equals("")
                && !visitedUrl.contains(url)
                && !unVisitedUrl.isContain(url))
            unVisitedUrl.inQueue(url);

    }
    public static int getVisitedUrlNum(){
        return visitedUrl.size();
    }

    public static Set<String> getVisitedUrl() {
        return visitedUrl;
    }
   //加关键字是为了让在判断的时候不会改变未访问队列的状态，就是说别的线程不会
    // 调用unVisitedUrloutQueue()方法
/*
  未访问URL队列不空的话，未访问URL队列出队列并返回其值，否则返回null
 */
    public static synchronized String isUnVisitedUrlEmpty(){
       String url = null;
        if(!unVisitedUrl.isQueueEmpty())
            return url = unVisitedUrloutQueue();
        else
            return null;
    }
}
