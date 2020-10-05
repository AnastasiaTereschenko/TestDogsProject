package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import ch.iagentur.unity.testdogsproject.data.DogBreed


interface DogBreedsView {
    fun displayDogBreeds(dogBreeds: List<DogBreed>?)
    fun handleLoadingError()
}