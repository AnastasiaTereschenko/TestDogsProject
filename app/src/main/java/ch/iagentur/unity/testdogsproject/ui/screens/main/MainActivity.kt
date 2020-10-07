package ch.iagentur.unity.testdogsproject.ui.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.di.modules.ActivityModule
import ch.iagentur.unity.testdogsproject.di.components.DaggerActivityComponent
import ch.iagentur.unity.testdogsproject.ui.navigation.MainFragmentNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mainScreenFragmentNavigator: MainFragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerActivityComponent.builder().activityModule(
            ActivityModule(
                this
            )
        ).build()
            .inject(this)
        mainScreenFragmentNavigator.navigateToDogBreedsFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mainScreenFragmentNavigator.navigateToDogBreedsFragment()
    }
}