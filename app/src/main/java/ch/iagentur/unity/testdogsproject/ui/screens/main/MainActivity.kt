package ch.iagentur.unity.testdogsproject.ui.screens.main

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.ui.common.BackButtonListener
import ch.iagentur.unity.testdogsproject.ui.common.ChainHolder
import ch.iagentur.unity.testdogsproject.ui.navigation.MainFragmentNavigator
import ch.iagentur.unity.testdogsproject.ui.screens.Screens.DogBreeds
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseActivity
import ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds.DogBreedsFragment
import com.github.terrakok.cicerone.*
import com.github.terrakok.cicerone.androidx.AppNavigator
import java.lang.ref.WeakReference
import java.util.ArrayList
import javax.inject.Inject

class MainActivity : BaseActivity(), ChainHolder{
    @Inject
    lateinit var mainScreenNavigator: MainFragmentNavigator
    override val chain = ArrayList<WeakReference<Fragment>>()
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator: Navigator = object : AppNavigator(this, R.id.amContainer) {

        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
            printScreensScheme()
        }
    }

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent?.inject(this)
        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf<Command>(Replace(DogBreeds())))
        } else {
            printScreensScheme()
        }
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
            && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            super.onBackPressed()
        }
    }

    private fun printScreensScheme() {
        val fragments = ArrayList<DogBreedsFragment>()
        for (fragmentReference in chain) {
            val fragment = fragmentReference.get()
            if (fragment != null && fragment is DogBreedsFragment) {
                fragments.add(fragment)
            }
        }
    }
}