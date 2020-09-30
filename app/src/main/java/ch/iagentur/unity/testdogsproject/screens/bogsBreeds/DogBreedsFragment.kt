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

    override fun displayDogBreeds(dogBreeds: List<DogBreed>) {
        val dogBreedsAdapter =
            DogBreedsAdapter(
                context!!,
                dogBreeds
            )
        val layoutManager = LinearLayoutManager(context!!)
        val dividerItemDecoration = DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL)
        dogBreedsRecyclerView.layoutManager = layoutManager
        dogBreedsRecyclerView.addItemDecoration(dividerItemDecoration)
        dogBreedsRecyclerView.adapter = dogBreedsAdapter
        dogBreedsAdapter.openDogBreedInfoCallback = {

        }
        //dogBreeds[0].name
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dogsBreedsPresenter.setView(this)
        dogsBreedsPresenter.initLoading()
    }
}