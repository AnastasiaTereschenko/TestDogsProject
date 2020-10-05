package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.ui.pagination.RecyclerViewPagination
import ch.iagentur.unity.testdogsproject.ui.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_bog_breeds.*

class DogBreedsFragment : Fragment(), DogBreedsView {
    private var dogBreedsPresenter: DogBreedsPresenter = DogBreedsPresenter()
    lateinit var pagination: RecyclerViewPagination
    lateinit var dogBreedsAdapter: DogBreedsAdapter
    var localDogBreeds: MutableList<DogBreed?> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bog_breeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dogBreedsPresenter.setView(this)
        initAdapter()
        pagination = RecyclerViewPagination(fdbDogBreedsRecyclerView) {
            dogBreedsPresenter.loadNextPage()
        }
        fdbErrorLoadingView.visibility = View.GONE
        fdbProgressBar.visibility = View.VISIBLE
        fdbDogBreedsSwipeRefresh.setOnRefreshListener {
            setRefresh(true)
            pagination.reset()
            dogBreedsPresenter.resetPage()
            dogBreedsPresenter.loadNextPage()
            dogBreedsAdapter.refreshData(mutableListOf())
            localDogBreeds = mutableListOf()
        }
        fdbReloadImageView.setOnClickListener {
            fdbProgressBar.visibility = View.VISIBLE
            fdbErrorLoadingView.visibility = View.GONE
            dogBreedsPresenter.loadNextPage()
            localDogBreeds.clear()
        }
    }

    private fun setRefresh(isRefresh: Boolean) {
        fdbDogBreedsSwipeRefresh.post { fdbDogBreedsSwipeRefresh.isRefreshing = isRefresh }
    }

    private fun initAdapter() {
        dogBreedsAdapter =
            DogBreedsAdapter(
                context!!
            )
        val layoutManager = LinearLayoutManager(context!!)
        val dividerItemDecoration =
            DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        fdbDogBreedsRecyclerView.layoutManager = layoutManager
        fdbDogBreedsRecyclerView.adapter = dogBreedsAdapter
        fdbDogBreedsRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun displayDogBreeds(dogBreeds: List<DogBreed>?) {
        if (dogBreeds != null && dogBreeds.isNotEmpty()) {
            localDogBreeds.addAll(dogBreeds)
            setRefresh(false)
            pagination.finishLoadMoreItems()
            dogBreedsAdapter.addPage(dogBreeds)
            fdbProgressBar?.visibility = View.GONE
            dogBreedsAdapter.openDogBreedInfoCallback = {
                (activity as MainActivity).mainScreenFragmentNavigator.navigateToDogBreedInfoFragment(
                    DogBreedInfoFragment.newInstance(it)
                )
            }
        } else {
            pagination.lastPageLoaded()
        }
    }

    override fun handleLoadingError() {
        if (localDogBreeds.isEmpty()) {
            fdbErrorLoadingView.visibility = View.VISIBLE
            fdbProgressBar.visibility = View.GONE
        } else {
            pagination.isLoading = false
        }
        setRefresh(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dogBreedsPresenter.unSubscribe()
    }
}