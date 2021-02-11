package ch.iagentur.unity.testdogsproject.ui.screens.dogBreedsInfo

import androidx.lifecycle.*
import ch.iagentur.unity.testdogsproject.data.network.DogBreedInfo
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.network.Resource
import ch.iagentur.unity.testdogsproject.misc.test.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DogBreedInfoViewModel @Inject constructor(
    private val repositoryRetriever: RepositoryRetriever
) : ViewModel() {
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
        if (EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.increment()
        }
        this.id = id
        updateDogBreedsLiveData.postValue("updateClients")
    }
}