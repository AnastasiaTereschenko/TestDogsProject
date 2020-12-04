package ch.iagentur.unity.testdogsproject.ui.screens.main

import android.os.Bundle
import androidx.navigation.Navigation
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent?.inject(this)
        Navigation.createNavigateOnClickListener(R.id.nav_host_fragment)
    }
}