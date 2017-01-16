package td.quang.vnplayer.views.fragments.home;

import java.util.ArrayList;

import td.quang.vnplayer.models.objects.OnlineSong;

/**
 * Created by djwag on 1/7/2017.
 */

public interface LoadSongView {
    void refreshListSong();

    void refreshListSong(ArrayList<OnlineSong> list);

    void showLoading();

    void hideLoading();

    void showDialogLoadFail();

    void showDialogConfirmDelete(String filePath, int position);

    void showSuccess(String message);

    void showError(String message);
}
