import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds.DogBreedsFragment

abstract class BaseFragmentNavigator(val fragmentManager: FragmentManager) {
    fun isFragmentExist(tag: String): Boolean {
        return fragmentManager.findFragmentByTag(tag) != null
    }

    fun getFragmentByTag(tag: String): Fragment? {
        return fragmentManager.findFragmentByTag(tag)
    }
    protected var currentTag = ""

    fun hideFragment(fragment: Fragment?) {
        doActionOnFragment(fragment) {
            if (fragment != null) {
                it.hide(fragment)
            }
        }
    }

    fun showFragment(fragment: Fragment?) {
        doActionOnFragment(fragment) {
            if (fragment != null) {
                it.show(fragment)
            }
        }
    }

    fun addFragment(containerId: Int, fragment: Fragment?, tag: String) {
        doActionOnFragment(fragment) {
            if (fragment != null) {
                it.add(containerId, fragment, tag)
                if (fragment !is DogBreedsFragment) {
                    it.addToBackStack(null)
                }
            }
        }
    }

    fun doActionOnFragment(fragment: Fragment?, action:(ft: FragmentTransaction)->Unit) {
        if (fragment == null) {
            return
        }
       val ft = fragmentManager.beginTransaction()
        action(ft)
        ft.commit()
    }

    fun navigateToFragment(tag: String, containerId: Int = getContainerId()) {
        hideFragment(getFragmentByTag(currentTag))
        currentTag = tag
        if (isFragmentExist(tag)) {
            showFragment(getFragmentByTag(currentTag))
        } else {
            addFragment(containerId, createFragmentByTag(currentTag), currentTag )
        }
    }

    fun navigateToFragment(fragment: Fragment?, tag: String, containerId: Int = getContainerId()) {
        hideFragment(getFragmentByTag(currentTag))
        currentTag = tag
        addFragment(containerId, fragment, currentTag)
    }

    abstract fun createFragmentByTag(tag: String): Fragment

    abstract fun getContainerId(): Int
}