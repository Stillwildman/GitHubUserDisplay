package com.vincent.githubusers.database

import android.os.AsyncTask
import com.vincent.githubusers.callbacks.OnProcessingCallback

/**
 * Created by Vincent on 2020/9/28.
 */
class DBAsyncTask(private val processingCallback: OnProcessingCallback) : AsyncTask<Void, Void, Boolean>() {

    init {
        this.execute()
    }

    override fun doInBackground(vararg params: Void?): Boolean {
        return processingCallback.onProcessing()
    }

    override fun onPostExecute(result: Boolean) {
        processingCallback.onDone(result)
    }
}