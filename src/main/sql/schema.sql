use netease_music;
DROP TABLE song;
CREATE TABLE song(
  `song_Id` INT NOT NULL AUTO_INCREMENT COMMENT '歌曲序号',
  `song_name` VARCHAR(120) NOT NULL COMMENT '歌曲名',
  `singer` VARCHAR(120) NOT NULL COMMENT '歌手',
  `album` VARCHAR(120) NOT NULL COMMENT '专辑',
  `comment_count` INT COMMENT '评论数',
  `song_url` VARCHAR(120) NOT NULL COMMENT '歌曲链接',
  PRIMARY KEY (song_Id),
  KEY idx_singer(singer),
  KEY idx_song_name(song_name),
  KEY idx_album(album)

)DEFAULT CHARSET=utf8 COMMENT='网易云音乐歌曲表'