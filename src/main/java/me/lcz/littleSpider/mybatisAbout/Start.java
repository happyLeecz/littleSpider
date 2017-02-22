package me.lcz.littleSpider.mybatisAbout;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by lcz on 2016/11/24.
 */
public class Start {


    public SqlSessionFactory getSqlSessionFactory() {
         SqlSessionFactory sqlSessionFactory = null;
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;

    }
}
