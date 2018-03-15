package cz.levinzonr.stackquestions.screens

/**
 * Created by nomers on 3/15/18.
 */
interface ViewCallBacks<in Item> {

    fun onLoadingStarted()

    fun onLoadingFinished(result: Item)

    fun onError()
}