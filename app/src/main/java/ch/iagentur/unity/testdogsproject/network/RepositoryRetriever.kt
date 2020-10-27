package ch.iagentur.unity.testdogsproject.network

import android.util.Log
import ch.iagentur.unity.testdogsproject.bd.AppDatabase
import ch.iagentur.unity.testdogsproject.bd.DogBreedEntity
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.misc.coroutines.AppExecutors
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RepositoryRetriever @Inject constructor(
    private val appExecutors: AppExecutors,
    private val appDatabase: AppDatabase
) : BaseInteractor() {
    private val service: DogsService

    companion object {
        const val BASE_URL = "https://api.thedogapi.com/v1/"
        const val DOG_BREED_ID = "dogBreed"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(DogsService::class.java)
    }


    suspend fun getDogBreeds(page: Int): Flow<Resource<List<DogBreed>?>> {
        val gson = Gson()
        return object : NetworkBoundResource<List<DogBreed>?, List<DogBreed>?>() {

            var response: List<DogBreed>? = null

            override fun processResponse(response: List<DogBreed>?): List<DogBreed>? {
                return response
            }

            override suspend fun saveCallResults(items: List<DogBreed>?) {
                withContext(Dispatchers.IO) {
                    if (page == 0) {
                        val dogBreedEntity = DogBreedEntity()
                        dogBreedEntity.id = DOG_BREED_ID
                        dogBreedEntity.jsonObject = gson.toJson(items)
                        appDatabase.dogBreedDao()?.insert(dogBreedEntity)
                    }
                }
                this.response = items
            }

            override fun shouldFetch(data: List<DogBreed>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): List<DogBreed>? {
                if (page != 0) {
                    return response
                }
                return withContext(Dispatchers.IO) {

                    response =
                        gson.fromJson(
                            appDatabase.dogBreedDao()?.getDogBreedById(DOG_BREED_ID)?.jsonObject,
                            object : TypeToken<ArrayList<DogBreed>?>() {}.type
                        ) as ArrayList<DogBreed>?
                    Log.d("loaded list items", response?.size.toString())
                    return@withContext response
                }
            }

            override suspend fun createCallAsync(): List<DogBreed> {
                Log.d("loaded list items", page.toString())
                return service.getBreeds(page)
            }
        }.build().asFlow()
    }

    suspend fun getDogBreedInfo(id: Int): Flow<Resource<List<DogBreedInfo>?>> {
        return object : NetworkBoundResource<List<DogBreedInfo>?, List<DogBreedInfo>?>() {

            var response: List<DogBreedInfo>? = null

            override fun processResponse(response: List<DogBreedInfo>?): List<DogBreedInfo>? {
                return response
            }

            override suspend fun saveCallResults(items: List<DogBreedInfo>?) {
                this.response = items
            }

            override fun shouldFetch(data: List<DogBreedInfo>?): Boolean {
                return true
            }

            override suspend fun loadFromDb(): List<DogBreedInfo>? {
                return response

            }

            override suspend fun createCallAsync(): List<DogBreedInfo> {
                return service.getBreedInfo(id)
            }
        }.build().asFlow()
    }

}