package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import ch.iagentur.unity.testdogsproject.bd.DogBreedAllPageEntity
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.model.mapper.DogBreedMapper
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class DogBreedsBDViewModel @Inject constructor(
    private val repositoryRetriever: RepositoryRetriever
) : ViewModel() {
val mapper = DogBreedMapper()
    fun initLoading() {
        GlobalScope.launch(Dispatchers.IO) {
            repositoryRetriever.loadDogBreeds()
        }
    }

    val dogBreedsLiveData: LiveData<List<DogBreed>?> = liveData {
        val allDogBreeds = repositoryRetriever.getAllDogBreeds() ?: return@liveData
        emitSource(allDogBreeds.flowOn(Dispatchers.IO).map { dogBreedsList ->
            val listRes = mutableListOf<DogBreed>()
            dogBreedsList.sortedBy { it.page.toInt() }.forEach {
                Log.d("DogBreedsBDViewModel",it.page.toString())
                listRes.addAll(mapper.map(it)) }
            return@map listRes
        }.asLiveData())
    }
}

