package me.lcz.littleSpider;

/**
 * Created by lcz on 2016/12/15.
 */
public class Test5 implements Runnable{
    static int  i=0;
    static int k=0;
    public  void run() {

synchronized (String.class) {
    ++i;
}




    }


    public static void main(String[] args) throws Exception{

         for(int j=0;j<10000;j++){
             Test5 test5 = new Test5();
             new Thread(test5).start();
         }

//        Thread.currentThread().sleep(5000);
//        System.out.println( Thread.activeCount());
//        Thread.currentThread().sleep(5000);
        Thread.currentThread().sleep(1000);
        System.out.println(i);



    }

}
