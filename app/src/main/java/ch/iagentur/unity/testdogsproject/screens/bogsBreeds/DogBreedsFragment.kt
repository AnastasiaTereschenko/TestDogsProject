package ch.iagentur.unity.testdogsproject.screens.bogsBreeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.navigation.MainFragmentNavigator
import ch.iagentur.unity.testdogsproject.screens.main.MainActivity
import kotlinx.android.synthetic.main.fragment_bog_breeds.*

class DogBreedsFragment : Fragment(), DogBreedsView  {
    private val dogsBreedsPresenter: DogBreedsPresenter =
        DogBreedsPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bog_breeds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dogsBreedsPresenter.setView(this)
        dogsBreedsPresenter.initLoading()
        fdbProgressBar.visibility = View.VISIBLE
    }

    override fun displayDogBreeds(dogBreeds: List<DogBreed>) {
        fdbProgressBar?.visibility = View.GONE
        val dogBreedsAdapter =
            DogBreedsAdapter(
                context!!,
                dogBreeds
            )
        val layoutManager = LinearLayoutManager(context!!)
        val dividerItemDecoration = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        fdbDogBreedsRecyclerView.layoutManager = layoutManager
        fdbDogBreedsRecyclerView.addItemDecoration(dividerItemDecoration)
        fdbDogBreedsRecyclerView.adapter = dogBreedsAdapter
        dogBreedsAdapter.openDogBreedInfoCallback = {
            (activity as MainActivity).mainScreenFragmentNavigator.navigateToDogBreedInfoFragment(DogBreedInfoFragment.newInstance(it))
        }
        //dogBreeds[0].name
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dogsBreedsPresenter.unSubscribe()
    }
}