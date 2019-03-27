package kqb.das.awkie.schedule

import android.os.SystemClock
import kqb.das.awkie.AppPreferences
import kqb.das.awkie.notifications.NotificationHelper
import kqb.das.awkie.schedule.activities.MainActivity
import kqb.das.awkie.schedule.utils.Constants
import kotlin.concurrent.thread

class Utils {
    fun initAlarms(actuvuty: MainActivity) {
        val prefs = AppPreferences(actuvuty)
        if (prefs.pusIsSet()){
            NotificationHelper().schedule(actuvuty)
            prefs.pusIsSet(false)
        }
        }
    }