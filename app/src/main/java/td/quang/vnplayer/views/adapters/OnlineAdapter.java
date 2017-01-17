package td.quang.vnplayer.views.adapters;

import java.util.ArrayList;

import td.quang.vnplayer.models.objects.OnlineSong;

/**
 * Created by Quang_TD on 1/10/2017.
 */

interface OnlineAdapter {

    void setData(ArrayList<OnlineSong> songs);

    void playSongOnClick(int position);


}
