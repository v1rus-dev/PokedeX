package yegor.cheprasov.pokedex.core.network

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class HttpError(val code: Int, val message: String?) : NetworkResult<Nothing>()
    data class NetworkError(val exception: Exception) : NetworkResult<Nothing>()

    val isSuccess get() = this is Success

    inline fun onSuccess(action: (T) -> Unit): NetworkResult<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onHttpError(action: (code: Int, message: String?) -> Unit): NetworkResult<T> {
        if (this is HttpError) action(code, message)
        return this
    }

    inline fun onNetworkError(action: (Exception) -> Unit): NetworkResult<T> {
        if (this is NetworkError) action(exception)
        return this
    }

    inline fun <R> map(transform: (T) -> R): NetworkResult<R> = when (this) {
        is Success -> Success(transform(data))
        is HttpError -> this
        is NetworkError -> this
    }

    fun getOrNull(): T? = (this as? Success)?.data
    fun getOrDefault(default: @UnsafeVariance T): T = getOrNull() ?: default
}
