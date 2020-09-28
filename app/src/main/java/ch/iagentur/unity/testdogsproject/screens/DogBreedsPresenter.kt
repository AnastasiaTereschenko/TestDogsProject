package ch.iagentur.unity.testdogsproject.screens

import android.util.Log
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.screens.base.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DogBreedsPresenter: BasePresenter<DogBreedsView>{
    private val repoRetriever = RepositoryRetriever()
    private lateinit var dogsBreedsView: DogBreedsView
     fun initLoading() {
        getDogBreeds()
    }

    private fun getDogBreeds() {
        repoRetriever.getDogBreeds(object : Callback<List<DogBreed>> {
            override fun onFailure(call: Call<List<DogBreed>>?, t: Throwable?) {
                Log.e("MainActivity", "Problem calling Github API {${t?.message}}")
            }

            override fun onResponse(call: Call<List<DogBreed>>?, response: Response<List<DogBreed>>?) {
                response?.isSuccessful.let {
                    val resultList = response?.body()
                    if (resultList != null) {
                        dogsBreedsView.displayDogBreeds(resultList)
                    }
                    Log.e("MainActivity", "Problem calling Github API {${resultList?.indexOf(1) }}")
                }
            }
        })
    }

    override fun setView(view: DogBreedsView?) {
        if (view != null) {
            dogsBreedsView = view
        }
    }

    override fun unSubscribe() {
        TODO("Not yet implemented")
    }

}