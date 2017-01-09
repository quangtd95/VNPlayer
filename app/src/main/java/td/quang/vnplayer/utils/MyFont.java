package td.quang.vnplayer.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

/**
 * Created by djwag on 1/4/2017.
 */

public class MyFont {
    public static final String FONT_AWESOME = "fontawesome-webfont.ttf";

    public static Typeface getFont(Context context, View view, String fontName) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
    }
}
