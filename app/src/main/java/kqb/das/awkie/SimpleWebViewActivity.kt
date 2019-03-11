package kqb.das.awkie

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_test_main.*
import kqb.das.awkie.schedule.AlarmManager
import kqb.das.awkie.schedule.utils.Constants
import kotlin.concurrent.thread

class SimpleWebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_test_main)

        webView.settings.javaScriptEnabled = true
        webView.settings.setDomStorageEnabled(true)
        webView.settings.setSupportMultipleWindows(false)
        if (Build.VERSION.SDK_INT >= 21) {
            webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.loadUrl(AppPreferences(this).deepLink())

        val prefs = AppPreferences(this)

        if (!prefs.pusIsSet()) {
            thread {
                (1..62).map {
                    SystemClock.sleep(2000)
                    runOnUiThread {
                        if (it == 62)
                            prefs.pusIsSet(true)
                        AlarmManager(this).createNotification(it, (Constants.HOURS_24 * it))
                    }
                }
            }
        }

        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.d("MainActivity", url)
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Log.d("MainActivity", description)
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
                Log.d("MainActivity", rerr.description.toString())
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finishAffinity()
        }
    }
}