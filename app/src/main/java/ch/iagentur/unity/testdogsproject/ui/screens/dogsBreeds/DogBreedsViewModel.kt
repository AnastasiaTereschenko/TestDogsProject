package ch.iagentur.unity.testdogsproject.ui.screens.dogsBreeds

import android.util.Log
import androidx.lifecycle.*
import ch.iagentur.unity.testdogsproject.data.network.DogBreed
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.network.Resource
import ch.iagentur.unity.testdogsproject.network.map
import ch.iagentur.unity.testdogsproject.misc.test.EspressoIdlingResource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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
    val _dogBreedStateFlow = MutableStateFlow(mutableListOf<DogBreed>())
    val dogBreedStateFlow: StateFlow<MutableList<DogBreed>> = _dogBreedStateFlow

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
        viewModelScope.launch {
            Log.d("DogBreedsViewModel", "launch")
            repositoryRetriever.getDogBreeds(page).collect { resource ->
                DogBreedsViewModel
                if (resource.status == Resource.Status.SUCCESS) {
                    Log.d("DogBreedsViewModel", "success")
                    page++
                    if (resource.data != null) {
                        localDogBreeds.addAll(resource.data.toMutableList())
                        Log.d("DogBreedsViewModel", localDogBreeds.size.toString())
                        _dogBreedStateFlow.value = localDogBreeds
                    }
                }
                //return@map resource
            }
        }
        if (EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.increment()
        }
        //updateDogBreedsLiveData.postValue("updateClients")
    }

    fun resetPage() {
        page = DEFAULT_PAGE
    }
}

