package ch.iagentur.unity.testdogsproject.di.components

import ch.iagentur.unity.testdogsproject.di.PerFragment
import ch.iagentur.unity.testdogsproject.di.modules.FragmentModule
import ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds.DogBreedInfoFragment
import ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds.DogBreedsBDFragment
import ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds.DogBreedsFragment
import dagger.Component

@PerFragment
@Component(dependencies = [ActivityComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(dogBreedsFragment: DogBreedsFragment)
    fun inject(dogBreedsBDFragment: DogBreedsBDFragment)
    fun inject(dogBreedInfoFragment: DogBreedInfoFragment)
}