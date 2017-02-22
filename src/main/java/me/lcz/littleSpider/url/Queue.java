package me.lcz.littleSpider.url;

import java.util.LinkedList;

/**
 * Created by lcz on 2016/10/25.
 */
public class Queue {
    private LinkedList<String> queue = new LinkedList<String>();
    public void inQueue(String t){
        queue.addLast(t);
    }
    public String outQueue(){
        return queue.removeFirst();
    }
    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }
    public boolean isContain(String t){
        return queue.contains(t);
    }
    public LinkedList<String> getQueue() {
        return queue;
    }
}
