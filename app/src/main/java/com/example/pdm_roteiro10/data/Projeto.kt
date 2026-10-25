package com.example.pdm_roteiro10.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projetos")
data class Projeto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val prazo: String
)
