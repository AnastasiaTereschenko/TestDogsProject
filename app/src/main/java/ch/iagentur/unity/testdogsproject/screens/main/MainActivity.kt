package ch.iagentur.unity.testdogsproject.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.navigation.MainFragmentNavigator
import ch.iagentur.unity.testdogsproject.screens.bogsBreeds.DogBreedInfoFragment
import ch.iagentur.unity.testdogsproject.screens.bogsBreeds.DogBreedsFragment
import ch.iagentur.unity.testdogsproject.screens.bogsBreeds.DogBreedsPresenter

class MainActivity : AppCompatActivity() {
      val mainScreenFragmentNavigator = MainFragmentNavigator(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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