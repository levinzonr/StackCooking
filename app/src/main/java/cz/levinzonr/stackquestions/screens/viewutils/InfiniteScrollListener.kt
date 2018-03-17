package cz.levinzonr.stackquestions.screens.viewutils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by nomers on 3/16/18.
 */
class InfiniteScrollListener(
        val callbacks: InfiniteScrollCallbacks,
        val linearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener(){
    var isLoading: Boolean = false
    var currentPage: Int = 1
    var previousItemCount = 5


    interface InfiniteScrollCallbacks {
        fun onLoadMore(pageToLoad: Int)
    }

    companion object {
        const val TAG = "InfiniteScrollListener"
        const val STATIC_ELEMENTS_CNT = 2
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemsCount = linearLayoutManager.itemCount
        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

        if (!isLoading && totalItemsCount <= lastVisibleItem + 4
                && totalItemsCount != STATIC_ELEMENTS_CNT) {
            currentPage ++
            Log.d(TAG, "-->LoadTime? $currentPage")
            isLoading = true
            callbacks.onLoadMore(currentPage)
        }

        if (previousItemCount < totalItemsCount) {
            isLoading = false
            previousItemCount = totalItemsCount
        }
    }
}