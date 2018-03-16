package cz.levinzonr.stackquestions.screens

import cz.levinzonr.stackquestions.persistence.CacheProvider

/**
 * Created by nomers on 3/15/18.
 */
interface ViewCallBacks<in Item> {

    fun onLoadingStarted()

    fun restoreFromCache(data: CacheProvider.CachedData)

    fun onLoadingFinished(result: Item)

    fun onError()
}