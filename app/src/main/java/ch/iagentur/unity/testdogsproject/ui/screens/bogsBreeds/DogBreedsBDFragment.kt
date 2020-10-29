package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.os.Bundle
import android.util.Log
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
import ch.iagentur.unity.testdogsproject.di.components.DaggerFragmentComponent
import ch.iagentur.unity.testdogsproject.network.Resource
import ch.iagentur.unity.testdogsproject.ui.pagination.RecyclerViewPagination
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseActivity
import ch.iagentur.unity.testdogsproject.ui.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_bog_breeds.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogBreedsBDFragment : Fragment() {
    @Inject
    lateinit var dogBreedsBDViewModel: DogBreedsBDViewModel
    lateinit var dogBreedsAdapter: DogBreedsAdapter

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
        fdbErrorLoadingView.visibility = View.GONE
        fdbProgressBar.visibility = View.VISIBLE
        dogBreedsBDViewModel.initLoading()
        dogBreedsBDViewModel.dogBreedsLiveData.observe(this as LifecycleOwner, Observer {
            displayDogBreeds(it)
        })
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
        dogBreedsAdapter.hasStableIds()
    }

    private fun displayDogBreeds(dogBreeds: List<DogBreed>?) {
        if (dogBreeds != null && dogBreeds.isNotEmpty()) {
            Log.d("Size", dogBreeds.size.toString())
            dogBreedsAdapter.addPage(dogBreeds)
            fdbProgressBar?.visibility = View.GONE
            dogBreedsAdapter.openDogBreedInfoCallback = {
                (activity as MainActivity).mainScreenFragmentNavigator.navigateToDogBreedInfoFragment(
                    DogBreedInfoFragment.newInstance(it)
                )
            }
        }
    }
}