package ch.iagentur.unity.testdogsproject.network

import ch.iagentur.unity.testdogsproject.data.DogBreed
import retrofit2.Call
import retrofit2.http.GET


interface DogsService {
    @GET("breeds?attach_breed=0&page=0&limit=10")
    fun getBreeds(): Call<List<DogBreed>>

}