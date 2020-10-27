package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.misc.coroutines.AppExecutors
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class DogBreedInfoViewModel @Inject constructor(
    private val repositoryRetriever: RepositoryRetriever,
    private val appExecutors: AppExecutors
) {
    val dogBreedInfoLiveData: MutableLiveData<Result<List<DogBreedInfo>>> by lazy {
        MutableLiveData<Result<List<DogBreedInfo>>>()
    }

    fun getDogBreedInfo(id: Int) {
//        dogBreedInfoLiveData.: LiveData<Resource<List<DogBreedInfo>>> =
//        Transformations.switchMap(updateDogBreedsLiveData) {
//            return@switchMap liveData {
//                emitSource(
//                    repositoryRetriever.getDogBreeds(page).flowOn(Dispatchers.IO).map { list ->
//                        if (list.status == Resource.Status.SUCCESS) {
//                            page++
//                        }
//                        return@map list
//                    }.asLiveData()
//                )
//            }
//        }
//        GlobalScope.launch(appExecutors.uiContext) {
//            dogBreedInfoLiveData.value = repositoryRetriever.getDogBreedInfo(id)
//        }
    }
}