package ch.iagentur.unity.testdogsproject.di.modules

import androidx.appcompat.app.AppCompatActivity
import ch.iagentur.unity.testdogsproject.di.PerActivity
import ch.iagentur.unity.testdogsproject.ui.navigation.MainFragmentNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule constructor(private val activity: AppCompatActivity){

    @Provides
    @PerActivity
    fun activity(): AppCompatActivity {
        return activity
    }

    @Provides
    @PerActivity
    fun navigator(): MainFragmentNavigator {
        return MainFragmentNavigator(activity.supportFragmentManager)
    }

}