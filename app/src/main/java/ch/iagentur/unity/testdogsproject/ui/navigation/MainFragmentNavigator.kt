package ch.iagentur.unity.testdogsproject.ui.navigation

import androidx.fragment.app.FragmentManager
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.ui.screens.dogBreedsInfo.DogBreedInfoFragment
import ch.iagentur.unity.testdogsproject.ui.screens.dogsBreeds.DogBreedsFragment
import javax.inject.Inject

class MainFragmentNavigator @Inject constructor(supportFragmentManager: FragmentManager) :
    BaseFragmentNavigator(supportFragmentManager) {
    companion object {
        const val DOG_BREEDS = "dog_breeds"
        const val DOG_BREED_INFO = "dog_breed_info"
    }

    fun navigateToDogBreedsFragment () {
        navigateToFragment(DogBreedsFragment(), DOG_BREEDS)
    }

    fun navigateToDogBreedInfoFragment (id: Int) {
        navigateToFragment(DogBreedInfoFragment.newInstance(id), DOG_BREED_INFO)
    }

    override fun getContainerId(): Int {
        return R.id.amContainer
    }
}