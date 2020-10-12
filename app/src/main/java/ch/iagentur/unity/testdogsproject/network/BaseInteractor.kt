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


open class BaseInteractor{
    @Inject
    lateinit var networkUtils: NetworkUtils

    suspend fun <T : Any> execute(call: suspend () -> Response<T>): Result<T>? {
        return try {
            val response = call.invoke()
            Result.Success(response.body()!!)
//            else {
//                Result.Error(getError(response))
//            }
        } catch (th: Throwable) {
            Result.Error(th)
        }
    }

//    fun getError(response: Response<T>?): NetworkException {
//        val error = NetworkException(response?.errorBody()?.string(), response?.code() ?: 0)
//        Log.d("BaseRepository", "error  ${response?.code()} urk ${response?.raw()?.request()?.url()} ")
//        return error
//    }

}