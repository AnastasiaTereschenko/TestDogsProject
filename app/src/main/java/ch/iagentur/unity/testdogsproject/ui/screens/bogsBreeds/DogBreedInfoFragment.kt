package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.di.components.DaggerFragmentComponent
import ch.iagentur.unity.testdogsproject.network.Resource
import ch.iagentur.unity.testdogsproject.test.EspressoIdlingResource
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_dog_breed_info.*
import javax.inject.Inject


class DogBreedInfoFragment : Fragment() {
    @Inject
    lateinit var dogBreedInfoViewModel: DogBreedInfoViewModel
    var breedId: Int = 0
    var breedName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFragmentComponent.builder().activityComponent(
            (context as BaseActivity)
                .activityComponent
        ).build().inject(this)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            NavHostFragment.findNavController(this@DogBreedInfoFragment)
                .previousBackStackEntry?.savedStateHandle?.set("key", breedName)
            NavHostFragment.findNavController(this@DogBreedInfoFragment).popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dog_breed_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromArgs()
        dogBreedInfoViewModel.updateDogBreeds(breedId)
        dogBreedInfoViewModel.dogBreedInfoLiveData.observe(this as LifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (it.data != null) {
                        displayDogBreedInfo(it.data[0])
                    }
                    if (!EspressoIdlingResource.idlingResource.isIdleNow) {
//                        Handler().postDelayed({
//                            Log.d("DogBreedsFragmentTest", "decr ${activity.hashCode()}")
                        EspressoIdlingResource.decrement()
//                        },3000)
                    }
                }
                Resource.Status.ERROR -> {
                    Log.e("DogBreedInfoFragment", "error {${it.error}}")
                }
            }
        })
        fdbiProgressBar.visibility = View.VISIBLE
    }

    private fun getDataFromArgs() {
        val args: DogBreedInfoFragmentArgs? = arguments?.let {
            DogBreedInfoFragmentArgs.fromBundle(it)
        }
        args?.let {
            breedId = it.id
        }
    }


    @SuppressLint("SetTextI18n")
    fun displayDogBreedInfo(dogBreedInfo: DogBreedInfo) {
        fdbiProgressBar?.visibility = View.GONE
        breedName = dogBreedInfo.breeds[0].name?:""
        fdbiDogBreedNameTextView.text = dogBreedInfo.breeds[0].name
        if (!dogBreedInfo.breeds[0].bredFor.isNullOrEmpty()) {
            fdbiDogBreedForTextView.text = "Breed for: " + dogBreedInfo.breeds[0].bredFor
        }
        if (!dogBreedInfo.breeds[0].temperament.isNullOrEmpty()) {
            fdbiDogBreedTemperamentTextView.text =
                "Temperament:  " + dogBreedInfo.breeds[0].temperament
        }
        if (!dogBreedInfo.breeds[0].lifeSpan.isNullOrEmpty()) {
            fdbiDogBreedLifeSpanTextView.text = "Life span: " + dogBreedInfo.breeds[0].lifeSpan
        }
        context?.let {
            Glide.with(it).load(dogBreedInfo.url).apply(
                RequestOptions()
                    .placeholder((ContextCompat.getDrawable(requireContext(), R.drawable.no_image)))
                    .dontAnimate()
            )
                .into(fdbiDogBreedImageView)
        }

    }
}