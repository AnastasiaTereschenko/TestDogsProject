package ch.iagentur.unity.testdogsproject.di.components

import ch.iagentur.unity.testdogsproject.DogBreedsApplication
import ch.iagentur.unity.testdogsproject.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: DogBreedsApplication)
}