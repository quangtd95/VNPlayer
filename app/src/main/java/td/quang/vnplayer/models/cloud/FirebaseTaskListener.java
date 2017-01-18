package td.quang.vnplayer.models.cloud;

/**
 * Created by Quang_TD on 1/18/2017.
 */

public interface FirebaseTaskListener {
    void onSuccess(String message);

    void onFail(String message);
}
