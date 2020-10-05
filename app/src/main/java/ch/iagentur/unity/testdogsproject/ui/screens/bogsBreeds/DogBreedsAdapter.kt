package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseAdapter
import ch.iagentur.unity.testdogsproject.ui.viewHolder.DogBreedsViewHolder

class DogBreedsAdapter(val context: Context, items: MutableList<DogBreed?> = mutableListOf()) :
    BaseAdapter<DogBreed>(items) {
    var openDogBreedInfoCallback: ((id: Int) -> Unit)? = null

    companion object {
        const val DOG_BREEDS = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DOG_BREEDS) {
            DogBreedsViewHolder(context, parent)
        } else {
            super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DogBreedsViewHolder) {
            val bogBreed = items[position]
            holder.openDogBreedInfoCallback = openDogBreedInfoCallback
            holder.bindView(bogBreed)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] != null) {
            DOG_BREEDS
        } else {
            super.getItemViewType(position)
        }
    }
}