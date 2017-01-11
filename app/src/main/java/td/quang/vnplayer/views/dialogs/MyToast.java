package td.quang.vnplayer.views.dialogs;

import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by djwag on 1/10/2017.
 */

public class MyToast {
    public static void show(View view, String message) {
        int x = view.getLeft();
        int y = view.getTop();
        Toast toast = Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.LEFT, x, y);
        toast.show();
    }
}
