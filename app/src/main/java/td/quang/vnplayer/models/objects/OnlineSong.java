package td.quang.vnplayer.models.objects;

/**
 * Created by djwag on 1/14/2017.
 */

public class OnlineSong {
    //int song_id;
    String title;
    String artist;
    // int duration;
    //  int download_status;
//    Source source;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}

//class Source {
//    String lossless;
//    @SerializedName("320") String b320;
//    @SerializedName("128") String b128;
//
//}
