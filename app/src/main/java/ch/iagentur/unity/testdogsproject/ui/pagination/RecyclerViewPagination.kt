package ch.iagentur.unity.testdogsproject.ui.pagination

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseAdapter

class RecyclerViewPagination(recyclerView: RecyclerView, val callback:()-> Unit) {
    var isLoading: Boolean = false
    var isLastPage: Boolean = false
    var adapter: BaseAdapter<*> = recyclerView.adapter as BaseAdapter<*>
    var layoutManager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

    private val recyclerViewOnScrollListener = object : OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            recyclerView.let {
                initiateLoadingMore()
            }
        }
    }

    init {
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun initiateLoadingMore() {
        layoutManager.let {
            val visibleItemCount = it.childCount
            val totalItemCount = it.itemCount
            val firstVisibleItemPosition = it.findFirstVisibleItemPosition()

            if (!isLoading && !isLastPage) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE
                ) {
                    startLoadMoreItems()
                }
            }
        }
    }

    fun reset() {
        isLoading = false
        isLastPage = false
    }

    private fun startLoadMoreItems() {
        isLoading = true
        adapter.addProgressRow()
        callback()
    }

    fun finishLoadMoreItems() {
        isLoading = false
        adapter.removeProgressRow()
    }

    fun lastPageLoaded() {
        isLastPage = true
        isLoading = false
        adapter.removeProgressRow()
    }
}