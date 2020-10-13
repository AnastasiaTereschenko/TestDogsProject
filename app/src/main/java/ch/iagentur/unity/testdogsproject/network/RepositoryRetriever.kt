package ch.iagentur.unity.testdogsproject.network

import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.data.source.Result
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RepositoryRetriever @Inject constructor() : BaseInteractor() {
    private val service: DogsService

    companion object {
        const val BASE_URL = "https://api.thedogapi.com/v1/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(DogsService::class.java)
    }

    suspend fun getDogBreeds(page: Int): Result<List<DogBreed>>? {
        return execute { service.getBreeds(page) }
    }

    suspend fun getDogBreedInfo(id: Int): Result<List<DogBreedInfo>>? {
        return execute { service.getBreedInfo(id) }
    }

}