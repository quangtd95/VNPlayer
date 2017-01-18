package td.quang.vnplayer.models.cloud;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

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
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import td.quang.vnplayer.broadcasts.BroadCastToUI;
import td.quang.vnplayer.models.objects.Song;
import td.quang.vnplayer.models.objects.SongMetadata;
import td.quang.vnplayer.utils.AudioUtils;
import td.quang.vnplayer.views.notifications.FirebaseTaskNotification;

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

    @Setter private UpLoadFinishedListener upLoadFinishedListener;

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
                    SongMetadata songMetadata = new SongMetadata();

                    songMetadata.setDuration(AudioUtils.getDuration(mContext, song));
                    songMetadata.setId(song.hashId());
                    songMetadata.setTitle(song.getTitle());
                    songMetadata.setArtist(song.getArtist());
                    songMetadata.setNameUploader(Build.MODEL);
                    songMetadata.setFilePath(taskSnapshot.getDownloadUrl().toString());

                    songNameRef.child(songMetadata.getId()).setValue(songMetadata);
                })
                .addOnProgressListener(taskSnapshot -> {
                    int progress = (int) (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    firebaseTaskNotification.setProgress(progress);
                });
    }

    public void downloadFromCloud(String filePath) {
        StorageReference httpsReference = storage.getReferenceFromUrl(filePath);
        try {
            File localFile = File.createTempFile("mysong", "mp3");
            FileDownloadTask downloadTask = httpsReference.getFile(localFile);
            downloadTask.addOnProgressListener()
                    .addOnSuccessListener()
                    .addOnFailureListener()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
