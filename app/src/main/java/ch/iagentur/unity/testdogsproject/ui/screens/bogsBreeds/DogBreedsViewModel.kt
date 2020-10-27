package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import androidx.lifecycle.*
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.network.Resource
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
    var updateDogBreedsLiveData = MutableLiveData<String>()

    val dogBreedsLiveData: LiveData<Resource<List<DogBreed>?>> =
        Transformations.switchMap(updateDogBreedsLiveData) {
            return@switchMap liveData {
                emitSource(
                    repositoryRetriever.getDogBreeds(page).flowOn(Dispatchers.IO).map { list ->
                        if (list.status == Resource.Status.SUCCESS) {
                            page++
                        }
                        return@map list
                    }.asLiveData()
                )
            }
        }
    fun updateDogBreeds() {
        updateDogBreedsLiveData.postValue("updateClients")
    }
    fun resetPage() {
        page = DEFAULT_PAGE
    }
}

