package ch.iagentur.unity.testdogsproject.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.DogBreed
import kotlinx.android.synthetic.main.activity_main.*

class DogBreedsActivity : AppCompatActivity(), DogBreedsView {
    val dogsBreedsPresenter: DogBreedsPresenter = DogBreedsPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dogsBreedsPresenter.setView(this)
        dogsBreedsPresenter.initLoading()
    }

    override fun displayDogBreeds(dogBreeds: List<DogBreed>) {
        breedsNameTextView.text = dogBreeds[0].name
    }
}