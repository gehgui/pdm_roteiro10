package com.example.pdm_roteiro10.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjetoDao {
    @Query("SELECT * FROM projetos ORDER BY id DESC")
    fun getAllProjetos(): Flow<List<Projeto>>

    @Query("SELECT * FROM projetos WHERE id = :id")
    suspend fun getProjetoById(id: Int): Projeto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjeto(projeto: Projeto): Long

    @Update
    suspend fun updateProjeto(projeto: Projeto)

    @Delete
    suspend fun deleteProjeto(projeto: Projeto)
}
