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

    public static void showError(Context mContext, String text) {
        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(text)
                .show();
    }

    public static void showSuccess(Context mContext, String text) {
        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Successfully!")
                .setContentText(text)
                .show();
    }

    public static void showSuccess(Context mContext) {
        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Successfully!")
                .setContentText("")
                .show();
    }

    /**
     * @param mContext
     * @return must declare listener when touch ok
     */
    public static SweetAlertDialog showConfirm(Context mContext) {
        SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(sDialog -> sDialog.cancel());
        return dialog;
    }
}
