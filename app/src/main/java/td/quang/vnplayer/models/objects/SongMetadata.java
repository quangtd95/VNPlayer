package td.quang.vnplayer.models.objects;

import com.google.firebase.database.IgnoreExtraProperties;

import lombok.Data;

/**
 * Created by Quang_TD on 1/18/2017.
 */
@IgnoreExtraProperties
@Data
public class SongMetadata {
    String title;
    String artist;
    String id;
    String filePath;
    int duration;
    String nameUploader;

    public SongMetadata() {

    }

}
