package ch.iagentur.unity.testdogsproject.screens.bogsBreeds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.DogBreed
import kotlinx.android.synthetic.main.activity_bog_breeds.*

class DogBreedsActivity : AppCompatActivity(), DogBreedsView {
    val dogsBreedsPresenter: DogBreedsPresenter = DogBreedsPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bog_breeds)
        dogsBreedsPresenter.setView(this)
        dogsBreedsPresenter.initLoading()
    }

    override fun displayDogBreeds(dogBreeds: List<DogBreed>) {
        val dogBreedsAdapter = DogBreedsAdapter(this, dogBreeds)
        val layoutManager = LinearLayoutManager(this)
        dogBreedsRecyclerView.layoutManager = layoutManager
        dogBreedsRecyclerView.adapter = dogBreedsAdapter
        //dogBreeds[0].name
    }
}