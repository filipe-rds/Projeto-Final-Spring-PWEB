# Study Manager - API de Usuários

A API de Usuários do **Study Manager** permite o cadastro, login, atualização, exclusão e busca de usuários, além da listagem de todos os usuários cadastrados. Esta API foi desenvolvida utilizando **Spring Boot** e está preparada para operações CRUD e autenticação básica.

## Endpoints

### Cadastro de Usuário
- **POST** `/usuarios`
- Adiciona um novo usuário.
- **Corpo da Requisição**: `UsuarioCadastroDTO`
- **Respostas**:
  - **201**: Usuário criado com sucesso.
  - **409**: Usuário já existe.
  - **400**: Dados inválidos.

### Atualização de Usuário
- **PATCH** `/usuarios/{id}`
- Atualiza um usuário existente.
- **Corpo da Requisição**: `UsuarioAtualizacaoDTO`
- **Respostas**:
  - **200**: Usuário atualizado.
  - **404**: Usuário não encontrado.
  - **400**: Dados inválidos.

### Exclusão de Usuário
- **DELETE** `/usuarios/{id}`
- Remove um usuário pelo ID.
- **Respostas**:
  - **204**: Usuário removido.
  - **404**: Usuário não encontrado.

### Busca de Usuário por ID
- **GET** `/usuarios/{id}`
- Retorna um usuário pelo ID.
- **Respostas**:
  - **200**: Usuário encontrado.
  - **404**: Usuário não encontrado.

### Listar Usuários
- **GET** `/usuarios`
- Retorna uma lista de todos os usuários.
- **Respostas**:
  - **200**: Lista de usuários.

### Login de Usuário
- **POST** `/usuarios/login`
- Realiza o login de um usuário.
- **Corpo da Requisição**: `UsuarioLoginDTO`
- **Respostas**:
  - **200**: Login bem-sucedido.
  - **401**: Falha no login.

## Tratamento de Erros
- **400**: Requisição inválida.
- **404**: Recurso não encontrado.
- **409**: Conflito (usuário já existe).
- **500**: Erro interno no servidor.

