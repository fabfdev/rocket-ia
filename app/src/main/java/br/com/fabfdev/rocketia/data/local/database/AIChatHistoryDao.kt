package br.com.fabfdev.rocketia.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AIChatHistoryDao {
    @Query("SELECT * FROM aichattextentity WHERE stack = :stack ORDER BY datetime DESC")
    fun getAllByStack(stack: String): Flow<List<AIChatTextEntity>>

    @Insert
    suspend fun insertAll(vararg aiChatTextEntity: AIChatTextEntity)
}