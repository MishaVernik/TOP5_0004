package kqb.das.awkie.schedule

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.kosmo_activity.*
import kqb.das.awkie.AppPreferences
import kqb.das.awkie.R
import kqb.das.awkie.notifications.NotificationHelper
import kqb.das.awkie.schedule.utils.Constants
import kotlin.concurrent.thread

class KosmoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kosmo_activity)

        thread {
            SystemClock.sleep(3000)
            runOnUiThread {
                web_view.visibility = View.VISIBLE
                load_view.visibility = View.GONE
            }
        }

        val prefs = AppPreferences(this)
        if (prefs.pusIsSet()){
            NotificationHelper().schedule(this)
            prefs.pusIsSet(false)
        }

        verifyStoragePermissions(this)

        web_view.settings.javaScriptEnabled = true
        if (Build.VERSION.SDK_INT >= 21) {
            web_view.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        web_view.loadUrl(AppPreferences(this).deepLink())

        web_view.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return if ((url ?: "").contains("auth/success")) {
                    web_view.loadUrl(AppPreferences(this@KosmoActivity).deepLink())
                    super.shouldOverrideUrlLoading(view, url)
                } else super.shouldOverrideUrlLoading(view, url)
            }

            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                web_view.visibility = View.GONE
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView, req: WebResourceRequest, rerr: WebResourceError) {
                onReceivedError(view, rerr.errorCode, rerr.description.toString(), req.url.toString())
            }
        }
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            finishAffinity()
        }
    }

    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private val REQUEST_EXTERNAL_STORAGE = 1

    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have read or write permission
        val writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
        val cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED || cameraPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}