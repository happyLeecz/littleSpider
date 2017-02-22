package me.lcz.littleSpider;

import me.lcz.littleSpider.dao.SongDao;
import me.lcz.littleSpider.entity.Song;
import me.lcz.littleSpider.mybatisAbout.Start;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by lcz on 2016/11/24.
 */
public class Test3 {

    public static void main(String[] args) {
        Song song = new Song("a","b","c","d",5L);
        Song song1 = new Song("a1","b1","c1","d1",6L);
        Song song2 = new Song("a","b","大sds","大",122L);
        Start start = new Start();
        SqlSessionFactory sqlSessionFactory = start.getSqlSessionFactory();
        //这里的true自动commit
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
//        sqlSession.insert("saveSongInfo",song);
//        sqlSession.insert("saveSongInfo",song1);
        SongDao songDao = sqlSession.getMapper(SongDao.class);
        songDao.saveSongInfo(song2);
    }
}
