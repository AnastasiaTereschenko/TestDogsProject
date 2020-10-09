package ch.iagentur.unity.testdogsproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RepositoryRetriever @Inject constructor() {
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

    fun getService(): DogsService {
        return service
    }
}