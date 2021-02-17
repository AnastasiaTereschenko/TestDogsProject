package ch.iagentur.unity.testdogsproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ch.iagentur.unity.testdogsproject.data.network.DogBreed
import ch.iagentur.unity.testdogsproject.network.RepositoryRetriever
import ch.iagentur.unity.testdogsproject.network.Resource
import ch.iagentur.unity.testdogsproject.test.getOrAwaitValue
import ch.iagentur.unity.testdogsproject.ui.screens.dogsBreeds.DogBreedsViewModel
import com.carlosgub.coroutines.utils.CoroutinesRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TestDogsUnitTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutinesRule()

    private val repositoryRetriever = mockk<RepositoryRetriever>()
    private lateinit var viewModel: DogBreedsViewModel
    private val observer = mockk<Observer<Resource<List<DogBreed>?>>>(relaxed = true)

    @Before
    fun setup() {
        viewModel = DogBreedsViewModel(repositoryRetriever)
    }

    @Test
     fun checkPageTest() {
        coEvery {
            repositoryRetriever.getDogBreeds(0)
        }.returns(TestData.dataList.asFlow())
        viewModel.updateDogBreedsLiveData.value = "update"
        viewModel.dogBreedsLiveData.observeForever(observer)
//        viewModel.dogBreedsLiveData.observe(this as LifecycleOwner, Observer {
//            when (it.status) {
//                Resource.Status.SUCCESS -> {
//
//                }
//            }
//        })
        viewModel.dogBreedsLiveData.getOrAwaitValue()
        assertEquals(viewModel.page, 1)
    }
}