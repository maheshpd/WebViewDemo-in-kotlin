package com.example.targetvideodemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoWeb.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        videoWeb.settings.javaScriptEnabled = true
        videoWeb.loadUrl("http://exam.targeteducare.com/Home/Subject/1008")
        videoWeb.settings.allowFileAccess = true
        videoWeb.settings.setAppCacheEnabled(true)
        videoWeb.webChromeClient = ChromeClient()
    }

    inner class ChromeClient: WebChromeClient() {
        var mCustomeView: View?= null
        var mCustomeViewCallback: WebChromeClient.CustomViewCallback?= null
        var mFullscreenContainer: FrameLayout? = null
        var mOriginalOrientation: Int?= null
        var mOriginalSystemUIVisibility: Int?= null


        override fun onHideCustomView() {
            (window.decorView as FrameLayout).removeView(this.mCustomeView)
            this.mCustomeView = null
            window.decorView.systemUiVisibility = this.mOriginalSystemUIVisibility!!
            requestedOrientation = mOriginalOrientation!!
            this.mCustomeViewCallback!!.onCustomViewHidden()
            this.mCustomeViewCallback = null
        }

        override fun getDefaultVideoPoster(): Bitmap? {
            return if (mCustomeView == null) {
                null
            } else BitmapFactory.decodeResource(applicationContext.resources, 2130837573)
        }


        override fun onShowCustomView(
            paramView: View,
            paramCustomViewCallback: CustomViewCallback
        ) {
            if (this.mCustomeView != null) {
                onHideCustomView()
                return
            }
            this.mCustomeView = paramView
            this.mOriginalSystemUIVisibility = window.decorView.systemUiVisibility
            mOriginalOrientation = requestedOrientation
            this.mCustomeViewCallback = paramCustomViewCallback
            (window.decorView as FrameLayout).addView(
                this.mCustomeView,
                FrameLayout.LayoutParams(-1, -1)
            )
            window.decorView.systemUiVisibility = 3846 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }


    }



}
