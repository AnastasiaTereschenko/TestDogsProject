package ch.iagentur.unity.testdogsproject.ui.screens.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.iagentur.unity.testdogsproject.DogBreedsApplication
import ch.iagentur.unity.testdogsproject.di.components.ActivityComponent
import ch.iagentur.unity.testdogsproject.di.components.DaggerActivityComponent
import ch.iagentur.unity.testdogsproject.di.modules.ActivityModule

open class BaseActivity : AppCompatActivity()  {
     var activityComponent: ActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val dogBreedsApplication = this.applicationContext as DogBreedsApplication
        activityComponent = DaggerActivityComponent.builder().appComponent(dogBreedsApplication.appComponent).activityModule(
            ActivityModule(
                this
            )
        ).build()
        super.onCreate(savedInstanceState)
    }
}