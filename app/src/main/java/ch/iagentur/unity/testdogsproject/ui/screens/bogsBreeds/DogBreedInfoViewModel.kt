package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DogBreedInfoViewModel @Inject constructor(
    private val repositoryRetriever: RepositoryRetriever
) {
    var updateDogBreedsLiveData = MutableLiveData<String>()
    var id = 0

    val dogBreedInfoLiveData: LiveData<Resource<List<DogBreedInfo>?>> = liveData {
        emitSource(
            repositoryRetriever.getDogBreedInfo(id).flowOn(Dispatchers.IO).map { list ->
                return@map list
            }.asLiveData()
        )
    }

    fun updateDogBreeds(id: Int) {
        this.id = id
        updateDogBreedsLiveData.postValue("updateClients")
    }
}