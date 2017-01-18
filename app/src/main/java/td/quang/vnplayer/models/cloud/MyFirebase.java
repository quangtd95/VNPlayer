package td.quang.vnplayer.models.cloud;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import td.quang.vnplayer.broadcasts.BroadCastToUI;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.models.objects.SongMetadata;
import td.quang.vnplayer.utils.AudioUtils;
import td.quang.vnplayer.views.notifications.FirebaseDownloadNotification;
import td.quang.vnplayer.views.notifications.FirebaseUploadNotification;

/**
 * Created by Quang_TD on 1/18/2017.
 */

public class MyFirebase {
    private static final String URL_FIREBASE = "gs://test-firebase-86ef9.appspot.com";
    private static final String FOLDER_NAME = "songs";
    private static MyFirebase instance;

    //storage music
    private final FirebaseStorage storage;
    private final StorageReference storageRef;
    private final StorageReference songsRef;

    //name music
    private DatabaseReference songNameRef;

    @Setter private FirebaseTaskListener firebaseTaskListener;

    @Getter private List<SongMetadata> mCloudSongs;

    private MyFirebase() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl(URL_FIREBASE);
        songsRef = storageRef.child(FOLDER_NAME);
        mCloudSongs = new LinkedList<>();
        songNameRef = FirebaseDatabase.getInstance().getReference();
        songNameRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                mCloudSongs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SongMetadata songMetadata = postSnapshot.getValue(SongMetadata.class);
                    mCloudSongs.add(songMetadata);
                }
                Intent intent = new Intent();
                intent.setAction(BroadCastToUI.ACTION_UPDATE_CLOUD);
                storage.getApp().getApplicationContext().sendBroadcast(intent);
            }

            @Override public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static synchronized MyFirebase getInstance() {
        if (instance == null) instance = new MyFirebase();
        return instance;
    }

    public void upload(Song song) {
        FirebaseUploadNotification firebaseUploadNotification = FirebaseUploadNotification.getInstance();
        boolean b = firebaseUploadNotification.showUpLoadNotification(storage.getApp().getApplicationContext(), song);
        if (!b) {
            firebaseTaskListener.onFail("Only 1 song/time");
            return;
        }
        Uri file = Uri.fromFile(new File(song.getFilePath()));
        StorageReference fileRef = songsRef.child(file.getLastPathSegment());
        UploadTask uploadTask = fileRef.putFile(file);
        uploadTask.
                addOnFailureListener(e -> {
                    firebaseUploadNotification.setFinished(false);
                    firebaseTaskListener.onFail("upload is interrupted");
                })
                .addOnSuccessListener(taskSnapshot -> {
                    firebaseUploadNotification.setFinished(true);
                    firebaseTaskListener.onSuccess("upload is completed");
                    SongMetadata songMetadata = new SongMetadata();

                    songMetadata.setDuration(AudioUtils.getDuration(storage.getApp().getApplicationContext(), song));
                    songMetadata.setId(song.hashId());
                    songMetadata.setTitle(song.getTitle());
                    songMetadata.setArtist(song.getArtist());
                    songMetadata.setNameUploader(Build.MODEL);
                    songMetadata.setFilePath(taskSnapshot.getDownloadUrl().toString());

                    songNameRef.child(songMetadata.getId()).setValue(songMetadata);
                })
                .addOnProgressListener(taskSnapshot -> {
                    int progress = (int) (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    firebaseUploadNotification.setProgress(progress);
                });
    }

    public void downloadFromCloud(Song song) {
        FirebaseDownloadNotification firebaseDownloadNotification = FirebaseDownloadNotification.getInstance();
        boolean b = firebaseDownloadNotification.showDownLoadNotification(storage.getApp().getApplicationContext(), song);
        if (!b) {
            firebaseTaskListener.onFail("Only 1 song/time");
            return;
        }
        StorageReference httpsReference = storage.getReferenceFromUrl(song.getFilePath());
        File SDCardRoot = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(SDCardRoot, song.getTitle().concat(".mp3"));
        FileDownloadTask downloadTask = httpsReference.getFile(file);
        downloadTask
                .addOnProgressListener(taskSnapshot -> {
                    int progress = (int) (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    int constant = 1000;
                    if (taskSnapshot.getBytesTransferred() % constant == 0) {
                        firebaseDownloadNotification.setProgress(progress);
                    }
                })
                .addOnSuccessListener(taskSnapshot -> {
                    firebaseTaskListener.onSuccess("downloaded successfully. check in \"download\" folder");
                    firebaseDownloadNotification.setFinished(true);
                })
                .addOnFailureListener(e -> firebaseTaskListener.onSuccess("download is interrupted"));
    }
}
