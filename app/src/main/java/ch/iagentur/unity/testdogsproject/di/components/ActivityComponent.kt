package ch.iagentur.unity.testdogsproject.di.components

import ch.iagentur.unity.testdogsproject.di.modules.ActivityModule
import ch.iagentur.unity.testdogsproject.di.PerActivity
import ch.iagentur.unity.testdogsproject.misc.coroutines.AppExecutors
import ch.iagentur.unity.testdogsproject.ui.screens.main.MainActivity
import dagger.Component

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)

    fun provideAppExecutor(): AppExecutors
}