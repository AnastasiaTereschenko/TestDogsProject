package ch.iagentur.unity.testdogsproject.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.iagentur.unity.testdogsproject.di.ViewModelKey
import ch.iagentur.unity.testdogsproject.misc.viewModel.AppViewModelFactory
import ch.iagentur.unity.testdogsproject.ui.screens.dogsBreeds.DogBreedsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DogBreedsViewModel::class)
    abstract fun bindDogBreedsViewModel(viewModel: DogBreedsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}