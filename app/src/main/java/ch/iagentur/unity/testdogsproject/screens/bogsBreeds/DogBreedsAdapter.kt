package ch.iagentur.unity.testdogsproject.screens.bogsBreeds

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.viewHolder.DogBreedsViewHolder

class DogBreedsAdapter(val context: Context, val items: List<DogBreed>) :
    RecyclerView.Adapter<DogBreedsViewHolder>() {
    var openDogBreedInfoCallback: ((id: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogBreedsViewHolder {
        return DogBreedsViewHolder(context, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DogBreedsViewHolder, position: Int) {
        holder.openDogBreedInfoCallback = openDogBreedInfoCallback
        holder.bindView(items[position])
    }

}