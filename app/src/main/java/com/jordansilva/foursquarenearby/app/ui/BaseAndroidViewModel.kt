package com.jordansilva.foursquarenearby.app.ui

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private val asyncJobs: MutableList<Job> = mutableListOf()

    @CallSuper
    @Synchronized
    protected fun launchAsync(block: suspend CoroutineScope.() -> Unit) {
        val job: Job = launch(UI) { block() }
        asyncJobs.add(job)
        job.invokeOnCompletion { asyncJobs.remove(job) }
    }

    @CallSuper
    @Synchronized
    protected fun cancelAllAsync() {
        val asyncJobsSize = asyncJobs.size

        if (asyncJobsSize > 0) {
            for (i in asyncJobsSize - 1 downTo 0) {
                asyncJobs[i].cancel()
            }
        }
    }

    @CallSuper
    @Synchronized
    open fun cleanup() {
        cancelAllAsync()
    }

    override fun onCleared() {
        cleanup()
        super.onCleared()
    }
}