package td.quang.vnplayer.models.objects;

import lombok.Data;

/**
 * Created by Quang_TD on 1/4/2017.
 */
@Data
public class Album {
    private final String thumPath;
    private final String album;
    private final String artist;

    public Album(String album, String artist, String thumPath) {
        this.thumPath = thumPath;
        this.album = album;
        this.artist = artist;
    }

}
