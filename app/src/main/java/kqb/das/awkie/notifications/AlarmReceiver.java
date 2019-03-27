package kqb.das.awkie.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.app.NotificationCompat;
import kqb.das.awkie.R;
import kqb.das.awkie.schedule.activities.CatchDeepLinkActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String text = intent.getStringExtra(NotificationHelper.TEXT_EXTRA);
        int type = intent.getIntExtra(NotificationHelper.TYPE_EXTRA, 0);

        Intent intentToRepeat = new Intent(context, CatchDeepLinkActivity.class);
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, type, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager nm = new NotificationHelper().getNotificationManager(context);
        Notification notification = buildNotification(context, pendingIntent, nm, text).build();
        nm.notify(type, notification);
    }

    public NotificationCompat.Builder buildNotification(Context context, PendingIntent pendingIntent, NotificationManager nm, String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "Daily Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Daily Notification");
            if (nm != null) {
                nm.createNotificationChannel(channel);
            }
        }

        String title = Utils.removeAfterWord(text,"\n");
        String content = Utils.removeTillWord(text,"\n");


//        Bitmap bitmap =  BitmapFactory.decodeResource(context.getResources(), R.drawable.img);

        return new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(content)
//                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle(title))
                .setAutoCancel(true);
    }
}
