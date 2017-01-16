package td.quang.vnplayer.models.objects;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

/**
 * Created by djwag on 1/4/2017.
 */
@Data
public class Song implements Parcelable {
    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    private String id;
    private String artist;
    private String title;
    private String album;
    private String filePath;
    private Bitmap albumCover;
    private int duration;

    public Song() {

    }

    private Song(Builder builder) {
        id = builder.id;
        artist = builder.artist;
        title = builder.title;
        album = builder.album;
        filePath = builder.filePath;
        duration = builder.duration;
        albumCover = builder.albumCover;

    }

    protected Song(Parcel in) {
        id = in.readString();
        artist = in.readString();
        title = in.readString();
        album = in.readString();
        filePath = in.readString();
        albumCover = in.readParcelable(Bitmap.class.getClassLoader());
        duration = in.readInt();
    }


    public Uri getSource() {
        return Uri.parse(Uri.encode(filePath));
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(artist);
        dest.writeString(title);
        dest.writeString(album);
        dest.writeString(filePath);
        dest.writeParcelable(albumCover, flags);
        dest.writeInt(duration);
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
