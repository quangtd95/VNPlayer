package td.quang.vnplayer.views.customviews;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.Button;

import td.quang.vnplayer.utils.MyFont;

/**
 * Created by djwag on 1/4/2017.
 */

public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
        setFont();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFont();
    }

    private void setFont() {
        this.setTypeface(MyFont.getFont(getContext(), this, MyFont.FONT_AWESOME));
    }
}
