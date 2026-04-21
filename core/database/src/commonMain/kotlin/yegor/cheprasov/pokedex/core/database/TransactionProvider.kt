package yegor.cheprasov.pokedex.core.database

interface TransactionProvider {
    suspend fun <R> runAsTransaction(block: suspend () -> R): R
}