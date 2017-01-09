package td.quang.vnplayer.views.fragments.home;

/**
 * Created by djwag on 1/7/2017.
 */

public interface LoadSongView {
    void refreshListSong();

    void showLoading();

    void hideLoading();

    void showDialogLoadFail();

    void showDialogConfirmDelete(String filePath, int position);

    void showDeleteSuccess();

    void showDeleteError();
}
