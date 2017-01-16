package td.quang.vnplayer.views.dialogs;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by djwag on 1/10/2017.
 */

public class MyToast {
    public static void show(View view, String message) {
        int[] points = new int[2];
        view.getLocationOnScreen(points);
        int x = points[0];
        int y = points[1] - view.getHeight();
        Toast toast = Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.START | Gravity.TOP, x, y);
        toast.show();
    }
}
