package cz.levinzonr.stackquestions

import android.app.Application
import android.content.Context
import rx.Scheduler
import rx.schedulers.Schedulers

/**
 * Created by nomers on 3/16/18.
 */
class MyApplication : Application() {
    private var scheduler: Scheduler? = null

    companion object {
        fun fromContext(context: Context) : MyApplication {
            return context.applicationContext as MyApplication
        }
    }

    fun defaultScheduler() : Scheduler {
        if (scheduler == null) {
            scheduler = Schedulers.io()
        }
        return scheduler!!
    }

}
