package kqb.das.awkie.schedule

import android.os.SystemClock
import kqb.das.awkie.AppPreferences
import kqb.das.awkie.schedule.activities.MainActivity
import kqb.das.awkie.schedule.utils.Constants
import kotlin.concurrent.thread

class Utils {
    fun initAlarms(actuvuty: MainActivity) {
        val prefs = AppPreferences(actuvuty)

        if (!prefs.pusIsSet()) {
            thread {
                (1..62).map {
                    SystemClock.sleep(2000)
                    actuvuty.runOnUiThread {
                        if (it == 62)
                            prefs.pusIsSet(true)
                        AlarmManager(actuvuty).createNotification(it, (Constants.HOURS_24 * it))
                    }
                }
            }
        }
    }

}