# PDM - Roteiro 10: Banco de Dados Local com SQLite e Room

Projeto desenvolvido para a disciplina de Programação para Dispositivos Móveis (PDM), com base no **Roteiro 10** (evolução do Roteiro 8 e Roteiro 9).

## 🚀 Tecnologias e Configurações
- **IDE**: Android Studio Otter 2 Feature Drop | 2025.2.2 Patch 1
- **Linguagem**: Kotlin 2.0.21
- **UI Toolkit**: Jetpack Compose (BOM 2024.09.00, Material 3)
- **Compilador / Build**: Gradle 8.13, AGP 8.13.2, JDK 21 (JBR 21)
- **Processamento de Anotações**: KSP 2.0.21-1.0.28
- **Persistência Local**: Room 2.6.1 (SQLite) com corrotinas e Kotlin Flow
- **Arquitetura**: MVVM com `AndroidViewModel` e Navigation Compose

---

## 📋 Requisitos Atendidos

### Requisitos do Roteiro 10
1. **Banco de Dados Local com Room (SQLite)**:
   - Substituição dos cadastros em memória (`mutableStateListOf`) por persistência em banco SQLite local via Room.
   - Entidades Room (`@Entity`):
     - `Tarefa`: tabela `tarefas` (colunas: `id` autoincrementado, `titulo`, `status`).
     - `Projeto`: tabela `projetos` (colunas: `id` autoincrementado, `nome`, `prazo`).
   - DAOs com corrotinas e Flow reativo:
     - `TarefaDao`: inserção (`OnConflictStrategy.REPLACE`), atualização, exclusão, busca por ID e listagem reativa via `Flow<List<Tarefa>>`.
     - `ProjetoDao`: inserção, atualização, exclusão, busca por ID e listagem reativa via `Flow<List<Projeto>>`.
   - `AppDatabase`: classe abstrata estendendo `RoomDatabase` com padrão Singleton thread-safe (`@Volatile` e `synchronized`).

2. **Indicadores de Carregamento (Loadings)**:
   - **Telas de Listagem (`ListaTarefasScreen` e `ListaProjetosScreen`)**: exibição de `CircularProgressIndicator` enquanto os dados do Room são consultados e emitidos.
   - **Telas de Formulário (`FormTarefaScreen` e `FormProjetoScreen`)**:
     - Loading ao abrir item existente para edição (busca assíncrona por ID no Room).
     - Loading no botão Salvar durante a inserção/atualização no banco, desabilitando os botões para prevenir múltiplos cliques e garantindo feedback visual claro ao usuário.

### Requisitos herdados do Roteiro 8
- **Single Activity**: Apenas uma Activity (`MainActivity`) contendo a navegação e Scaffold com Bottom Navigation.
- **Transições de Telas**: Navegação entre telas de publicação (formulários) e exibição (listagens) gerenciada via `NavHost` do Jetpack Compose.
- **Comunicação por ViewModel**: Comunicação centralizada via `AppViewModel` conectando a interface ao banco Room.
- **Tema Livre**: Gerenciamento de Tarefas e Projetos.

---

## 📁 Estrutura do Projeto

```
app/src/main/java/com/example/pdm_roteiro10/
├── data/
│   ├── AppDatabase.kt        # Banco de dados Room e Singleton
│   ├── Tarefa.kt             # Entidade Room para Tarefas
│   ├── Projeto.kt            # Entidade Room para Projetos
│   ├── TarefaDao.kt          # Interface DAO para operações em tarefas
│   └── ProjetoDao.kt         # Interface DAO para operações em projetos
├── ui/theme/
│   ├── Color.kt              # Cores da aplicação
│   ├── Theme.kt              # Pdm_roteiro10Theme
│   └── Type.kt               # Tipografia Material 3
├── AppViewModel.kt           # ViewModel (operações assíncronas e StateFlows)
├── Componentes.kt            # Itens de lista, diálogo de exclusão e loading
├── MainActivity.kt           # Activity única e controle de navegação
├── TelasFormulario.kt        # Telas de cadastro/edição (publicação de dados) com loadings
└── TelasListagem.kt          # Telas de listagem (exibição de dados) com loadings
```

---

## 🛠️ Como executar

1. Abra o projeto no **Android Studio Otter 2 Feature Drop | 2025.2.2 Patch 1**.
2. Aguarde a sincronização automática do Gradle.
3. Conecte um dispositivo físico ou inicie um emulador Android (API 24 ou superior).
4. Clique em **Run 'app'** (`Shift + F10`).
