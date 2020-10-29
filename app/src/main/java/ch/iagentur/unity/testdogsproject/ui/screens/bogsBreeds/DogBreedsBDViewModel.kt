package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
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


    private val mapper = DogBreedMapper()

    fun initLoading() {
        GlobalScope.launch { repositoryRetriever.loadBreeds() }
    }

    val dogBreedsLiveData: LiveData<List<DogBreed>> = liveData {
        val bdFlow = repositoryRetriever.getBdFlow() ?: return@liveData
        emitSource(bdFlow.flowOn(Dispatchers.IO).map {
            val listRes = mutableListOf<DogBreed>()
            it.forEach { dogBreedAllPageEntity ->
                Log.d(
                    "DogBreedsBDViewModel", dogBreedAllPageEntity.page
                )
                listRes.addAll(mapper.map(dogBreedAllPageEntity))
            }
            return@map listRes
        }.asLiveData())
    }
}



