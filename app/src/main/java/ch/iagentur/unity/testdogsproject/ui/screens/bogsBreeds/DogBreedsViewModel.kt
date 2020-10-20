package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.misc.coroutines.AppExecutors
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DogBreedsViewModel @Inject constructor(
    private val repositoryRetriever: RepositoryRetriever,
    private val appExecutors: AppExecutors
) {
    companion object {
        const val DEFAULT_PAGE = 0
    }

    var page: Int = 0
    val dogBreedsLiveData: MutableLiveData<Result<List<DogBreed>>> by lazy {
        MutableLiveData<Result<List<DogBreed>>>()
    }

    fun getDogBreeds() {
        GlobalScope.launch(appExecutors.uiContext) {
            when (val result = repositoryRetriever.getDogBreeds(page)) {
                is Result.Success -> {
                    dogBreedsLiveData.value = Result.Success(result.data)
                    page++
                }
                is Result.Error -> {
                    dogBreedsLiveData.value = result
                    if (result.data!=null) {
                        page++
                    }
                    Log.e("DogBreedsPresenter", "error {${result.data}}")
                }
            }
        }
    }

    fun resetPage() {
        page = DEFAULT_PAGE
    }
}