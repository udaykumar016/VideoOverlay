package uday.videoplay.vlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import uday.videoplay.R;

public class ViewVideo extends Activity {
      private String filename;
      VideoView vv;
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            System.gc();
            Intent i = getIntent();
            Bundle extras = i.getExtras();
            filename = extras.getString("videofilename");
            // vv = new VideoView(getApplicationContext());
            setContentView(R.layout.activity_view);
            vv = (VideoView) findViewById(R.id.videoView);
            vv.setVideoPath(filename);
            vv.setMediaController(new MediaController(this));
            vv.requestFocus();
            vv.start();
      }
}
