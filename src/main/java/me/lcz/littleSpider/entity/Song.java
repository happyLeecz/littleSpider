package me.lcz.littleSpider.entity;

/**
 * Created by lcz on 2016/11/14.
 */
public class Song {
    private Integer songId;
    private String songUrl;
    private String songName;
    private String singer;
    private String album;
    private Long commentCount;

    public Song(String songUrl, String songName, String singer, String album,Long commentCount) {
        this.songUrl = songUrl;
        this.songName = songName;
        this.singer = singer;
        this.album = album;
        this.commentCount = commentCount;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
