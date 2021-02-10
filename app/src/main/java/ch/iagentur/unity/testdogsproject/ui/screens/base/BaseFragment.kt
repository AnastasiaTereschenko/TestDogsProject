package ch.iagentur.unity.testdogsproject.ui.screens.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import ch.iagentur.unity.testdogsproject.di.components.DaggerFragmentComponent
import ch.iagentur.unity.testdogsproject.di.components.FragmentComponent

open class BaseFragment: Fragment() {
    var fragmentComponent: FragmentComponent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentComponent =  DaggerFragmentComponent.builder().activityComponent(
            (context as BaseActivity)
                .activityComponent
        ).build()
        super.onCreate(savedInstanceState)
    }
}