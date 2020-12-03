package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ch.iagentur.unity.testdogsproject.DogBreedsApplication
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.di.components.DaggerFragmentComponent
import ch.iagentur.unity.testdogsproject.network.Resource
import ch.iagentur.unity.testdogsproject.test.EspressoIdlingResource
import ch.iagentur.unity.testdogsproject.ui.pagination.RecyclerViewPagination
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseActivity
import ch.iagentur.unity.testdogsproject.ui.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_bog_breeds.*

class DogBreedsFragment : Fragment() {
    lateinit var pagination: RecyclerViewPagination
    lateinit var dogBreedsAdapter: DogBreedsAdapter
    private var dataWasLoading = false

    private lateinit var vmFactory: ViewModelProvider.Factory

    val dogBreedsViewModel by viewModels<DogBreedsViewModel> {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFragmentComponent.builder().activityComponent(
            (context as BaseActivity)
                .activityComponent
        ).build().inject(this)
        val dogBreedsApplication = activity?.applicationContext as DogBreedsApplication
        vmFactory = dogBreedsApplication.appComponent.getViewModelFactory()
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
        Log.d("DogBreedsFragment","onViewCreated")
        initAdapter()
        pagination = RecyclerViewPagination(fdbDogBreedsRecyclerView) {
            dogBreedsViewModel.updateDogBreeds()
        }
        if (savedInstanceState == null) {
            dogBreedsViewModel.updateDogBreeds()
        }
        fdbErrorLoadingView.visibility = View.GONE
        fdbProgressBar.visibility = View.VISIBLE
        fdbDogBreedsSwipeRefresh.setOnRefreshListener {
            setRefresh(true)
            pagination.reset()
            dogBreedsViewModel.resetPage()
            dogBreedsViewModel.updateDogBreeds()
            dogBreedsAdapter.refreshData(mutableListOf())
        }
        fdbReloadImageView.setOnClickListener {
            fdbProgressBar.visibility = View.VISIBLE
            fdbErrorLoadingView.visibility = View.GONE
            dogBreedsViewModel.updateDogBreeds()
        }
        dogBreedsViewModel.dogBreedsLiveData.observe(this as LifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    displayDogBreeds(it.data)
                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                        EspressoIdlingResource.decrement()
                    }
                }
                Resource.Status.ERROR -> {
                    if (it.data != null) {
                        displayDogBreeds(it.data)
                        pagination.lastPageLoaded()
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
                requireContext()
            )
        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        fdbDogBreedsRecyclerView.layoutManager = layoutManager
        fdbDogBreedsRecyclerView.adapter = dogBreedsAdapter
        fdbDogBreedsRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun displayDogBreeds(dogBreeds: List<DogBreed>?) {
        if (dogBreeds != null && dogBreeds.isNotEmpty()) {
            dataWasLoading = true
            setRefresh(false)
            pagination.finishLoadMoreItems()
            dogBreedsAdapter.addPage(dogBreeds)
            fdbProgressBar?.visibility = View.GONE
            dogBreedsAdapter.openDogBreedInfoCallback = {
                view?.let { it1 -> sendDogBreedId(it1, it) }

//                (activity as MainActivity).mainScreenNavigator
//                    .navigateToDogBreedInfoFragment(it)
            }
        } else {
            pagination.lastPageLoaded()
        }
    }

    fun sendDogBreedId(view: View, id:Int) {
        val direction = DogBreedsFragmentDirections.actionGoto1(id)
        Navigation.findNavController(view).navigate(direction)
    }

    private fun handleLoadingError() {
        if (!dataWasLoading) {
            fdbErrorLoadingView.visibility = View.VISIBLE
            fdbProgressBar.visibility = View.GONE
        } else {
            pagination.isLoading = false
        }
        setRefresh(false)
    }
}