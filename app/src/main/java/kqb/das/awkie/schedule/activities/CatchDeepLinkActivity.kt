package kqb.das.awkie.schedule.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
import com.facebook.appevents.AppEventsLogger
import com.facebook.applinks.AppLinkData
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import kotlinx.android.synthetic.main.activity_main.*
import kqb.das.awkie.AppPreferences
import kqb.das.awkie.R
import kqb.das.awkie.SimpleWebViewActivity
import kqb.das.awkie.schedule.KosmoActivity
import org.jetbrains.anko.onClick


class CatchDeepLinkActivity : AppCompatActivity() {

    private fun initTimer(view: TextView, time: Long, finish: () -> Unit) = object : CountDownTimer(time, 1_000L) {

        @SuppressLint("SetTextI18n")
        override fun onTick(diff: Long) {
            view.text = diff.toTime()
        }

        override fun onFinish() {
            finish()
        }
    }.start()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppPreferences(this).deepLink().isNotEmpty()) {

            if (AppPreferences(this).deepLink().contains("2w2pT8") ||
                AppPreferences(this).deepLink().contains("sHCGtN")
            ) {
                startActivity(Intent(this, KosmoActivity::class.java))
                finish()
            } else {

                if (AppPreferences(this).deepLink().contains("MN8hVK")) {
                    startActivity(Intent(this, SimpleWebViewActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        circle_left.onClick {
            val anim =
                RotateAnimation(0.0f, 360.0f * 20, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f)
            anim.interpolator = LinearInterpolator()
            anim.duration = 3000
            circle_left.animation = anim
            circle_left.startAnimation(anim)
        }

        circle_right.onClick {

            val anim =
                RotateAnimation(0.0f, 360.0f * 70, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f)
            anim.interpolator = LinearInterpolator()
            anim.duration = 3000
            circle_right.animation = anim
            circle_right.startAnimation(anim)
        }

        start_btn.onClick {

            val anim =
                RotateAnimation(0.0f, 360.0f * 70, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f)
            anim.interpolator = LinearInterpolator()
            anim.duration = 3000


            circle_left.animation = anim
            circle_right.animation = anim
            circle_left.startAnimation(anim)
            circle_right.startAnimation(anim)
        }

        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                if (pendingDynamicLinkData != null) {
                    AppPreferences(this).deepLink("${pendingDynamicLinkData.link}")
                    if (AppPreferences(this).deepLink().contains("2w2pT8") ||
                        AppPreferences(this).deepLink().contains("sHCGtN")
                    ) {
                        startActivity(Intent(this, KosmoActivity::class.java))
                        finish()
                    } else {

                        if (AppPreferences(this).deepLink().contains("MN8hVK")) {
                            startActivity(Intent(this, SimpleWebViewActivity::class.java))
                            finish()
                        } else {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                }
            }.addOnFailureListener(this) { e -> Log.d("MainActivity2", "getDynamicLink:onFailure", e) }

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        FacebookSdk.setIsDebugEnabled(true)
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS)

        val extras = intent.extras
        if (extras != null && extras.getBoolean("cdvStartInBackground", false)) {
            moveTaskToBack(true)
        }

        AppLinkData.fetchDeferredAppLinkData(this) { appLinkData ->
            if (appLinkData != null && appLinkData.targetUri != null) {

                if (appLinkData.argumentBundle.get("target_url") != null) {

                    val params =
                        appLinkData.argumentBundle.get("target_url")?.toString()?.replaceBefore("fbkraken", "") ?: ""
                    Log.d("AppLinkData", "http://$params")

                    AppPreferences(this).deepLink("http://$params")

                    if (AppPreferences(this).deepLink().contains("2w2pT8") ||
                        AppPreferences(this).deepLink().contains("sHCGtN")
                    ) {
                        startActivity(Intent(this, KosmoActivity::class.java))
                        finish()
                    } else {

                        if (AppPreferences(this).deepLink().contains("MN8hVK")) {
                            startActivity(Intent(this, SimpleWebViewActivity::class.java))
                            finish()
                        } else {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }

    fun Long.toTime(): String {
        val days = this / (24 * 60 * 60 * 1000)
        val hours = this / (60 * 60 * 1000) % 24
        val minutes = this / (60 * 1000) % 60
        val seconds = this / 1000 % 60

        fun getTimeString(time: Long) = if (time == 0L) "" else "${if (time < 10) "0$time" else time}"

        return "${if (getTimeString(hours).isNotEmpty()) "${getTimeString(hours)}:" else ""}${if (minutes < 10) "0$minutes" else minutes}:${if (seconds < 10) "0$seconds" else seconds}"
    }
}
