package td.quang.vnplayer.services;

/**
 * Created by Quang_TD on 1/9/2017.
 */

public interface MusicService {
    //    void play(Song song);
    void play(int position);

    void stop();

    void resume();

    void pause();

    void next();

    void prev();

    void seek(int position);

    void setRepeat(boolean b);

    void setShuffle(boolean b);

    void getCurrentState();

    void getPlaylist(int position);

    void removeInPlayList(int position);
}
