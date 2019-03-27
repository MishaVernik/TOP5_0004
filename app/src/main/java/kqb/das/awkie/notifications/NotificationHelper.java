package kqb.das.awkie.notifications;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import kqb.das.awkie.R;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotificationHelper {

    static final String TEXT_EXTRA = "text";
    static final String TYPE_EXTRA = "type";

    private int [] texts = {R.string.text_1, R.string.text_2, R.string.text_3, R.string.text_4, R.string.text_5, R.string.text_6, R.string.text_7, R.string.text_9};

    public void schedule(Context context){
        Calendar calendar = Calendar.getInstance();

        int n = 0;
        for (int i = 0; i < 30; i++) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            if (i <= 7) {
                calendar.add(Calendar.DAY_OF_WEEK, i);
                calendar.set(Calendar.HOUR_OF_DAY, 20);
                calendar.set(Calendar.MINUTE, 0);
                scheduleNotification(calendar, context, context.getString(texts[i]), i);
            }
            if (i >= 9){
                calendar.add(Calendar.DAY_OF_WEEK, i + (2 * n));
                calendar.set(Calendar.HOUR_OF_DAY, 20);
                calendar.set(Calendar.MINUTE, 0);
                scheduleNotification(calendar, context, context.getString(texts[7]), i);
                n++;
            }
        }
    }

    private void scheduleNotification(Calendar calendar, Context context, String text, int type) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(TEXT_EXTRA, text);
        intent.putExtra(TYPE_EXTRA, type);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, type, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManagerRTC = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManagerRTC.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

}
