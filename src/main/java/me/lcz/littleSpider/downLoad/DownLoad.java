package me.lcz.littleSpider.downLoad;

import me.lcz.littleSpider.entity.Song;
import me.lcz.littleSpider.mybatisAbout.Start;
import me.lcz.littleSpider.tool.*;
import me.lcz.littleSpider.url.LinkedQueue;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lcz on 2016/11/8.
 */
public class DownLoad {
    // private static CloseableHttpClient httpClient = HttpClients.createDefault();
    //private final static String salt ="132esdsagdgs/f'/d'fdofoejrewjro";
    //访问网易云获取评论信息接口post方法后面带的参数
    private static final String[] paramsAndEncSeckey = DealParams.getParamsAndEncSeckeyForCommentAPI().split("   ");
    //当前爬到的歌曲数量
    //private static AtomicInteger count = new AtomicInteger(0);
    //正在工作的线程数量
    private static int WorkThreadCount = 100;
    private static final SqlSessionFactory sqlSessionFactory = new Start().getSqlSessionFactory();


    public DownLoad(String start) {
        LinkedQueue.addUnVisitedUrl(start);
    }

    //这里为什么不用synchronized关键字来修饰，因为如果用了之后，这个方法里面是一个循环，某一个线程进入了这个方法里
    //在循环没有结束之前，这个对象的锁会被这一个线程一直都占有，其他线程就一直在阻塞，也就相当于只有一个线程在工作
    //这个多线程就没有意义，而不用这个关键字而在资源争夺的更低层次进行同步就会更有效率
    public void startTask() {
        System.out.println(Thread.currentThread().getName() + "开始工作");
        //Song song = null;
        //String[] paramsAndEncSeckey = DealParams.getParamsAndEncSeckeyForCommentAPI().split("|");
        Song song = null;
        String dealingUrl = null;
        String commentCount = null;
        String[] aboutSong = null;
        String songId = null;
        DealHTML dh = new DealHTML();
        DealJSON dj = new DealJSON();
        //true表示自动提交
         SqlSession sqlSession = sqlSessionFactory.openSession(true);

        while (true) {
            if ((dealingUrl = LinkedQueue.isUnVisitedUrlEmpty()) != null) {
//            if(false){
                //这里用trycatch为了让自己不能解决的问题给打印出来
                try {
                    songId = isSongPage(dealingUrl);
                    if (songId != null) {
                        //处理接口返回的关于歌曲的评论信息，取得评论数
                        commentCount = dj.dealJSON(postGetCommentCountJSON(paramsAndEncSeckey, songId));
                        //处理当前歌曲页面HTML信息，获取歌曲名，歌手名，还有专辑名
                        aboutSong = dh.dealHTML(dealingUrl);
                        //System.out.println(count.incrementAndGet() + " " + Thread.currentThread().getName() + " " + Arrays.toString(aboutSong) + " " + commentCount);
                        song = new Song(dealingUrl,aboutSong[0],aboutSong[1],aboutSong[2],Long.valueOf(commentCount));
                        sqlSession.insert("saveSongInfo",song);

                    } else {
                        //如果不是歌曲页面的话就处理下该页面，将页面的相关链接加入未访问URL队列中
                        URLInQueue.urlInQueue(dealingUrl);
                    }
                } catch (Exception e) {
                    System.out.println(dealingUrl);
                    try {
                        //出现异常一般是timeout，还有就是页面访问不到即404页面，所以先判断是不是404页面
                        //如果不是的话就重新访问这个对象
                        if (!If404.if404(dealingUrl)){
                            LinkedQueue.removeVisitedUrl(dealingUrl);
                            LinkedQueue.addUnVisitedUrl(dealingUrl);
                        }
                        //e.printStackTrace();
                        continue;
                    } catch (Exception e1) {

                    }
                }
            } else {
                synchronized (this) {
                    if (--WorkThreadCount > 0) {
                        try {
                            //如果还有工作线程就要wait一段时间，否则就直接退出，由于退出时没有修改WorkThreadCount
                            //相当于退出的时候工作的线程少了一个
                            wait(1000);
                        } catch (InterruptedException e) {
                                   e.printStackTrace();
                        }
                        ++WorkThreadCount;
                    } else {
                        System.out.println(Thread.currentThread().getName() + "已经停止工作");
                        sqlSession.close();
                        return;
                    }
                }


//                    //如果队列为空的话就，当前线程就先不工作
//                    --WorkThreadCount;
//                    //如果还有工作的线程的话说明还有线程可能往队列中加URL，就先wait一段时间
//                    if (WorkThreadCount > 0) {
//                        try {
//                            //这里wait一段时间是为了，刚开始的时候队列为空进入等待但没有其他线程能够去解除它的等待，
//                            // 最后可能就变成单线程的了
//                            wait(10000);
//                            //当恢复后将工作线程加1，继续去取队列里的URL
//                            ++WorkThreadCount;
//                        } catch (Exception e) {
//
//                        }
//                    } else {
//                        //没有工作的线程了，就通知所有在等待的线程解除等待然后退出，其他线程就会一个一个的退出
//                        notifyAll();
//                        System.out.println(Thread.currentThread().getName() + "结束工作");
//                        //sqlSession.close();
//                        return;
//                    }
            }
        }


    }


    public String postGetCommentCountJSON(String[] paramsAndEncSeckey, String songId) throws IOException {
        Document document = Jsoup.connect("http://music.163.com/weapi/v1/resource/comments/R_SO_4_" + songId + "/").cookie("appver", "1.5.0.75771")
                .header("Referer", "http://music.163.com/").data("params", paramsAndEncSeckey[0]).data("encSecKey", paramsAndEncSeckey[1])
                .ignoreContentType(true).post();
        return document.text();
    }

    public String isSongPage(String url) {
        if (url.matches("http://music\\.163\\.com/song\\?id=\\d+"))
            return url.substring(29);
        else
            return null;
    }

//      public String postGetCommentCount(String url) throws IOException{
//-------------------------------------------------------------------------------------------------------
//        try {
//            OutputStream output = null;
//            HttpGet httpGet = new HttpGet(url);
//            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
//            try {
//                if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                    String name = getFileNameByUrl(url);
//                    File file = new File("file\\" + name);
//                    output = new FileOutputStream(file);
//                    httpResponse.getEntity().writeTo(output);
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                output.close();
//                httpResponse.close();
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//------------------------------------------------------------------------------------------------------------
//        String responseBody = null;
//        try {
//            HttpGet httpGet = new HttpGet(url);
//            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
//                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
//                    int status = httpResponse.getStatusLine().getStatusCode();
//                    if (status == HttpStatus.SC_OK) {
//                        HttpEntity httpEntity = httpResponse.getEntity();
//                        return httpEntity != null ? EntityUtils.toString(httpEntity) : null;
//                    } else {
//                        throw new ClientProtocolException("Unexpected response status: " + status);
//                    }
//                }
//
//            };
//            responseBody = httpClient.execute(httpGet, responseHandler);
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            httpClient.close();
//
//        }
//        return responseBody;
// ----------------------------------------------------------------------------------------------------------------

    //   }

//    public String getFileNameByUrl(String url) {
//
//        url = url.substring(7);
//        Date date =new Date();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_mm_dd&hh_mm_ss");
//        String finalName=getMd5(url).substring(0,6)+"_"+simpleDateFormat.format(date);
//        return  finalName;
//
//    }
//    public String getMd5(String url){
//        String base = url + salt + Math.random();
//        String md5 = DigestUtils.md5Hex(base.getBytes());
//        return md5;
//    }


}




