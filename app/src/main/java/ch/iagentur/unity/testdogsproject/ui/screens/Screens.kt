package ch.iagentur.unity.testdogsproject.ui.screens

import ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds.DogBreedInfoFragment
import ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds.DogBreedsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun DogBreeds() = FragmentScreen {
        DogBreedsFragment()
    }

    fun DogBreedInfo(breedId: Int) = FragmentScreen {
        DogBreedInfoFragment.newInstance(breedId)
    }
}