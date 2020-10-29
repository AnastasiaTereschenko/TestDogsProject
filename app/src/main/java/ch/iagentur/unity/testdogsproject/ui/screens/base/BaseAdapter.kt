package ch.iagentur.unity.testdogsproject.ui.screens.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.iagentur.unity.testdogsproject.ui.viewHolder.ProgressViewHolder

abstract class BaseAdapter<T>(var items: MutableList<T?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val PROGRESS_ITEM_TYPE = Int.MAX_VALUE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProgressViewHolder(parent)
    }

    fun refreshData(newItems: MutableList<T?>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun addPage(pageList: List<T?>) {
        items.clear()
        items.addAll(pageList)
        notifyDataSetChanged()
    }



    fun addProgressRow() {
        val itemInsertPosition = itemCount
        if (!items.contains(null)) {
            items.add(null)
            notifyItemInserted(itemInsertPosition)
        }
    }

    fun removeProgressRow() {
        val lastPosition = itemCount - 1
        if (lastPosition >= 0) {
            items.removeAt(lastPosition)
            notifyItemRemoved(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        //return if (getItemViewType(position) == PROGRESS_ITEM_TYPE) PROGRESS_ITEM_TYPE.toLong()
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return PROGRESS_ITEM_TYPE
    }
}