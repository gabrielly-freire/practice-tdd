# Biblioteca — TDD

Este projeto implementa um sistema simples de gerenciamento de biblioteca, com funcionalidades de cadastro de livros e usuários, além de empréstimo e devolução de livros.

O principal objetivo é praticar o desenvolvimento orientado por testes (TDD — Test Driven Development), aplicando boas práticas de design, validações de regras de negócio e escrita de testes unitários com foco na qualidade do código.

A proposta é construir a aplicação a partir dos testes, escrevendo primeiro o teste que representa uma regra de negócio, e só depois a implementação mínima para fazê-lo passar, seguindo o ciclo Red → Green → Refactor.

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
- Não pode devolver se: empréstimo já estiver devolvido.
- Ao devolver: altera status para DEVOLVIDO; preenche `dataDevolucao`; aumenta `quantidadeDisponivel` do livro.
