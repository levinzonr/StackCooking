package cz.levinzonr.stackquestions.presenter

import android.util.Log
import cz.levinzonr.stackquestions.api.StackClient
import cz.levinzonr.stackquestions.model.QuestionResponce
import cz.levinzonr.stackquestions.screens.ViewCallBacks
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by nomers on 3/15/18.
 */
class ListPresenter : Presenter<ViewCallBacks<QuestionResponce>>, Subscriber<QuestionResponce>() {

    private var view: ViewCallBacks<QuestionResponce>? = null
    private var subscription: Subscription? = null
    private lateinit var item : QuestionResponce

    companion object {
        const val TAG = "ListPresenter"
    }

    override fun attachView(view: ViewCallBacks<QuestionResponce>) {
        this.view = view
    }

    fun getQuestionsPage(){
        subscription?.unsubscribe()
        view?.onLoadingStarted()
        subscription = StackClient.instance().fetchQuestionsPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this)
    }

    override fun detachView() {
        view = null
        subscription?.unsubscribe()

    }

    override fun onNext(result: QuestionResponce?) {
        if (result != null) {
            Log.d(TAG, "Loading succes: $result")
            this.item = result
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
}