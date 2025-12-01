

CREATE DATABASE pooProject;

CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    primeiro_nome VARCHAR(100) NOT NULL,
    ultimo_nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cliente (
    id_usuario INTEGER PRIMARY KEY,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE administrador (
    id_administrador SERIAL PRIMARY KEY,
    id_usuario INTEGER UNIQUE NOT NULL,
    credencial VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE email (
    id_email SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE telefone (
    id_telefone SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    numero VARCHAR(20) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE plano (
    id_plano SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    limite_telas INTEGER NOT NULL,
    periodicidade VARCHAR(50) NOT NULL
);

CREATE TABLE assinatura (
    id_assinatura SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_plano INTEGER NOT NULL,
    duracao INTEGER NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('Ativo', 'Desativado')),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_plano) REFERENCES plano(id_plano) ON DELETE RESTRICT
);

CREATE TABLE conteudo (
    id_conteudo SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    ano_lancamento INTEGER NOT NULL,
    classificacao_etaria VARCHAR(10) NOT NULL,
    sinopse TEXT,
    id_usuario INTEGER NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE RESTRICT
);

CREATE TABLE filme (
    id_conteudo INTEGER PRIMARY KEY,
    diretor VARCHAR(255),
    roteirista VARCHAR(255),
    FOREIGN KEY (id_conteudo) REFERENCES conteudo(id_conteudo) ON DELETE CASCADE
);

CREATE TABLE serie (
    id_conteudo INTEGER PRIMARY KEY,
    qtd_temporadas INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (id_conteudo) REFERENCES conteudo(id_conteudo) ON DELETE CASCADE
);

CREATE TABLE animacao (
    id_conteudo INTEGER PRIMARY KEY,
    studio VARCHAR(255),
    FOREIGN KEY (id_conteudo) REFERENCES conteudo(id_conteudo) ON DELETE CASCADE
);

CREATE TABLE documentario (
    id_conteudo INTEGER PRIMARY KEY,
    FOREIGN KEY (id_conteudo) REFERENCES conteudo(id_conteudo) ON DELETE CASCADE
);

CREATE TABLE temporada (
    id_temporada SERIAL PRIMARY KEY,
    id_serie INTEGER NOT NULL,
    numero_temp INTEGER NOT NULL,
    ano_temp INTEGER NOT NULL,
    FOREIGN KEY (id_serie) REFERENCES serie(id_conteudo) ON DELETE CASCADE,
    UNIQUE(id_serie, numero_temp)
);

CREATE TABLE episodio (
    id_episodio SERIAL PRIMARY KEY,
    id_temporada INTEGER NOT NULL,
    num_episodio INTEGER NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    duracao DECIMAL(5, 2),
    FOREIGN KEY (id_temporada) REFERENCES temporada(id_temporada) ON DELETE CASCADE,
    UNIQUE(id_temporada, num_episodio)
);

CREATE TABLE avaliacao (
    id_avaliacao SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_conteudo INTEGER NOT NULL,
    nota DECIMAL(3, 1) NOT NULL CHECK (nota >= 0 AND nota <= 10),
    comentario TEXT,
    data_avaliacao DATE NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_conteudo) REFERENCES conteudo(id_conteudo) ON DELETE CASCADE,
    UNIQUE(id_usuario, id_conteudo)
);

CREATE TABLE historico (
    id_historico SERIAL PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_conteudo INTEGER NOT NULL,
    data_visualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    progresso DECIMAL(5, 2) NOT NULL DEFAULT 0 CHECK (progresso >= 0 AND progresso <= 100),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_conteudo) REFERENCES conteudo(id_conteudo) ON DELETE CASCADE
);

CREATE TABLE assinatura_libera_conteudo (
    id_assinatura INTEGER NOT NULL,
    id_conteudo INTEGER NOT NULL,
    PRIMARY KEY (id_assinatura, id_conteudo),
    FOREIGN KEY (id_assinatura) REFERENCES assinatura(id_assinatura) ON DELETE CASCADE,
    FOREIGN KEY (id_conteudo) REFERENCES conteudo(id_conteudo) ON DELETE CASCADE
);

CREATE INDEX idx_assinatura_usuario ON assinatura(id_usuario);
CREATE INDEX idx_assinatura_plano ON assinatura(id_plano);
CREATE INDEX idx_conteudo_usuario ON conteudo(id_usuario);
CREATE INDEX idx_avaliacao_conteudo ON avaliacao(id_conteudo);
CREATE INDEX idx_avaliacao_usuario ON avaliacao(id_usuario);
CREATE INDEX idx_historico_usuario ON historico(id_usuario);
CREATE INDEX idx_historico_conteudo ON historico(id_conteudo);
CREATE INDEX idx_temporada_serie ON temporada(id_serie);
CREATE INDEX idx_episodio_temporada ON episodio(id_temporada);
CREATE INDEX idx_email_usuario ON email(id_usuario);
CREATE INDEX idx_telefone_usuario ON telefone(id_usuario);
CREATE INDEX idx_assinatura_libera_conteudo_assinatura ON assinatura_libera_conteudo(id_assinatura);
CREATE INDEX idx_assinatura_libera_conteudo_conteudo ON assinatura_libera_conteudo(id_conteudo);

