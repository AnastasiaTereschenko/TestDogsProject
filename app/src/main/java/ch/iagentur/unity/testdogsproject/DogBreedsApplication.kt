package ch.iagentur.unity.testdogsproject

import android.app.Application
import ch.iagentur.unity.testdogsproject.di.components.AppComponent
import ch.iagentur.unity.testdogsproject.di.components.DaggerAppComponent
import ch.iagentur.unity.testdogsproject.di.modules.AppModule
import com.github.terrakok.cicerone.Cicerone

class DogBreedsApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    private fun initializeInjector() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }

}