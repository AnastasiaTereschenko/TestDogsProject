package ch.iagentur.unity.testdogsproject.ui.screens.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.ui.navigation.MainFragmentNavigator
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var mainScreenNavigator: MainFragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent?.inject(this)
        Navigation.createNavigateOnClickListener(R.id.nav_host_fragment)
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        mainScreenNavigator.onSaveState(outState)
//    }

//    override fun onBackPressed() {
//        if (!mainScreenNavigator.onBackNavigation()) {
//            super.onBackPressed()
//        }
//    }
}