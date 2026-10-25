package com.example.pdm_roteiro10

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdm_roteiro10.data.AppDatabase
import com.example.pdm_roteiro10.data.Projeto
import com.example.pdm_roteiro10.data.Tarefa
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val tarefaDao = database.tarefaDao()
    private val projetoDao = database.projetoDao()

    private val _isLoadingTarefas = MutableStateFlow(true)
    val isLoadingTarefas: StateFlow<Boolean> = _isLoadingTarefas.asStateFlow()

    private val _isLoadingProjetos = MutableStateFlow(true)
    val isLoadingProjetos: StateFlow<Boolean> = _isLoadingProjetos.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    val tarefas: StateFlow<List<Tarefa>> = tarefaDao.getAllTarefas()
        .onEach {
            // Emits initial data or updates from Room
            delay(300) // Visual loading feedback
            _isLoadingTarefas.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val projetos: StateFlow<List<Projeto>> = projetoDao.getAllProjetos()
        .onEach {
            delay(300) // Visual loading feedback
            _isLoadingProjetos.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun salvarTarefa(tarefa: Tarefa, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            _isSaving.value = true
            delay(400) // Loading feedback on save
            if (tarefa.id == 0) {
                tarefaDao.insertTarefa(tarefa)
            } else {
                tarefaDao.updateTarefa(tarefa)
            }
            _isSaving.value = false
            onComplete()
        }
    }

    suspend fun getTarefa(id: Int): Tarefa? {
        delay(250) // Loading feedback when querying single item
        return tarefaDao.getTarefaById(id)
    }

    fun deletarTarefa(tarefa: Tarefa) {
        viewModelScope.launch {
            tarefaDao.deleteTarefa(tarefa)
        }
    }

    fun salvarProjeto(projeto: Projeto, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            _isSaving.value = true
            delay(400) // Loading feedback on save
            if (projeto.id == 0) {
                projetoDao.insertProjeto(projeto)
            } else {
                projetoDao.updateProjeto(projeto)
            }
            _isSaving.value = false
            onComplete()
        }
    }

    suspend fun getProjeto(id: Int): Projeto? {
        delay(250) // Loading feedback when querying single item
        return projetoDao.getProjetoById(id)
    }

    fun deletarProjeto(projeto: Projeto) {
        viewModelScope.launch {
            projetoDao.deleteProjeto(projeto)
        }
    }
}
