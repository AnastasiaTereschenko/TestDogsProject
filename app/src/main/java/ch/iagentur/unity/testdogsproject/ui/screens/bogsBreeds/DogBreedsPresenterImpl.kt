package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.util.Log
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.ui.screens.base.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DogBreedsPresenterImpl @Inject constructor(private val repositoryRetriever: RepositoryRetriever) :
    BasePresenter<DogBreedsView> {
    private lateinit var dogBreedsView: DogBreedsView
    var page: Int = 0

    companion object {
        const val DEFAULT_PAGE = 0
    }

    override fun setView(view: DogBreedsView?) {
        if (view != null) {
            dogBreedsView = view
        }
        loadNextPage()
    }


    fun loadNextPage() {
        GlobalScope.launch(Dispatchers.Main) {
            when (val result = repositoryRetriever.getDogBreeds(page)) {
                is Result.Success -> {
                    dogBreedsView.displayDogBreeds(result.data)
                    page++
                }
                is Result.Error -> {
                    Log.e("DogBreedsPresenter", "error {${result.exception}}")
                }
            }
        }

//        repoRetriever.getDogBreeds(page, object : Callback<List<DogBreed>> {
//            override fun onFailure(call: Call<List<DogBreed>>?, t: Throwable?) {
//                dogBreedsView.handleLoadingError()
//                Log.e("MainActivity", "Problem calling Github API {${t?.message}}")
//            }
//
//            override fun onResponse(
//                call: Call<List<DogBreed>>?,
//                response: Response<List<DogBreed>>?
//            ) {
//                response?.isSuccessful.let {
//                    val resultList = response?.body()
//                    dogBreedsView.displayDogBreeds(resultList)
//                    page++
//                    Log.e("MainActivity", "Problem calling Github API {${resultList?.indexOf(1)}}")
//                }
//            }
//        })
    }

    fun resetPage() {
        page = DEFAULT_PAGE
    }

    override fun unSubscribe() {

    }
}