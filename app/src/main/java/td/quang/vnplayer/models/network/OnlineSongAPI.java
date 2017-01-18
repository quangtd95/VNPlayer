package td.quang.vnplayer.models.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Quang_TD on 1/14/2017.
 */

public interface OnlineSongAPI {
    //    http://api.mp3.zing.vn/api/mobile/search/song?requestdata={"length":5,"start":0,"q":"hello","sort":"hot"}&keycode=b319bd16be6d049fdb66c0752298ca30&fromvn=true
    @GET("/api/mobile/search/song?keycode=b319bd16be6d049fdb66c0752298ca30&fromvn=true")
    Call<OnlineSongList> getOnlineSongs(@Query("requestdata") String json);
}
