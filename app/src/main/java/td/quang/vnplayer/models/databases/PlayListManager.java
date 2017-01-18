package td.quang.vnplayer.models.databases;

import java.util.List;

import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.presenters.playoffline.OnPreparePlaylistListener;

/**
 * Created by Quang_TD on 1/16/2017.
 */

public interface PlayListManager {
    void createPlaylist(List<Song> songs, int position);

    void deletePlaylist();

    void addToPlaylist(Song song);

    void removeItemPlaylist(Song song);

    Song getSongById(String id);

    List<Song> getPlaylist();

    void setOnPreparePlaylistListener(OnPreparePlaylistListener onPreparePlaylistListener);
}
