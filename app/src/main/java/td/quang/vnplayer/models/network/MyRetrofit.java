package td.quang.vnplayer.models.network;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import td.quang.vnplayer.models.objects.OnlineSong;
import td.quang.vnplayer.presenters.playonline.OnQueryFinishedListener;

/**
 * Created by Quang_TD on 1/14/2017.
 */

public class MyRetrofit implements Callback<OnlineSongList> {
    private final static String BASE_URL = "http://api.mp3.zing.vn";

    private OnQueryFinishedListener queryFinishedListener;

    public void setQueryFinishedListener(OnQueryFinishedListener queryFinishedListener) {
        this.queryFinishedListener = queryFinishedListener;
    }

    public void search(String keyword) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OnlineSongAPI onlineSongAPI = retrofit.create(OnlineSongAPI.class);
        Gson gson = new Gson();
        String json = gson.toJson(new RequestData(20, 0, keyword, "hot"));
        Call<OnlineSongList> onlineSongListCall = onlineSongAPI.getOnlineSongs(json);
        onlineSongListCall.enqueue(this);
    }

    @Override public void onResponse(Call<OnlineSongList> call, Response<OnlineSongList> response) {
        ArrayList<OnlineSong> onlineSongs = (ArrayList<OnlineSong>) response.body().docs;
        removeEmplySong(onlineSongs);
        queryFinishedListener.onQuerySuccess(onlineSongs);
    }

    private void removeEmplySong(ArrayList<OnlineSong> onlineSongs) {
        for (int i = 0; i < onlineSongs.size(); i++) {
            if (onlineSongs.get(i).getFilePath().equalsIgnoreCase("")) {
                onlineSongs.remove(i);
                i--;
            }
        }
    }

    @Override public void onFailure(Call<OnlineSongList> call, Throwable t) {
        queryFinishedListener.onQueryFail();
    }
}
