package ch.iagentur.unity.testdogsproject.network

import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RepositoryRetriever {
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

    fun getDogBreeds(callback: Callback<List<DogBreed>>) {
        val call = service.getBreeds()
        call.enqueue(callback)
    }

    fun getBreedInfo(id: Int, callback: Callback<List<DogBreedInfo>>?) {
        val call = service.getBreedInfo(id)
        call.enqueue(callback)
    }
}