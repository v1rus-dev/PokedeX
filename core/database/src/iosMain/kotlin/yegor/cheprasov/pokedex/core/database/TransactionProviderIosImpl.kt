package yegor.cheprasov.pokedex.core.database

import androidx.room.Transactor
import androidx.room.useWriterConnection

class TransactionProviderIosImpl(private val database: PokedexDatabase) : TransactionProvider {
    override suspend fun <R> runAsTransaction(block: suspend () -> R): R = database.useWriterConnection {
        it.withTransaction(Transactor.SQLiteTransactionType.IMMEDIATE) {
            block.invoke()
        }
    }
}