package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import androidx.lifecycle.*
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.network.Resource
import ch.iagentur.unity.testdogsproject.test.EspressoIdlingResource
import ch.iagentur.unity.testdogsproject.test.EspressoIdlingResource1
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DogBreedInfoViewModel @Inject constructor(
    private val repositoryRetriever: RepositoryRetriever,
    val router: Router
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

    fun onBackPressed() {
        router.exit()
    }
}