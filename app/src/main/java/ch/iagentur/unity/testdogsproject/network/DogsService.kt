package ch.iagentur.unity.testdogsproject.network

import ch.iagentur.unity.testdogsproject.data.network.DogBreed
import ch.iagentur.unity.testdogsproject.data.network.DogBreedInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface DogsService {
    @GET("breeds?attach_breed=0&limit=16")
    suspend fun getBreedsResult(@Query("page") page: Int): Response<List<DogBreed>>

    @GET("breeds?attach_breed=0&limit=16")
    suspend fun getBreeds(@Query("page") page: Int): List<DogBreed>

    @GET("images/search?")
    suspend fun getBreedInfo(@Query("breed_id") id: Int): List<DogBreedInfo>
}