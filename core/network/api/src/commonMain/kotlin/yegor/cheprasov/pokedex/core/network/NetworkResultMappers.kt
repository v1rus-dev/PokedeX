package yegor.cheprasov.pokedex.core.network

fun <T> NetworkResult<T>.asResult(): Result<T> = when (this) {
    is NetworkResult.Success -> Result.success(data)
    is NetworkResult.HttpError -> Result.failure(ApiException(code = code, message = message))
    is NetworkResult.NetworkError -> Result.failure(exception)
}
