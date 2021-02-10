package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.network.DogBreedInfo
import ch.iagentur.unity.testdogsproject.network.Resource
import ch.iagentur.unity.testdogsproject.misc.test.EspressoIdlingResource
import ch.iagentur.unity.testdogsproject.ui.screens.base.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_dog_breed_info.*
import javax.inject.Inject

class DogBreedInfoFragment : BaseFragment() {
    @Inject
    lateinit var dogBreedInfoViewModel: DogBreedInfoViewModel

    companion object {
        const val BREED_ID = "breed_id"
        @JvmStatic
        fun newInstance(id: Int): DogBreedInfoFragment {
            val args = Bundle()
            args.putInt(BREED_ID, id)
            val fragment = DogBreedInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
    var breedId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent?.inject(this)
        if (arguments != null) {
            breedId = requireArguments().getInt(BREED_ID)
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

    @SuppressLint("SetTextI18n")
    fun displayDogBreedInfo(dogBreedInfo: DogBreedInfo) {
        fdbiProgressBar?.visibility = View.GONE
        fdbiDogBreedNameTextView.text = dogBreedInfo.breeds.get(0).name
        if (!dogBreedInfo.breeds[0].bredFor.isNullOrEmpty()) {
            fdbiDogBreedForTextView.text = "Breed for: " + dogBreedInfo.breeds.get(0).bredFor
        }
        if (!dogBreedInfo.breeds[0].temperament.isNullOrEmpty()) {
            fdbiDogBreedTemperamentTextView.text =
                "Temperament:  " + dogBreedInfo.breeds.get(0).temperament
        }
        if (!dogBreedInfo.breeds[0].lifeSpan.isNullOrEmpty()) {
            fdbiDogBreedLifeSpanTextView.text = "Life span: " + dogBreedInfo.breeds.get(0).lifeSpan
        }
        context?.let {
            Glide.with(it).load(dogBreedInfo.url).apply(
                RequestOptions()
                    .placeholder((ContextCompat.getDrawable(requireContext(), R.drawable.no_image)))
                .dontAnimate())
                .into(fdbiDogBreedImageView)
        }

    }
}