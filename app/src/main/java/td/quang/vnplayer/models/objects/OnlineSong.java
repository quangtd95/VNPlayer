package td.quang.vnplayer.models.objects;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Quang_TD on 1/14/2017.
 */
@Data
public class OnlineSong {
    int song_id;
    String title;
    String artist;
    int duration;
    int download_status;
    Source source;

    public Song toOffline() {
        return new Song.Builder()
                .setTitle(title)
                .setArtist(artist)
                .setDuration(duration * 1000)
                .setFilePath((source.b320 == null) ? source.b128 : source.b320)
                .build();
    }

    public String getFilePath() {
        return (source.b320 == null) ? source.b128 : source.b320;
    }

}

class Source {
    String lossless;
    @SerializedName("320") String b320;
    @SerializedName("128") String b128;

}
