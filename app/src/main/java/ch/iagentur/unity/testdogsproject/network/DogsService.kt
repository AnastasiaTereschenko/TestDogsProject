package ch.iagentur.unity.testdogsproject.network

import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DogsService {
    @GET("breeds?attach_breed=0&page=0&limit=10")
    fun getBreeds(): Call<List<DogBreed>>

    @GET("images/search?")
    fun getBreedInfo(@Query("breed_id") id: Int): Call<List<DogBreedInfo>>
}