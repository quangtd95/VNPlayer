package td.quang.vnplayer.views.dialogs;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by djwag on 1/7/2017.
 */

public class MyDialog {

    public static SweetAlertDialog showLoading(Context mContext) {
        SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        return pDialog;
    }

    public static void hideLoading(SweetAlertDialog pDialog) {
        pDialog.dismiss();
    }

    public static void showError(Context mContext) {
        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .show();
    }
}
