package td.quang.vnplayer.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Quang_TD on 1/7/2017.
 */

public class RandomColor {
    private static int[] colors = new int[]{
            Color.parseColor("#263238"),
            Color.parseColor("#37474F"),
            Color.parseColor("#607D8B"),
            Color.parseColor("#ECEFF1"),
            Color.parseColor("#CFD8DC"),
            Color.parseColor("#B0BEC5"),
            Color.parseColor("#90A4AE"),
            Color.parseColor("#78909C"),
            Color.parseColor("#607D8B"),
            Color.parseColor("#546E7A"),
            Color.parseColor("#455A64")
    };

    public static int getColor() {
        return colors[new Random().nextInt(colors.length)];
    }
}
