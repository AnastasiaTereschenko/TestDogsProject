package ch.iagentur.unity.testdogsproject.misc.test

import android.util.Log
import androidx.test.espresso.IdlingResource


/**
 * Contains a static reference IdlingResource, and should be available only in a mock build type.
 */
class EspressoIdlingResource {
    companion object {
        private const val RESOURCE = "GLOBAL"
        private val mCountingIdlingResource =
            SimpleCountingIdlingResource(
                RESOURCE
            )

        fun increment() {
            Log.d("DogBreedsFragmentTest", "increment")
            mCountingIdlingResource.increment()
        }

        fun decrement() {
            Log.d("DogBreedsFragmentTest", "decrement")
            mCountingIdlingResource.decrement()
        }

        val idlingResource: IdlingResource
            get() = mCountingIdlingResource
    }
}