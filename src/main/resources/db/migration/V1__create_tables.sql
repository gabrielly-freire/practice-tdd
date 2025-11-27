CREATE TABLE IF NOT EXISTS usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS livro (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    quantidade_total INTEGER NOT NULL,
    quantidade_disponivel INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS emprestimo (
    id BIGSERIAL PRIMARY KEY,
    livro_id BIGINT NOT NULL REFERENCES livro(id),
    usuario_id BIGINT NOT NULL REFERENCES usuario(id),
    data_emprestimo DATE,
    data_devolucao DATE,
    status VARCHAR(50) NOT NULL
);
