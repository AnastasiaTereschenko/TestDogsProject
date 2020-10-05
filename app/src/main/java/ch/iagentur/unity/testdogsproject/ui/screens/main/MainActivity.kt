package ch.iagentur.unity.testdogsproject.ui.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.ui.navigation.MainFragmentNavigator

class MainActivity : AppCompatActivity() {
    val mainScreenFragmentNavigator = MainFragmentNavigator(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainScreenFragmentNavigator.navigateToDogBreedsFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mainScreenFragmentNavigator.navigateToDogBreedsFragment()
    }

    //    fun showDogsBreedsFragmentFragment() {
//        val dogsBreedsFragment =
//            DogBreedsFragment()
//        this.supportFragmentManager.beginTransaction().add(
//            R.id.container_frame_layout, dogsBreedsFragment,
//            "dogsBreedsFragment")
//            .commit()
//    }
//
//    fun showDogBreedInfoFragment(id: Int) {
//        val dogBreedInfoFragment =
//            DogBreedInfoFragment()
//        this.supportFragmentManager.beginTransaction().add(
//            R.id.container_frame_layout, dogBreedInfoFragment,
//            "dogBreedInfoFragment")
//            .commit()
//    }
}