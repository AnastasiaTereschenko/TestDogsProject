package ch.iagentur.unity.testdogsproject.ui.screens.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.ui.common.BackButtonListener
import ch.iagentur.unity.testdogsproject.ui.common.ChainHolder
import ch.iagentur.unity.testdogsproject.ui.navigation.MainFragmentNavigator
import ch.iagentur.unity.testdogsproject.ui.screens.Screens.DogBreeds
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseActivity
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var mainScreenNavigator: MainFragmentNavigator

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val navigator = AppNavigator(this, R.id.amContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent?.inject(this)
        router.navigateTo(DogBreeds())
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)

    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.amContainer)
        if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            super.onBackPressed()
        }
    }

}