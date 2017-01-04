package td.quang.vnplayer.models.objects;

import lombok.Data;

/**
 * Created by djwag on 1/4/2017.
 */
@Data
public class Album {
    private String thumPath;
    private String album;
    private String artist;

    public Album(String album, String artist, String thumPath) {
        this.thumPath = thumPath;
        this.album = album;
        this.artist = artist;
    }

}
