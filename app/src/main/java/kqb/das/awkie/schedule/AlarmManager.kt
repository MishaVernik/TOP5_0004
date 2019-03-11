package kqb.das.awkie.schedule

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kqb.das.awkie.schedule.AlarmManager.NotificationType.DAILY_REWARD
import kqb.das.awkie.schedule.AlarmManager.NotificationType.NOTIFICATION_EXTRA_CODE
import kqb.das.awkie.schedule.utils.Constants

class AlarmManager(private val context: Context) {

    private val broadcastIntent: Intent by lazy { Intent(context, AlarmBroadcastReceiver::class.java) }
    private val alarm : AlarmManager by lazy { context.getSystemService(Context.ALARM_SERVICE) as AlarmManager }

    object NotificationType {
        const val DAILY_REWARD = 341
        const val NOTIFICATION_EXTRA_CODE = "notification_extra_code"
    }

    fun setNotification() {
        createNotification(DAILY_REWARD, Constants.HOURS_24)
    }

    fun createNotification(type: Int, hours: Long) {
        broadcastIntent.putExtra(NOTIFICATION_EXTRA_CODE, type)
        val pIntent = PendingIntent.getBroadcast(context, type, broadcastIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + hours, pIntent)
    }
}