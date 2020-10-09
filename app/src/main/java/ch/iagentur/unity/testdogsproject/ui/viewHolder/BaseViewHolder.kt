package ch.iagentur.unity.testdogsproject.ui.viewHolder

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import ch.iagentur.unity.testdogsproject.misc.extension.inflate

open class BaseViewHolder(layoutResId: Int, parent: ViewGroup)
    : RecyclerView.ViewHolder(parent.context.inflate(layoutResId, parent)) {

    open fun unbind() {}
}
