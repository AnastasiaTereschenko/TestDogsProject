package ch.iagentur.unity.testdogsproject.network


import ch.iagentur.unity.testdogsproject.data.source.Result
import ch.iagentur.unity.testdogsproject.misc.exception.NetworkException
import ch.iagentur.unity.testdogsproject.misc.exception.NullableResponseException
import ch.iagentur.unity.testdogsproject.misc.utils.NetworkUtils
import retrofit2.Response
import javax.inject.Inject


open class BaseInteractor {
    @Inject
    lateinit var networkUtils: NetworkUtils

    suspend fun <T : Any> execute(call: suspend () -> Response<T>): Result<T>? {
        try {
            val response = call.invoke()
            return if (response.isSuccessful) {

                val responseBody = response.body()

                if (responseBody != null) {
                    Result.Success(responseBody)
                } else {
                    Result.Error(NullableResponseException())
                }
            } else {
                Result.Error(NetworkException(response.errorBody()?.string(), response.code()))
            }
        } catch (th: Throwable) {
            return Result.Error(th)
        }
    }
}