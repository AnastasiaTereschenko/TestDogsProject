package ch.iagentur.unity.testdogsproject.network

import android.util.Log
import ch.iagentur.unity.testdogsproject.bd.AppDatabase
import ch.iagentur.unity.testdogsproject.bd.DogBreedEntity
import ch.iagentur.unity.testdogsproject.data.DogBreed
import ch.iagentur.unity.testdogsproject.data.DogBreedInfo
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.misc.coroutines.AppExecutors
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class RepositoryRetriever @Inject constructor(
    private val appExecutors: AppExecutors,
    private val appDatabase: AppDatabase
) :
    BaseInteractor() {
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

    suspend fun getDogBreeds(page: Int): Result<List<DogBreed>>? {
        val result = execute { service.getBreeds(page) }
        val gson = Gson()
        return withContext(appExecutors.ioContext) {
            Log.e(
                "listDog",
                appDatabase.dogBreedDao()?.getDogBreedById(DOG_BREED_ID)?.jsonObject ?: ""
            )
            val dogBreed =
                gson.fromJson(appDatabase.dogBreedDao()?.getDogBreedById(DOG_BREED_ID)?.jsonObject,
                    object : TypeToken<ArrayList<DogBreed>?>() {}.type
                ) as ArrayList<DogBreed>?
            when (result) {
                is Result.Success -> {
                    if (page == 0) {
                        val dogBreedEntity = DogBreedEntity()
                        dogBreedEntity.id = DOG_BREED_ID
                        dogBreedEntity.jsonObject = gson.toJson(result.data)
                        appDatabase.dogBreedDao()?.insert(dogBreedEntity)
                    }
                    return@withContext Result.Success(result.data)
                }
                is Result.Error -> {
                    if (page == 0) {
                        return@withContext Result.Error(null, dogBreed)
                    } else {
                        return@withContext Result.Error(result.exception, null)
                    }
                }
            }
            return@withContext result
        }

    }

    suspend fun getDogBreedInfo(id: Int): Result<List<DogBreedInfo>>? {
        return execute { service.getBreedInfo(id) }
    }

}