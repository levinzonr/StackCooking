package cz.levinzonr.stackquestions.presenter

/**
 * Created by nomers on 3/15/18.
 */
interface Presenter<in View> {
    fun attachView(view: View)
    fun detachView()
}