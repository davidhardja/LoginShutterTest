package com.example.android.loginshuttertest;

/**
 * Created by bocist-8 on 01/10/15.
 */
public class Shuttersong {
    private String file_url;
    private String timeline_image_url;
    private String audio_url;
    private String thumb_url;
    private String share_url;
    private String caption;
    private String uuid;
    private boolean is_mine;
    private int favorite_count;
    private int comment_count;
    private String song_title;
    private String song_artist;
    private String song_album;
    private String created_at;
    private String updated_at;

    public Shuttersong(String file_url, String timeline_image_url, String audio_url, String share_url, String thumb_url, String caption, String uuid, boolean is_mine, int favorite_count, int comment_count, String song_title, String song_artist, String song_album, String created_at, String updated_at) {
        this.file_url = file_url;
        this.timeline_image_url = timeline_image_url;
        this.audio_url = audio_url;
        this.share_url = share_url;
        this.thumb_url = thumb_url;
        this.caption = caption;
        this.uuid = uuid;
        this.is_mine = is_mine;
        this.favorite_count = favorite_count;
        this.comment_count = comment_count;
        this.song_title = song_title;
        this.song_artist = song_artist;
        this.song_album = song_album;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getTimeline_image_url() {
        return timeline_image_url;
    }

    public void setTimeline_image_url(String timeline_image_url) {
        this.timeline_image_url = timeline_image_url;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public boolean is_mine() {
        return is_mine;
    }

    public void setIs_mine(boolean is_mine) {
        this.is_mine = is_mine;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getSong_title() {
        return song_title;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public String getSong_artist() {
        return song_artist;
    }

    public void setSong_artist(String song_artist) {
        this.song_artist = song_artist;
    }

    public String getSong_album() {
        return song_album;
    }

    public void setSong_album(String song_album) {
        this.song_album = song_album;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

