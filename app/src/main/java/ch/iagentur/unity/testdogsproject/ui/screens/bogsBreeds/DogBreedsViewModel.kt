package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import androidx.lifecycle.*
import ch.iagentur.unity.testdogsproject.data.network.DogBreed
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.network.Resource
import ch.iagentur.unity.testdogsproject.network.map
import ch.iagentur.unity.testdogsproject.misc.test.EspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DogBreedsViewModel @Inject constructor(
    private val repositoryRetriever: RepositoryRetriever
) : ViewModel() {
    companion object {
        const val DEFAULT_PAGE = 0
    }

    var page: Int = 0
    var localDogBreeds: MutableList<DogBreed> = mutableListOf()
    var updateDogBreedsLiveData = MutableLiveData<String>()

    val dogBreedsLiveData: LiveData<Resource<List<DogBreed>?>> =
        Transformations.switchMap(updateDogBreedsLiveData) {
            return@switchMap liveData {
                emitSource(
                    repositoryRetriever.getDogBreeds(page).flowOn(Dispatchers.IO).map { resource ->
                        if (resource.status == Resource.Status.SUCCESS) {
                            page++
                            if (resource.data != null) {
                                localDogBreeds.addAll(resource.data.toMutableList())
                                return@map resource.map { localDogBreeds }
                            }
                        }
                        return@map resource
                    }.asLiveData()
                )
            }
        }

    override fun onCleared() {
        super.onCleared()
        localDogBreeds.clear()
    }

    fun updateDogBreeds() {
        if (EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.increment()
        }
        updateDogBreedsLiveData.postValue("updateClients")
    }

    fun resetPage() {
        page = DEFAULT_PAGE
    }
}

