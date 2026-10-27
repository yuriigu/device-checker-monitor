-- ============================================================================
-- Migration V2: Criação das tabelas de dispositivos (devices, devices_roles)
-- ============================================================================

-- Tabela de dispositivos cadastrados no monitor
CREATE TABLE devices (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    id_brand BIGINT NOT NULL,
    id_model BIGINT,
    ip_address VARCHAR(45) NOT NULL UNIQUE,
    mac_address VARCHAR(17),
    id_place BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_device_brand FOREIGN KEY (id_brand) REFERENCES brands(id),
    CONSTRAINT fk_device_model FOREIGN KEY (id_model) REFERENCES models(id),
    CONSTRAINT fk_device_place FOREIGN KEY (id_place) REFERENCES places(id)
);

-- Índices para melhorar performance de consultas frequentes
CREATE INDEX idx_devices_ip_address ON devices(ip_address);
CREATE INDEX idx_devices_brand ON devices(id_brand);
CREATE INDEX idx_devices_model ON devices(id_model);
CREATE INDEX idx_devices_place ON devices(id_place);

-- Tabela associativa N:N entre dispositivos e roles, com atributos próprios.
-- Chave primária composta: (id_device, id_role).
-- time_start/time_end definem o horário de atuação da role para aquele dispositivo.
-- active indica se a vinculação está ativa ou não.
CREATE TABLE devices_roles (
    id_device BIGINT NOT NULL,
    id_role BIGINT NOT NULL,
    time_start TIME,
    time_end TIME,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_device, id_role),
    CONSTRAINT fk_device_role_device FOREIGN KEY (id_device) REFERENCES devices(id),
    CONSTRAINT fk_device_role_role FOREIGN KEY (id_role) REFERENCES roles(id)
);

-- Índice para buscar dispositivos por role rapidamente
CREATE INDEX idx_devices_roles_role ON devices_roles(id_role);

-- Insere roles padrão do sistema
INSERT INTO roles (name, description) VALUES ('ADMIN', 'Administrador com acesso total ao sistema');
INSERT INTO roles (name, description) VALUES ('USER', 'Usuário padrão com acesso a dispositivos');
INSERT INTO roles (name, description) VALUES ('GUEST', 'Visitante com acesso restrito');