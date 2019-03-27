package kqb.das.awkie

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kqb.das.awkie.schedule.activities.SuccessDeepLinkActivity

class AppPreferences(private val context: Context) {
  private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

  private val PUSH_SETTED = "PUSH_SETTED"
  private val ATTACH_TO_DEEP_LINK = "ATTACH_TO_DEEP_LINK"

  fun pusIsSet(isOpened: Boolean) = prefs.edit().putBoolean(PUSH_SETTED, isOpened).apply()

  fun pusIsSet() = prefs.getBoolean(PUSH_SETTED, true)

  fun deepLink(url: String) = prefs.edit().putString(ATTACH_TO_DEEP_LINK, url).apply()

  fun deepLink() = prefs.getString(ATTACH_TO_DEEP_LINK, "")

}