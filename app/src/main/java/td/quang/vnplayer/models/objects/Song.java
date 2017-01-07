package td.quang.vnplayer.models.objects;

import android.graphics.Bitmap;

import lombok.Data;

/**
 * Created by djwag on 1/4/2017.
 */
@Data
public class Song {
    private String id;
    private String artist;
    private String title;
    private String album;
    private String filePath;
    private Bitmap albumCover;
    private int duration;

    public Song() {

    }

    public Song(Builder builder) {
        id = builder.id;
        artist = builder.artist;
        title = builder.title;
        album = builder.album;
        filePath = builder.filePath;
        duration = builder.duration;
        albumCover = builder.albumCover;

    }

    public static class Builder {

        private String id;
        private String artist;
        private String title;
        private String album;
        private String filePath;
        private int duration;
        private Bitmap albumCover;

        public Builder() {
        }

        public Song build() {
            return new Song(this);
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setAlbum(String album) {
            this.album = album;
            return this;
        }

        public Builder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setAlbumCover(Bitmap albumCover) {
            this.albumCover = albumCover;
            return this;
        }

    }
}
