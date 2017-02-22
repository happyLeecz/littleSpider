package me.lcz.littleSpider.dao;

import me.lcz.littleSpider.entity.Song;
import me.lcz.littleSpider.mybatisAbout.Start;

import java.io.IOException;

/**
 * Created by lcz on 2016/11/24.
 */
public interface SongDao {
    public void saveSongInfo(Song song);
}
