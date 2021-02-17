package ch.iagentur.unity.testdogsproject

import android.app.Application
import ch.iagentur.unity.testdogsproject.di.components.AppComponent
import ch.iagentur.unity.testdogsproject.di.components.DaggerAppComponent
import ch.iagentur.unity.testdogsproject.di.modules.AppModule
import ch.iagentur.unity.testdogsproject.misc.logs.HyperlinkedDebugTree
import timber.log.Timber

class DogBreedsApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
        Timber.plant(HyperlinkedDebugTree())
    }

    private fun initializeInjector() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }

}