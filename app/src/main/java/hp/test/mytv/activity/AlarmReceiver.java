package hp.test.mytv.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import hp.test.mytv.R;

public class AlarmReceiver extends BroadcastReceiver {
    private  final  String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
        CharSequence name = "Personal Notifications";
        String description = "Include all the Personal Notifications";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);

        notificationChannel.setDescription(description);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_notifications_black_24dp);
            builder.setContentTitle("THE TV SHOWS");
            builder.setContentText("Don't Miss Your Favorite Shows Today");

            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
        }

    }


}
