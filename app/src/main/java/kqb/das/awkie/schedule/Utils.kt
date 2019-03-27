package kqb.das.awkie.schedule

import kqb.das.awkie.AppPreferences
import kqb.das.awkie.notifications.NotificationHelper
import kqb.das.awkie.schedule.activities.MainActivity


class Utils {
    fun initAlarms(actuvuty: MainActivity) {
        val prefs = AppPreferences(actuvuty)
        if (prefs.pusIsSet()){
            NotificationHelper().schedule(actuvuty)
            prefs.pusIsSet(false)
        }
    }
}