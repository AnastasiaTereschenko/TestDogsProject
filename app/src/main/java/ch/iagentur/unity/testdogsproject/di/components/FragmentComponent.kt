package ch.iagentur.unity.testdogsproject.di.components

import ch.iagentur.unity.testdogsproject.di.PerFragment
import ch.iagentur.unity.testdogsproject.di.modules.FragmentModule
import ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds.DogBreedsFragment
import dagger.Component

@PerFragment
@Component(modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: DogBreedsFragment)
}