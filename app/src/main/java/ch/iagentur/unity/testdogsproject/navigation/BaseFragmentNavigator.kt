import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

abstract class BaseFragmentNavigator(val fragmentManager: FragmentManager) {
    fun isFragmentExist(tag: String): Boolean {
        return fragmentManager.findFragmentByTag(tag) != null
    }

    fun getFragmentByTag(tag: String): Fragment? {
        return fragmentManager.findFragmentByTag(tag)
    }

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
        hideFragment(getFragmentByTag(tag))
        if (isFragmentExist(tag)) {
            showFragment(getFragmentByTag(tag))
        } else {
            addFragment(containerId, createFragmentByTag(tag), tag )
        }
    }

    abstract fun createFragmentByTag(tag: String): Fragment

    abstract fun getContainerId(): Int
}