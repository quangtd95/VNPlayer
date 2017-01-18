package td.quang.vnplayer.models.cloud;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import lombok.Setter;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.views.notifications.FirebaseTaskNotification;

/**
 * Created by Quang_TD on 1/18/2017.
 */

public class MyFirebase {
    private static final String URL_FIREBASE = "gs://test-firebase-86ef9.appspot.com";
    private static final String FOLDER_NAME = "songs";
    private static MyFirebase instance;
    private final FirebaseStorage storage;
    private final StorageReference storageRef;
    private final StorageReference songsRef;
    @Setter private UpLoadFinishedListener upLoadFinishedListener;

    private MyFirebase() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl(URL_FIREBASE);
        songsRef = storageRef.child(FOLDER_NAME);
    }

    public static synchronized MyFirebase getInstance() {
        if (instance == null) instance = new MyFirebase();
        return instance;
    }

    public void upload(Context mContext, Song song) {

        FirebaseTaskNotification firebaseTaskNotification = FirebaseTaskNotification.getInstance();
        boolean b = firebaseTaskNotification.showUpLoadNotification(mContext, song);
        if (!b) {
            upLoadFinishedListener.onUploadFail("Only 1 song/time");
            return;
        }
        Uri file = Uri.fromFile(new File(song.getFilePath()));
        StorageReference fileRef = songsRef.child(file.getLastPathSegment());
        UploadTask uploadTask = fileRef.putFile(file);
        uploadTask.
                addOnFailureListener(e -> {
                    firebaseTaskNotification.setFinished(false);
                    upLoadFinishedListener.onUploadFail("upload is interrupted");
                })
                .addOnSuccessListener(taskSnapshot -> {
                    firebaseTaskNotification.setFinished(true);
                    upLoadFinishedListener.onUploadSuccess("upload is completed");
                })
                .addOnProgressListener(taskSnapshot -> {
                    int progress = (int) (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    firebaseTaskNotification.setProgress(progress);
                });
    }

}
