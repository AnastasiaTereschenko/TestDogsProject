package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import androidx.lifecycle.MutableLiveData
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.misc.coroutines.AppExecutors
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import kotlinx.coroutines.GlobalScope
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
        GlobalScope.launch(appExecutors.uiContext) {
            dogBreedInfoLiveData.value = repositoryRetriever.getDogBreedInfo(id)
        }
    }
}