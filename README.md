# Biblioteca — TDD

Projeto de biblioteca: cadastro de livros e usuários e empréstimo de livros.

## Regras de negócio

### Cadastro de Livro
- Não pode cadastrar livro sem título ou autor.
- `quantidadeTotal` deve ser ≥ 1.
- `quantidadeDisponivel` inicia igual à `quantidadeTotal`.

### Cadastro de Usuário
- Email deve ser único e válido (regex).
- Nome não pode ser vazio.

### Regra de Empréstimo
- Usuário pode pegar no máximo 3 livros ativos.
- Não pode emprestar se: não houver exemplares disponíveis ou usuário já tiver 3 empréstimos ativos.
- Ao emprestar: diminui `quantidadeDisponivel`, cria registro de empréstimo ativo.

### Devolução
Regras:
- Não pode devolver se: empréstimo já estiver devolvido.
- Ao devolver: altera status para DEVOLVIDO; preenche `dataDevolucao`; aumenta `quantidadeDisponivel` do livro.
