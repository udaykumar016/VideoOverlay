package uday.videoplay.video;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import uday.videoplay.R;



public class VideoService extends Service {

    private static final String TAG = "VideoService";

    private LinearLayout main_ll;
    String filename;
    ImageView close;


    public VideoService() {
    }

    private WindowManager windowManager;
    VideoView vv;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Log.e(TAG, "onStartCommand: " );
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            try {
                filename=(String) intent.getExtras().get("videofilename");
            } catch (Exception e) {
                e.printStackTrace();
            }

        main_ll = new LinearLayout(this);
            main_ll.setOrientation(LinearLayout.VERTICAL);
            vv =  new VideoView(this);
//        main_ll.setMinimumHeight(100);
//        main_ll.setMinimumWidth(200);
        main_ll.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        main_ll.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));

            close = new ImageView(this);
            close.setImageDrawable(getResources().getDrawable(R.mipmap.ic_clear_black_24dp));

            final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    300,
                    300,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
            System.gc();
//        VideoView vv =  new VideoView(this);
//        main_ll.removeAllViews();
            main_ll.addView(close);
        main_ll.addView(vv);

            try {
                vv.setVideoPath(filename);
                vv.setMediaController(new MediaController(this));
                vv.requestFocus();
                vv.start();
            } catch (Exception e) {
                e.printStackTrace();
            }


            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent stopTrackMapService = new Intent(getApplicationContext(), VideoService.class);
                    stopService(stopTrackMapService);

                    Log.e(TAG, "onClick: close");
                }
            });

//            windowManager.addView(vv,params);
            windowManager.addView(main_ll,params);

//        vv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e(TAG, "onClick: ----------------------" );
//                Log.e(TAG, "onClick: ----------------------" );
//                Log.e(TAG, "onClick: ----------------------" );
//                Log.e(TAG, "onClick: ----------------------" );
//                Log.e(TAG, "onClick: ----------------------" );
//            }
//        });


            vv.setOnTouchListener(new View.OnTouchListener() {
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            return true;
                        case MotionEvent.ACTION_UP:
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
//                            windowManager.updateViewLayout(vv, params);
                            windowManager.updateViewLayout(main_ll, params);
                            return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return super.onStartCommand(intent, flags, startId);
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        Log.e(TAG, "onCreate: " );
//
//
//
//        chatHead = new ImageView(this);
//        chatHead.setImageResource(R.mipmap.ic_launcher);
//
//        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_PHONE,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                PixelFormat.TRANSLUCENT);
//
//        params.gravity = Gravity.TOP | Gravity.LEFT;
//        params.x = 0;
//        params.y = 100;
//
////        windowManager.addView(chatHead, params);
////        windowManager.addView(main_ll, params);
//
//        chatHead.setOnTouchListener(new View.OnTouchListener() {
//            private int initialX;
//            private int initialY;
//            private float initialTouchX;
//            private float initialTouchY;
//
//            @Override public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        initialX = params.x;
//                        initialY = params.y;
//                        initialTouchX = event.getRawX();
//                        initialTouchY = event.getRawY();
//                        return true;
//                    case MotionEvent.ACTION_UP:
//                        return true;
//                    case MotionEvent.ACTION_MOVE:
//                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
//                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
//                        windowManager.updateViewLayout(chatHead, params);
//                        return true;
//                }
//                return false;
//            }
//        });
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
//            main_ll.removeAllViews();
            if (main_ll != null) windowManager.removeView(main_ll);
            if (vv != null)windowManager.removeView(vv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
