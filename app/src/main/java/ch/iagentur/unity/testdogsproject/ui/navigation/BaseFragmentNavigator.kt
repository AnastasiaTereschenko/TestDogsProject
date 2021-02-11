package ch.iagentur.unity.testdogsproject.ui.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*

abstract class BaseFragmentNavigator(val fragmentManager: FragmentManager) {
    companion object {
        const val STACK_FRAGMENTS = "STACK_FRAGMENTS"
    }

    fun getFragmentByTag(tag: String): Fragment? {
        return fragmentManager.findFragmentByTag(tag)
    }

    private var stack = Stack<String>()

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

    fun addFragment(fragment: Fragment?, tag: String) {
        doActionOnFragment(fragment) {
            if (fragment != null) {
                it.add(getContainerId(), fragment, tag)
            }
        }
    }

    private fun removeFragment(fragment: Fragment?) {
        doActionOnFragment(fragment) {
            if (fragment != null) {
                it.remove(fragment)
            }
        }
    }

    fun onBackNavigation(): Boolean {
        return if (stack.size == 1) {
            false
        } else {
            removeFragment(getFragmentByTag(stack.pop()))
            showFragment(getFragmentByTag(stack.peek()))
            true
        }
    }

    fun doActionOnFragment(fragment: Fragment?, action: (ft: FragmentTransaction) -> Unit) {
        if (fragment == null) {
            return
        }
        val ft = fragmentManager.beginTransaction()
        action(ft)
        ft.commit()
    }

    fun navigateToFragment(fragment: Fragment?, tag: String) {
        if (stack.size > 0) {
            hideFragment(getFragmentByTag(stack.peek()))
        }
        stack.add(tag)
        addFragment(fragment, tag)
    }

    fun onSaveState(bundle: Bundle) {
        bundle.putSerializable(STACK_FRAGMENTS, stack)
    }

    fun onRestoreState(bundle: Bundle) {
        stack = bundle.getSerializable(STACK_FRAGMENTS) as Stack<String>
    }

    abstract fun getContainerId(): Int
}