package yegor.cheprasov.pokedex.core.database

import androidx.room.Transactor
import androidx.room.useWriterConnection

interface TransactionProvider {
    suspend fun <R> runAsTransaction(block: suspend () -> R): R
}

class TransactionProviderImpl(private val pokedexDatabase: PokedexDatabase) : TransactionProvider {
    override suspend fun <R> runAsTransaction(block: suspend () -> R): R = pokedexDatabase.useWriterConnection {
        it.withTransaction(Transactor.SQLiteTransactionType.IMMEDIATE) {
            block.invoke()
        }
    }
}