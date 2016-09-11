package uday.videoplay.video;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.io.FileInputStream;

import uday.videoplay.R;


public class HomeActivity extends AppCompatActivity {


    private static final String TAG = "HomeActivity";
    VideoView home_video_vv;
//    MediaController media_Controller;

    DisplayMetrics dm;
    String SrcPath = Environment.getExternalStorageDirectory().getPath()+"/yag.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



//        sendNotification("Click here to Cancel ");

//        ShowNotification("Click here to Cancel ");

//        initData();
        vi();
    }

    public void initData(){
        try {
            home_video_vv = (VideoView)findViewById(R.id.home_video_vv);
//            media_Controller = new MediaController(HomeActivity.this);
//            dm = new DisplayMetrics();
//            this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//            int height = dm.heightPixels;
//            int width = dm.widthPixels;
//        home_video_vv.setMinimumWidth(width);
//        home_video_vv.setMinimumHeight(height);
//            home_video_vv.setMediaController(media_Controller);

            home_video_vv.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/yag.mp4");
            home_video_vv.setMediaController(new MediaController(this));
            home_video_vv.start();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public void vi(){
        try {
            VideoView myVideoView = (VideoView)findViewById(R.id.home_video_vv);
//
//            File filePath = new File(playRecordPath);
//
//            if (!filePath.exists())
//            {
//                filePath.createNewFile();
//            }
//
//            FileInputStream is = new FileInputStream(filePath);


            myVideoView.setVideoPath(SrcPath);
            myVideoView.setMediaController(new MediaController(this));
            myVideoView.requestFocus();
            myVideoView.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showVideo(View v){
        if(!isServiceRunning()) {
            startService(new Intent(HomeActivity.this, DrawOn.class));
        }
        ShowNotification("Click here to Cancel ",false);

    }
    public void hideVideo(View v){
            Intent stopTrackMapService = new Intent(HomeActivity.this, DrawOn.class);
            stopService(stopTrackMapService);
        ShowNotification("Click here to Cancel ",true);
    }

    public void screenLockOn(View v){
        if(!isServiceRunning()) {
            startService(new Intent(HomeActivity.this, LockScreenService.class));
        }

//        ShowNotification("Screen Lock",false);
    }
    public void screenLockOff(View v){
        Intent stopTrackMapService = new Intent(HomeActivity.this, LockScreenService.class);
        stopService(stopTrackMapService);

//        ShowNotification("Click here to Cancel ",true);
    }



    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("DrawOn".equals(service.service.getClassName())) {

                Log.e(TAG, "isServiceRunningLatlng: True" );
                return true;


            }
        }
        Log.e(TAG, "isServiceRunningLatlng: False" );
        return false;

    }

    private boolean isLOckServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("LockScreenService".equals(service.service.getClassName())) {

                Log.e(TAG, "isServiceRunningLatlng: True" );
                return true;


            }
        }
        Log.e(TAG, "isServiceRunningLatlng: False" );
        return false;

    }




//    private void sendNotification(String message) {
//        Intent intent = new Intent(this, SplashActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Screen Locked")
//                .setContentText(message)
//                .setAutoCancel(false)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }


    public void ShowNotification(String message, boolean can_state) {
        // notification icon
        int icon = R.mipmap.ic_launcher;
//	        above jellybean
        int icon_tran = R.mipmap.ic_launcher;
        String title=getString(R.string.app_name);

        Intent notificationIntent;
        long when = System.currentTimeMillis();

//	            Drawable d=mContext.getDrawable(R.drawable.notify);
//	            Bitmap icon_bitmap = ((BitmapDrawable)d).getBitmap();
        Bitmap icon_bitmap = BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.mipmap.ic_launcher);

        notificationIntent = new Intent(HomeActivity.this, HomeActivity.class);

        Log.e("Gcm","From Gcm intent to Pushnotf");

//        Intent stopTrackMapService = new Intent(SplashActivity.this, LockScreenService.class);
//        stopService(stopTrackMapService);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        HomeActivity.this,
                        0,
                        notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        android.support.v4.app.NotificationCompat.InboxStyle inboxStyle = new android.support.v4.app.NotificationCompat.InboxStyle();

        android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(
                HomeActivity.this);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(title)

                .setContentTitle(title)
                .setStyle(inboxStyle)
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), icon))
                .setWhen(System.currentTimeMillis())
                .setContentText(message)
                .setAutoCancel(false)
                .setOngoing(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(5, notification);

        if(can_state)
        notificationManager.cancelAll();

    }


}
