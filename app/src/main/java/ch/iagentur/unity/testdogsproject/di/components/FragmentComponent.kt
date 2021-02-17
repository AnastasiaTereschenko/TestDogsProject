package ch.iagentur.unity.testdogsproject.di.components

import ch.iagentur.unity.testdogsproject.di.PerFragment
import ch.iagentur.unity.testdogsproject.di.modules.FragmentModule
import ch.iagentur.unity.testdogsproject.ui.screens.dogBreedsInfo.DogBreedInfoFragment
import ch.iagentur.unity.testdogsproject.ui.screens.dogsBreeds.DogBreedsFragment
import dagger.Component

@PerFragment
@Component(dependencies = [ActivityComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(dogBreedsFragment: DogBreedsFragment)
    fun inject(dogBreedInfoFragment: DogBreedInfoFragment)
}