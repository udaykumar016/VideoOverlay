package uday.videoplay.video;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Random;

import uday.videoplay.R;

/**
 * Created by Lakki on 01-Jul-16.
 */
public class DrawOn extends Service {



    private WindowManager windowManager;

    private LinearLayout main_ll;
    String filename;
    private static final String TAG = "DrawOn";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        filename=(String) intent.getExtras().get("videofilename");
//
//        Log.e(TAG, "onStartCommand: "+filename );
//
//        return super.onStartCommand(intent, flags, startId);
//
//
//
//    }


//    @Override
//    public void onCreate() {
//        super.onCreate();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e(TAG, "onCreate: " );

        try {
            filename=(String) intent.getExtras().get("videofilename");
        } catch (Exception e) {
            e.printStackTrace();
        }

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);


        main_ll = new LinearLayout(this);
        main_ll.setOrientation(LinearLayout.VERTICAL);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER | Gravity.LEFT;
        params.x = 0;
        params.y = 100;

//        final WindowManager.LayoutParams params2 = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_PHONE,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                PixelFormat.TRANSLUCENT);

                final WindowManager.LayoutParams params2 = new WindowManager.LayoutParams(
                        100,
                100,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);




        params2.gravity = Gravity.CENTER ;

        System.gc();
        VideoView   vv =  new VideoView(this);
        main_ll.addView(vv);

        try {

            Log.e(TAG, "onCreate: "+filename );
            vv.setVideoPath(filename);

            vv.setMediaController(new MediaController(this));
            vv.requestFocus();
            vv.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

        windowManager.addView(main_ll,params2);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



    public void video(){
        System.gc();
//        Intent i = getApplication().getIntent();
//        Bundle extras = i.getExtras();
//        String filename = extras.getString("videofilename");
        // vv = new VideoView(getApplicationContext());
//        VideoView   vv =  (VideoView) findViewById(R.id.videoView);
        VideoView   vv =  new VideoView(this);
        vv.setVideoPath(filename);
        vv.setMediaController(new MediaController(this));
        vv.requestFocus();
        vv.start();
    }
}
