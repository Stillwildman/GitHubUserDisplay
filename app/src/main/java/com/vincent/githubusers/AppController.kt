package com.vincent.githubusers

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

/**
 * <h1>AppController</h1>
 * <p>
 * 提供在App生命週期裡一定會存在的Application instance，<br>
 * 可以在任何地方取得此instance來存取resources或functions，可減少使用Activity context。
 * </p>
 * 任何App共通都能做的function會放在這裡，例如開啟外部瀏覽器 or 隱藏軟體鍵盤等
 */
class AppController : MultiDexApplication() {

    companion object {
        @get:Synchronized
        lateinit var instance: AppController
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun openWebWithBrowser(webUrl: String?) {
        if (webUrl != null && webUrl.startsWith("http")) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse(webUrl)

            startActivity(intent)
        }
    }

    fun hideKeyboard(view: View) {
        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
