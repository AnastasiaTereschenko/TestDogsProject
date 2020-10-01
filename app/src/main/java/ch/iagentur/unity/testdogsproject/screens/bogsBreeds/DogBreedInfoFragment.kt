package ch.iagentur.unity.testdogsproject.screens.bogsBreeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ch.iagentur.unity.testdogsproject.R
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import kotlinx.android.synthetic.main.fragment_dog_breed_info.*

class DogBreedInfoFragment : Fragment(), DogBreedInfoView {
    private val dogBreedsInfoPresenter: DogBreedsInfoPresenter =
        DogBreedsInfoPresenter()
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
    }

    override fun displayDogBreedInfo(dogBreedInfo: DogBreedInfo) {
        fdbiDogBreedNameTextView.text = dogBreedInfo.breeds.get(0).name
    }
}