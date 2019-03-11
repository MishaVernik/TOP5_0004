package kqb.das.awkie.schedule

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.PendingIntent.getActivity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings
import androidx.core.app.NotificationCompat
import kqb.das.awkie.R
import kqb.das.awkie.schedule.activities.CatchDeepLinkActivity

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val requestCode = intent?.getIntExtra(AlarmManager.NotificationType.NOTIFICATION_EXTRA_CODE, -1) ?: -1

        val resultIntent = Intent(context, CatchDeepLinkActivity::class.java)
        resultIntent.putExtra(AlarmManager.NotificationType.NOTIFICATION_EXTRA_CODE, requestCode)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val resultPendingIntent = getActivity(context, requestCode, resultIntent, FLAG_UPDATE_CURRENT)

        val mBuilder = NotificationCompat.Builder(context!!, "app_mini")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(resultPendingIntent)

        context?.let {
            mBuilder.setContentTitle(it.getString(R.string.app_name))
            mBuilder.setStyle(NotificationCompat.BigTextStyle().bigText(getText(context, requestCode)))
        }

        val am = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = System.currentTimeMillis() / 1000

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel("app_mini", "NOTIFICATION_CHANNEL_NAME", importance)
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationChannel.lightColor = Color.RED
            mBuilder.setChannelId("app_mini")
            am.createNotificationChannel(notificationChannel)
        }

        am.notify(id.toInt(), mBuilder.build())
    }

    private fun getText(context: Context, code: Int): String {
        return when (code) {
            1 -> context.getString(R.string.asds_1)
            2 -> context.getString(R.string.ssaf_242)
            3 -> context.getString(R.string.saf_3)
            4 -> context.getString(R.string.staa_4)
            5 -> context.getString(R.string.s_5)
            6 -> context.getString(R.string.ssdv_6)
            else -> context.getString(R.string.sasd_7324)
        }
    }
}