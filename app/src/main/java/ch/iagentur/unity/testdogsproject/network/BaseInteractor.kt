package ch.iagentur.unity.testdogsproject.network


import android.util.Log
import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.misc.exception.NetworkException
import ch.iagentur.unity.testdogsproject.misc.exception.NoInternetConnectionException
import ch.iagentur.unity.testdogsproject.misc.exception.NullableResponseException
import ch.iagentur.unity.testdogsproject.misc.utils.NetworkUtils
import kotlinx.coroutines.Deferred
import retrofit2.Response
import javax.inject.Inject


open class BaseInteractor<T : Any> {
    @Inject
    lateinit var networkUtils: NetworkUtils

    suspend fun execute(deferred: suspend () -> Response<T>): Result<T>? {
        var response: Response<T>? = null
        try {
            if (!networkUtils.isNetworkAvailable()) {
                Result.Error(NoInternetConnectionException())
            } else {
                try {
                    response = deferred()
                } catch (th: Throwable) {
                    Result.Error(th)
                }
            }
            return if (response?.isSuccessful == true) {
                val responseBody = response?.body()
                if (responseBody != null) {
                    Result.Success(responseBody)
                } else {
                    Result.Error(NullableResponseException())
                }
            } else {
                Result.Error(getError(response))
            }

        } catch (th: Throwable) {
            return Result.Error(th)
        }
    }

    fun getError(response: Response<T>?): NetworkException {
        val error = NetworkException(response?.errorBody()?.string(), response?.code() ?: 0)
        Log.d("BaseRepository", "error  ${response?.code()} urk ${response?.raw()?.request()?.url()} ")
        return error
    }

}