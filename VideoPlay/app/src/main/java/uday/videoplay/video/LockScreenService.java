package uday.videoplay.video;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uday.videoplay.R;

public class LockScreenService extends Service {

    private WindowManager windowManager;
    private ImageView chatHead;
    TextView t1;
    private LinearLayout main_ll;
//    LinearLayout child_ll;

    int fontsize = 20;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);

        t1 = new TextView(this);

        chatHead.setImageResource(R.mipmap.ic_launcher);

        t1.setText("CLOSE");

        t1.setTextSize(fontsize);

        t1.setTextColor(Color.WHITE);


        main_ll = new LinearLayout(this);
//        child_ll = new LinearLayout(this);
//        main_ll.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        main_ll.setOrientation(LinearLayout.VERTICAL);
//        child_ll.setOrientation(LinearLayout.HORIZONTAL);
//        child_ll.setLayoutParams(new LinearLayout.LayoutParams(500,500));
        main_ll.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        main_ll.setBackground(getResources().getDrawable(R.mipmap.ic_launcher));



        final WindowManager.LayoutParams params2 = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params2.gravity = Gravity.CENTER ;
        main_ll.addView(t1);

//        child_ll.addView(t1);

//        main_ll.addView(t1);

        windowManager.addView(main_ll,params2);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopTrackMapService = new Intent(getApplicationContext(), LockScreenService.class);
                stopService(stopTrackMapService);

                Log.e("", "onClick: Close" );
            }
        });



//        main_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent stopTrackMapService = new Intent(getApplicationContext(), LockScreenService.class);
//                stopService(stopTrackMapService);
//                Log.e("", "onClick: Close" );
//            }
//        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        if (chatHead != null) windowManager.removeView(chatHead);
        if (chatHead != null) windowManager.removeView(main_ll);
    }

}
