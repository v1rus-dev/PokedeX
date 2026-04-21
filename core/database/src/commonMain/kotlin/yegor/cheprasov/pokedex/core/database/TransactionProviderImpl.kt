package yegor.cheprasov.pokedex.core.database

import androidx.room.withTransaction

class TransactionProviderImpl(private val database: PokedexDatabase) : TransactionProvider {
    override suspend fun <R> runAsTransaction(block: suspend () -> R): R = database.withTransaction(block)
}