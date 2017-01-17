package td.quang.vnplayer.models.network;

/**
 * Created by Quang_TD on 1/14/2017.
 */

public class RequestData {
    int length;
    int start;
    String q;
    String sort;

    public RequestData(int length, int start, String q, String sort) {
        this.length = length;
        this.start = start;
        this.q = q;
        this.sort = sort;
    }
}
