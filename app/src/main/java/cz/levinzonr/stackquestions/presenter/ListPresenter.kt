package cz.levinzonr.stackquestions.presenter

import android.util.Log
import cz.levinzonr.stackquestions.MyApplication
import cz.levinzonr.stackquestions.persistence.CacheProvider
import cz.levinzonr.stackquestions.persistence.StackClient
import cz.levinzonr.stackquestions.model.QuestionResponce
import cz.levinzonr.stackquestions.screens.questionslist.QuestionListFragment
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

/**
 * Created by nomers on 3/15/18.
 */
class ListPresenter : Presenter<QuestionListFragment> {

    private var view: QuestionListFragment? = null
    private var subscription: Subscription? = null
    private lateinit var item : QuestionResponce

    companion object {
        const val TAG = "ListPresenter"
    }

    override fun attachView(view: QuestionListFragment) {
        this.view = view
    }

    fun getQuestionsPage(pageToLoad: Int){
        subscription?.unsubscribe()
        val app = MyApplication.fromContext(view!!.context)
        val cache = CacheProvider(view!!.context)
        if (cache.cachedData != null) {
            Log.d(TAG, cache.cachedData.toString())
        }

        if (cache.cachedData != null && cache.cachedData!!.latstPage >= pageToLoad) {
            if (!cache.timeToUpdate(System.currentTimeMillis())) {
                Log.d(TAG, "restoreFromCache")
                view?.restoreFromCache(cache.cachedData!!)
                return
            }
            Log.d(TAG, "Outdated cache")
            cache.clear()
        }

        view?.onLoadingStarted()
        subscription = StackClient.instance().fetchQuestionsPage(pageToLoad)
                .subscribeOn(app.defaultScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<QuestionResponce>() {
                    override fun onNext(result: QuestionResponce?) {
                        if (result != null) {
                            Log.d(TAG, "Loading succes: $result")
                            Thread({
                                cache.updateCache(pageToLoad, result)
                            }).start()
                            item = result
                        } else Log.d(TAG, "Result null")
                    }

                    override fun onCompleted() {
                        view?.onLoadingFinished(item)
                        Log.d(TAG, "onCompleted")
                    }

                    override fun onError(e: Throwable?) {
                        view?.onError()
                        Log.d(TAG, "oError: $e")
                    }
                })
    }

    override fun detachView() {
        view = null
        subscription?.unsubscribe()

    }
}