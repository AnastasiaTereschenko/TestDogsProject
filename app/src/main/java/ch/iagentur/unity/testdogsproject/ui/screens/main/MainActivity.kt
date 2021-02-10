package ch.iagentur.unity.testdogsproject.ui.screens.main

import android.os.Bundle
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.ui.navigation.MainFragmentNavigator
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var mainScreenNavigator: MainFragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent?.inject(this)
        if (savedInstanceState == null) {
            mainScreenNavigator.navigateToDogBreedsFragment()
        } else {
            mainScreenNavigator.onRestoreState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mainScreenNavigator.onSaveState(outState)
    }

    override fun onBackPressed() {
        if (!mainScreenNavigator.onBackNavigation()) {
            finish()
        }
    }
}