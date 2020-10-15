package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.util.Log
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.ui.screens.base.BasePresenter
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DogBreedsInfoPresenterImpl @Inject constructor(private val repositoryRetriever: RepositoryRetriever) :
    BasePresenter<DogBreedInfoView> {
    private var dogBreedInfoView: DogBreedInfoView? = null

    fun initLoading(id: Int) {
        getDogBreedInfo(id)
    }

    private fun getDogBreedInfo(id: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            when (val result = repositoryRetriever.getDogBreedInfo(id)) {
                is Result.Success -> {
                    dogBreedInfoView?.displayDogBreedInfo(result.data[0])
                }
                is Result.Error -> {
                    Log.e("DogBreedsInfoPresenter", "error {${result.exception}}")
                }
            }
        }
    }

    override fun setView(view: DogBreedInfoView?) {
        if (view != null) {
            dogBreedInfoView = view
        }
    }

    override fun unSubscribe() {
        dogBreedInfoView = null
    }
}