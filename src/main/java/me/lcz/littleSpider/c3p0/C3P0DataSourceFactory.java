package me.lcz.littleSpider.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * Created by lcz on 2016/11/24.
 */
    public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {

        public C3P0DataSourceFactory() {
            this.dataSource = new ComboPooledDataSource();
        }
}
