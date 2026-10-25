package com.example.pdm_roteiro10.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TarefaDao {
    @Query("SELECT * FROM tarefas ORDER BY id DESC")
    fun getAllTarefas(): Flow<List<Tarefa>>

    @Query("SELECT * FROM tarefas WHERE id = :id")
    suspend fun getTarefaById(id: Int): Tarefa?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTarefa(tarefa: Tarefa): Long

    @Update
    suspend fun updateTarefa(tarefa: Tarefa)

    @Delete
    suspend fun deleteTarefa(tarefa: Tarefa)
}
