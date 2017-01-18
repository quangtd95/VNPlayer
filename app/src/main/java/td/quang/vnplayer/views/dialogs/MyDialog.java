package td.quang.vnplayer.views.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import td.quang.vnplayer.R;
import td.quang.vnplayer.presenters.playoffline.MainPresenter;

/**
 * Created by Quang_TD on 1/7/2017.
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
        if (text == null) {
            showError(mContext);
            return;
        }

        new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText(text)
                .show();
    }

    public static void showSuccess(Context mContext, String text) {
        if (text == null) {
            showError(mContext);
            return;
        }
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


    public static SweetAlertDialog showConfirm(Context mContext) {
        return new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(SweetAlertDialog::cancel);
    }

    public static void showMessage(Context mContext, String message) {
        new SweetAlertDialog(mContext)
                .setTitleText(message)
                .show();
    }

    public static void showSchedule(Context mContext, MainPresenter mainPresenter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_schedule, null);
        AlertDialog alertDialog = builder
                .setView(dialogView)
                .setCancelable(true)
                .create();

        SeekBar seekBarSchedule = (SeekBar) dialogView.findViewById(R.id.seekBarSchedule);
        TextView tvTimeSchedule = (TextView) dialogView.findViewById(R.id.tvTimeSchedule);

        seekBarSchedule.setMax(30);

        seekBarSchedule.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTimeSchedule.setText(String.format("%d minutes", seekBar.getProgress()));
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        alertDialog.setOnDismissListener(dialog -> {
            int minutes = seekBarSchedule.getProgress();
            mainPresenter.setSchedule(minutes);
            if (minutes == 0) {
                Toast.makeText(mContext, "clear!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, String.format("%dminutes", minutes), Toast.LENGTH_SHORT).show();
            }

        });
        alertDialog.show();
    }
}
