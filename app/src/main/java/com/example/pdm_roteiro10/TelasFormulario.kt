package com.example.pdm_roteiro10

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pdm_roteiro10.data.Projeto
import com.example.pdm_roteiro10.data.Tarefa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTarefaScreen(viewModel: AppViewModel, idTarefa: Int?, onNavigateBack: () -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var isLoadingItem by remember { mutableStateOf(idTarefa != null) }
    val isSaving by viewModel.isSaving.collectAsState()

    LaunchedEffect(idTarefa) {
        if (idTarefa != null) {
            isLoadingItem = true
            val tarefa = viewModel.getTarefa(idTarefa)
            if (tarefa != null) {
                titulo = tarefa.titulo
                status = tarefa.status
            }
            isLoadingItem = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (idTarefa == null) "Nova Tarefa" else "Editar Tarefa") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack, enabled = !isSaving) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (isLoadingItem) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicatorScreen("Carregando dados da tarefa...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título da Tarefa") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = status,
                    onValueChange = { status = it },
                    label = { Text("Status (ex: Em andamento, Concluído)") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onNavigateBack,
                        enabled = !isSaving
                    ) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            if (titulo.isNotBlank()) {
                                val novaTarefa = Tarefa(
                                    id = idTarefa ?: 0,
                                    titulo = titulo.trim(),
                                    status = status.trim().ifBlank { "Pendente" }
                                )
                                viewModel.salvarTarefa(novaTarefa) {
                                    onNavigateBack()
                                }
                            }
                        },
                        enabled = !isSaving && titulo.isNotBlank()
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Salvando...")
                        } else {
                            Text("Salvar")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormProjetoScreen(viewModel: AppViewModel, idProjeto: Int?, onNavigateBack: () -> Unit) {
    var nome by remember { mutableStateOf("") }
    var prazo by remember { mutableStateOf("") }
    var isLoadingItem by remember { mutableStateOf(idProjeto != null) }
    val isSaving by viewModel.isSaving.collectAsState()

    LaunchedEffect(idProjeto) {
        if (idProjeto != null) {
            isLoadingItem = true
            val projeto = viewModel.getProjeto(idProjeto)
            if (projeto != null) {
                nome = projeto.nome
                prazo = projeto.prazo
            }
            isLoadingItem = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (idProjeto == null) "Novo Projeto" else "Editar Projeto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack, enabled = !isSaving) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    ) { paddingValues ->
        if (isLoadingItem) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicatorScreen("Carregando dados do projeto...")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do Projeto") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = prazo,
                    onValueChange = { prazo = it },
                    label = { Text("Prazo (ex: 15/11/2026)") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onNavigateBack,
                        enabled = !isSaving
                    ) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            if (nome.isNotBlank()) {
                                val novoProjeto = Projeto(
                                    id = idProjeto ?: 0,
                                    nome = nome.trim(),
                                    prazo = prazo.trim().ifBlank { "Sem prazo" }
                                )
                                viewModel.salvarProjeto(novoProjeto) {
                                    onNavigateBack()
                                }
                            }
                        },
                        enabled = !isSaving && nome.isNotBlank()
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Salvando...")
                        } else {
                            Text("Salvar")
                        }
                    }
                }
            }
        }
    }
}
