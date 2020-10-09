package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.util.Log
import ch.iagentur.unity.testdogsproject.data.network.DogBreedsInfoInteractor
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.ui.screens.base.BasePresenter
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DogBreedsInfoPresenterImpl @Inject constructor(private val dogBreedsInfoInteractor: DogBreedsInfoInteractor) :
    BasePresenter<DogBreedInfoView> {
    private var dogBreedInfoView: DogBreedInfoView? = null
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    fun initLoading(id: Int) {
        getDogBreedInfo(id)
    }

    private fun getDogBreedInfo(id: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            when (val result = dogBreedsInfoInteractor.getDogBreedInfo(id)) {
                is Result.Success -> {
                    dogBreedInfoView?.displayDogBreedInfo(result.data[0])
                }
                is Result.Error -> {
                    Log.e("DogBreedsInfoPresenter", "error {${result.exception}}")
                }
            }
        }
//        repoRetriever.getBreedInfo(id, object : Callback<List<DogBreedInfo>> {
//            override fun onFailure(call: Call<List<DogBreedInfo>>?, t: Throwable?) {
//                Log.e("DogBreedsInfoPresenter", "Problem calling Github API {${t?.message}}")
//            }
//
//            override fun onResponse(
//                call: Call<List<DogBreedInfo>>?,
//                response: Response<List<DogBreedInfo>>?
//            ) {
//                response?.isSuccessful.let {
//                    val result = response?.body()
//                    if (result != null) {
//                        dogBreedInfoView?.displayDogBreedInfo(result.get(0))
//                    }
//                    Log.e(
//                        "DogBreedsInfoPresenter", "Problem calling Github API " +
//                                "{${result?.get(0)?.breeds?.get(0)?.name}}"
//                    )
//                }
//            }
//        })
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