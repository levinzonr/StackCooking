package cz.levinzonr.stackquestions.screens.viewutils

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by nomers on 3/16/18.
 */
abstract class InfiniteScrollListener(val linearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    var currentPage: Int = 1
    var isLoading: Boolean = false
    var previousItemCount = 10


    companion object {
        const val TAG = "InfiniteScrollListener"
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        Log.d(TAG, "onScrolled: X: $dx, Y: $dy\nManager: ${linearLayoutManager.findLastVisibleItemPosition()}")

        val totalItemsCount = linearLayoutManager.itemCount
        val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

        if (!isLoading && totalItemsCount == lastVisibleItem + 1 && totalItemsCount != 1) {
            Log.d(TAG, "-->LoadTime?")
            isLoading = true
            loadNext(currentPage++)
        }

        if (previousItemCount < totalItemsCount) {
            isLoading = false
            currentPage++
            previousItemCount = totalItemsCount
        }
    }

    abstract fun loadNext(pageToLoad: Int)
}