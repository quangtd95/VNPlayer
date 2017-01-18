package td.quang.vnplayer.models.cloud;

/**
 * Created by Quang_TD on 1/18/2017.
 */

public interface UpLoadFinishedListener {
    void onUploadSuccess(String message);

    void onUploadFail(String message);
}
