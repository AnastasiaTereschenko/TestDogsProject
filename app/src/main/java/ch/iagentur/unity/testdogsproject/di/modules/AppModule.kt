package ch.iagentur.unity.testdogsproject.di.modules

import android.content.Context
import androidx.room.Room
import ch.iagentur.unity.testdogsproject.bd.AppDatabase
import ch.iagentur.unity.testdogsproject.misc.coroutines.AppExecutors
import ch.iagentur.unity.testdogsproject.misc.utils.NetworkUtils
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val context: Context) {
    private val navigation: Cicerone<Router> = Cicerone.create()

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

    @Provides
    @Singleton
    fun provideDogDatabase(context: Context): AppDatabase {
        return AppDatabase.buildDatabase(context)
    }

    @Provides
    @Singleton
    fun provideRouter(): Router {
        return navigation.router
    }

    @Provides
    @Singleton
    fun provideNavigationHolder(): NavigatorHolder {
        return navigation.getNavigatorHolder()
    }
}