package ch.iagentur.unity.testdogsproject.di.modules

import android.content.Context
import ch.iagentur.unity.testdogsproject.misc.coroutines.AppExecutors
import ch.iagentur.unity.testdogsproject.misc.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val context: Context) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAppExecutor(): AppExecutors {
        return AppExecutors()
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils(context)
    }
}