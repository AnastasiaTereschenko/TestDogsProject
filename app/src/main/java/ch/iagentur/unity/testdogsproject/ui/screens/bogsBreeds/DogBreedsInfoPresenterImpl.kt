package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.util.Log
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.ui.screens.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DogBreedsInfoPresenterImpl @Inject constructor(): BasePresenter<DogBreedInfoView> {
    private val repoRetriever = RepositoryRetriever()
    private var dogBreedInfoView: DogBreedInfoView? = null

    fun initLoading(id: Int) {
        getDogBreedInfo(id)
    }

    private fun getDogBreedInfo(id: Int) {
        repoRetriever.getBreedInfo(id, object : Callback<List<DogBreedInfo>> {
            override fun onFailure(call: Call<List<DogBreedInfo>>?, t: Throwable?) {
                Log.e("DogBreedsInfoPresenter", "Problem calling Github API {${t?.message}}")
            }

            override fun onResponse(
                call: Call<List<DogBreedInfo>>?,
                response: Response<List<DogBreedInfo>>?
            ) {
                response?.isSuccessful.let {
                    val result = response?.body()
                    if (result != null ) {
                        dogBreedInfoView?.displayDogBreedInfo(result.get(0))
                    }
                    Log.e(
                        "DogBreedsInfoPresenter", "Problem calling Github API " +
                                "{${result?.get(0)?.breeds?.get(0)?.name}}"
                    )
                }
            }
        })
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