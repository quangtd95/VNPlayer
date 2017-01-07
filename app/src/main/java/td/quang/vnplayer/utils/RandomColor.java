package td.quang.vnplayer.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by djwag on 1/7/2017.
 */

public class RandomColor {
    private static int[] colors = new int[]{
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            Color.GRAY
    };

    public static int getColor() {
        return colors[new Random().nextInt(colors.length)];
    }
}
