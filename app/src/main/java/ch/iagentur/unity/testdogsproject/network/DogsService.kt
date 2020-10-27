package ch.iagentur.unity.testdogsproject.network

import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface DogsService {
    @GET("breeds?attach_breed=0&limit=16")
    suspend fun getBreeds(@Query("page") page: Int): List<DogBreed>

    @GET("images/search?")
    suspend fun getBreedInfo(@Query("breed_id") id: Int): List<DogBreedInfo>
}