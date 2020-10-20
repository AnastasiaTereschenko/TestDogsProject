package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.di.components.DaggerFragmentComponent
import ch.iagentur.unity.testdogsproject.ui.pagination.RecyclerViewPagination
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseActivity
import ch.iagentur.unity.testdogsproject.ui.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_bog_breeds.*
import javax.inject.Inject

class DogBreedsFragment : Fragment() {
    @Inject
    lateinit var dogBreedsViewModel: DogBreedsViewModel
    lateinit var pagination: RecyclerViewPagination
    lateinit var dogBreedsAdapter: DogBreedsAdapter
    var localDogBreeds: MutableList<DogBreed?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFragmentComponent.builder().activityComponent(
            (context as BaseActivity)
                .activityComponent
        ).build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bog_breeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        pagination = RecyclerViewPagination(fdbDogBreedsRecyclerView) {
            dogBreedsViewModel.getDogBreeds()
        }
        dogBreedsViewModel.getDogBreeds()
        fdbErrorLoadingView.visibility = View.GONE
        fdbProgressBar.visibility = View.VISIBLE
        fdbDogBreedsSwipeRefresh.setOnRefreshListener {
            setRefresh(true)
            pagination.reset()
            dogBreedsViewModel.resetPage()
            dogBreedsViewModel.getDogBreeds()
            dogBreedsAdapter.refreshData(mutableListOf())
            localDogBreeds = mutableListOf()
        }
        fdbReloadImageView.setOnClickListener {
            fdbProgressBar.visibility = View.VISIBLE
            fdbErrorLoadingView.visibility = View.GONE
            dogBreedsViewModel.getDogBreeds()
            localDogBreeds.clear()
        }
        dogBreedsViewModel.dogBreedsLiveData.observe(this as LifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {
                    displayDogBreeds(it.data)
                }
                is Result.Error -> {
                    if (it.data != null) {
                        displayDogBreeds(it.data)
                    } else {
                        handleLoadingError()
                    }
                }
            }
        })
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

    private fun displayDogBreeds(dogBreeds: List<DogBreed>?) {
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

    private fun handleLoadingError() {
        if (localDogBreeds.isEmpty()) {
            fdbErrorLoadingView.visibility = View.VISIBLE
            fdbProgressBar.visibility = View.GONE
        } else {
            pagination.isLoading = false
        }
        setRefresh(false)
    }
}