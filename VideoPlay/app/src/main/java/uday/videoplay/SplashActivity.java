package uday.videoplay;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uday.videoplay.video.HomeActivity;
import uday.videoplay.vlist.VideoStoredInSDCard;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            gotoHome();
            }
        },2000);
    }

    public void gotoHome(){
        Intent in = new Intent(SplashActivity.this, VideoStoredInSDCard.class);
        startActivity(in);
        finish();
    }
}
