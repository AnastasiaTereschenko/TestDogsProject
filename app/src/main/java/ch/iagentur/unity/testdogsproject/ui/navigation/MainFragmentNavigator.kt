package ch.iagentur.unity.testdogsproject.ui.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds.DogBreedsFragment
import javax.inject.Inject

class MainFragmentNavigator @Inject constructor(supportFragmentManager: FragmentManager) :
    BaseFragmentNavigator(supportFragmentManager) {
    companion object {
        const val DOG_BREEDS = "dog_breeds"
        const val DOG_BREED_INFO = "dog_breed_info"
    }

    fun navigateToDogBreedsFragment () {
        navigateToFragment(DOG_BREEDS, getContainerId())
    }

    fun navigateToDogBreedInfoFragment (fragment: Fragment) {
        navigateToFragment(fragment, DOG_BREED_INFO, getContainerId())
    }

    override fun createFragmentByTag(tag: String): Fragment {
        return DogBreedsFragment()
//            DOG_BREEDS -> DogBreedsFragment()
//            else ->
    }

    override fun getContainerId(): Int {
        return R.id.amContainer
    }
}