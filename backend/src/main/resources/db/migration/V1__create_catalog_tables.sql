-- ============================================================================
-- Migration V1: Criação das tabelas de catálogo (brands, models, roles, places, places_address)
-- ============================================================================

-- Tabela de marcas de dispositivos (ex: Dell, HP, Cisco, Samsung)
CREATE TABLE brands (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela de modelos de dispositivos (ex: Inspiron 15, EliteBook 840)
-- id_brand é FK para brands.
-- Unicidade composta: (id_brand, name) impede cadastro de modelo repetido para a mesma marca.
CREATE TABLE models (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_brand BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_model_brand FOREIGN KEY (id_brand) REFERENCES brands(id),
    CONSTRAINT uk_model_brand_name UNIQUE (id_brand, name)
);

-- Tabela de perfis de acesso (ex: ADMIN, USER, GUEST)
CREATE TABLE roles (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500)
);

-- Tabela de locais (predios, salas, setores) com suporte a hierarquia autorrelacionada.
-- parent_id aponta para outro lugar pai (ex: sala 101 fica dentro do predio A).
-- Se parent_id IS NULL, o lugar é considerado "raiz".
CREATE TABLE places (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    CONSTRAINT fk_place_parent FOREIGN KEY (parent_id) REFERENCES places(id)
);

-- Tabela de endereço físico de um lugar.
-- O id desta tabela é PK e também FK para places.id (relacionamento 1:1 compartilhando a PK).
-- Regra de negócio: apenas lugares raiz (parent_id == null) podem ter endereço.
CREATE TABLE places_address (
    id BIGINT PRIMARY KEY,
    street VARCHAR(255),
    number VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    CONSTRAINT fk_place_address_place FOREIGN KEY (id) REFERENCES places(id)
);