package ch.iagentur.unity.testdogsproject.ui.screens.bogsBreeds

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.di.components.DaggerFragmentComponent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_dog_breed_info.*
import javax.inject.Inject

class DogBreedInfoFragment : Fragment(), DogBreedInfoView {
    @Inject
    lateinit var dogBreedsInfoPresenter: DogBreedsInfoPresenterImpl

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
        DaggerFragmentComponent.builder().build().inject(this)
        if (arguments != null) {
            breedId = arguments!!.getInt(BREED_ID)
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
        dogBreedsInfoPresenter.setView(this)
        dogBreedsInfoPresenter.initLoading(breedId)
        fdbiProgressBar.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    override fun displayDogBreedInfo(dogBreedInfo: DogBreedInfo) {
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
                    .placeholder((ContextCompat.getDrawable(context!!, R.drawable.no_image)))
                .dontAnimate())
                .into(fdbiDogBreedImageView)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        dogBreedsInfoPresenter.unSubscribe()
    }
}