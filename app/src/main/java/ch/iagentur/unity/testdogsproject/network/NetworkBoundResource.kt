package ch.iagentur.unity.testdogsproject.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

abstract class NetworkBoundResource<ResultType, RequestType>(
) {
    companion object {
        private const val MAX_RETRY_COUNT = 2
    }

    private val result = MutableLiveData<Resource<ResultType>>()
    private val supervisorJob = SupervisorJob()
    private lateinit var stateFlow: MutableStateFlow<Resource<ResultType>>

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        stateFlow = MutableStateFlow<Resource<ResultType>>(Resource.loading(null))
        //setValue(Resource.loading(null))
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                fetchWithRetry(dbResult, 0, MAX_RETRY_COUNT)
            } else {
                //Log.d(NetworkBoundResource::class.java.name, "Return data from local database")
                setValue(Resource.success(dbResult))
            }
        }
        return this
    }

    private suspend fun NetworkBoundResource<ResultType, RequestType>.fetchWithRetry(
        dbResult: ResultType, retryCount: Int, maxRetryCount: Int
    ) {
        try {
            fetchFromNetwork(dbResult)
        } catch (e: Exception) {
            if (retryCount < maxRetryCount) {
                fetchWithRetry(dbResult, retryCount + 1, maxRetryCount)
            } else {
                //Log.e("NetworkBoundResource", "An error happened: $e")
                setValue(Resource.error(e, loadFromDb()))
            }

        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    fun asFlow(): Flow<Resource<ResultType>> = stateFlow

    private suspend fun fetchFromNetwork(dbResult: ResultType) {
        setValue(Resource.loading(dbResult)) // Dispatch latest value quickly (UX purpose)
        val apiResponse = createCallAsync()
        val response = processResponse(apiResponse)
        saveCallResults(response)
        // Dispatch value from DB
        setValue(Resource.success(loadFromDb()))
    }

    private suspend fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
        stateFlow.value = newValue
    }

    @WorkerThread
    protected abstract fun processResponse(response: RequestType): ResultType

    @WorkerThread
    protected abstract suspend fun saveCallResults(items: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @WorkerThread
    protected abstract suspend fun loadFromDb(): ResultType

    @WorkerThread
    open fun shouldReturnNetworkResultDirectly(): Boolean = false

    protected abstract suspend fun createCallAsync(): RequestType

}