package uday.videoplay.vlist;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import uday.videoplay.R;
import uday.videoplay.video.DrawOn;
import uday.videoplay.video.HomeActivity;
import uday.videoplay.video.VideoService;

public class VideoStoredInSDCard extends Activity {
	private Cursor videocursor;
	private int video_column_index;
	ListView videolist;
	int count;

	private static final String TAG = "VideoStoredInSDCard";
	String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
			MediaStore.Video.Thumbnails.VIDEO_ID };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init_phone_video_grid();
	}

	@SuppressWarnings("deprecation")
	private void init_phone_video_grid() {
		System.gc();
		String[] proj = { MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DATA,
				MediaStore.Video.Media.DISPLAY_NAME,
				MediaStore.Video.Media.SIZE };
		videocursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
				proj, null, null, null);
		count = videocursor.getCount();
		videolist = (ListView) findViewById(R.id.PhoneVideoList);
		videolist.setAdapter(new VideoAdapter(getApplicationContext()));
		videolist.setOnItemClickListener(videogridlistener);
	}

	private OnItemClickListener videogridlistener = new OnItemClickListener() {
		public void onItemClick(AdapterView parent, View v, int position,
				long id) {
			System.gc();
			video_column_index = videocursor
					.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			videocursor.moveToPosition(position);
			String filename = videocursor.getString(video_column_index);
//			Intent intent = new Intent(VideoStoredInSDCard.this,ViewVideo.class);
//			intent.putExtra("videofilename", filename);
//			startActivity(intent);


//			if(isServiceRunning()){
				hideVideo();
//			}





			Intent intent = new Intent(VideoStoredInSDCard.this,VideoService.class);
//			Intent intent = new Intent(VideoStoredInSDCard.this,DrawOn.class);
			intent.putExtra("videofilename", filename);
			startService(intent);


		}
	};

	public class VideoAdapter extends BaseAdapter {
		private Context vContext;

		public VideoAdapter(Context c) {
			vContext = c;
		}

		public int getCount() {
			return count;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			System.gc();
			ViewHolder holder;
			String id = null;
			convertView = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(vContext).inflate(
						R.layout.listitem, parent, false);
				holder = new ViewHolder();
				holder.txtTitle = (TextView) convertView
						.findViewById(R.id.txtTitle);
				holder.txtSize = (TextView) convertView
						.findViewById(R.id.txtSize);
				holder.thumbImage = (ImageView) convertView
						.findViewById(R.id.imgIcon);

				video_column_index = videocursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
				videocursor.moveToPosition(position);
				id = videocursor.getString(video_column_index);
				video_column_index = videocursor
						.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
				videocursor.moveToPosition(position);
				// id += " Size(KB):" +
				// videocursor.getString(video_column_index);
				holder.txtTitle.setText(id);
				holder.txtSize.setText(" Size(KB):"
						+ videocursor.getString(video_column_index));

				String[] proj = { MediaStore.Video.Media._ID,
						MediaStore.Video.Media.DISPLAY_NAME,
						MediaStore.Video.Media.DATA };
				@SuppressWarnings("deprecation")
				Cursor cursor = managedQuery(
						MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj,
						MediaStore.Video.Media.DISPLAY_NAME + "=?",
						new String[] { id }, null);
				cursor.moveToFirst();
				long ids = cursor.getLong(cursor
						.getColumnIndex(MediaStore.Video.Media._ID));

				ContentResolver crThumb = getContentResolver();
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 1;
				Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(
						crThumb, ids, MediaStore.Video.Thumbnails.MICRO_KIND,
						options);
				holder.thumbImage.setImageBitmap(curThumb);
				curThumb = null;

			} /*
			 * else holder = (ViewHolder) convertView.getTag();
			 */
			return convertView;
		}
	}

	static class ViewHolder {

		TextView txtTitle;
		TextView txtSize;
		ImageView thumbImage;
	}

	public void showVideo(View v){
		if(!isServiceRunning()) {
			startService(new Intent(VideoStoredInSDCard.this, VideoService.class));
		}
		ShowNotification("Click here to Cancel ",false);

	}

	public void hideVideo(){
		Intent stopTrackMapService = new Intent(VideoStoredInSDCard.this, VideoService.class);
		stopService(stopTrackMapService);
		ShowNotification("Click here to Cancel ",true);
	}


	private boolean isServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
			Log.e(TAG, "isServiceRunning: "+service.service.getClassName() );
			if(".video.VideoService".equals(service.service.getClassName())) {

				Log.e(TAG, "isServiceRunningLatlng: True" );
				return true;


			}
		}
		Log.e(TAG, "isServiceRunningLatlng: False" );
		return false;

	}
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
		Bitmap icon_bitmap = BitmapFactory.decodeResource(VideoStoredInSDCard.this.getResources(), R.mipmap.ic_launcher);

		notificationIntent = new Intent(VideoStoredInSDCard.this, HomeActivity.class);

		Log.e("Gcm","From Gcm intent to Pushnotf");

//        Intent stopTrackMapService = new Intent(SplashActivity.this, LockScreenService.class);
//        stopService(stopTrackMapService);

		PendingIntent resultPendingIntent =
				PendingIntent.getActivity(
						VideoStoredInSDCard.this,
						0,
						notificationIntent,
						PendingIntent.FLAG_UPDATE_CURRENT
				);

		android.support.v4.app.NotificationCompat.InboxStyle inboxStyle = new android.support.v4.app.NotificationCompat.InboxStyle();

		android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(
				VideoStoredInSDCard.this);
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

	@Override
	protected void onPause() {
//		hideVideo();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
//		hideVideo();
		super.onDestroy();
	}

	public void stopv(View v)
	{
		Intent stopTrackMapService = new Intent(VideoStoredInSDCard.this, VideoService.class);
		stopService(stopTrackMapService);
	}
}