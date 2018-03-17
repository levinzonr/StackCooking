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
    var previousItemCount = 10


    interface InfiniteScrollCallbacks {
        fun onLoadMore(pageToLoad: Int)
    }

    companion object {
        const val TAG = "InfiniteScrollListener"
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemsCount = linearLayoutManager.itemCount
        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

        if (!isLoading && totalItemsCount == lastVisibleItem + 1 && totalItemsCount != 1) {
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