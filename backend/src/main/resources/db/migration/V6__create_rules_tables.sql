CREATE TABLE rules (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE,
                       occurrence VARCHAR(255) NOT NULL,
                       severity VARCHAR(20) NOT NULL,
                       apply_to_all BOOLEAN NOT NULL DEFAULT FALSE,
                       notification_channel VARCHAR(255) NOT NULL
);

CREATE TABLE device_rules (
                              id BIGSERIAL PRIMARY KEY,
                              id_device BIGINT NOT NULL,
                              id_rule BIGINT NOT NULL,
                              enabled BOOLEAN NOT NULL DEFAULT TRUE,
                              CONSTRAINT fk_device_rules_device FOREIGN KEY (id_device) REFERENCES devices(id) ON DELETE CASCADE,
                              CONSTRAINT fk_device_rules_rule FOREIGN KEY (id_rule) REFERENCES rules(id) ON DELETE CASCADE,
                              CONSTRAINT uk_device_rule UNIQUE (id_device, id_rule)
);

CREATE TABLE place_rules (
                             id BIGSERIAL PRIMARY KEY,
                             id_place BIGINT NOT NULL,
                             id_rule BIGINT NOT NULL,
                             enabled BOOLEAN NOT NULL DEFAULT TRUE,
                             CONSTRAINT fk_place_rules_place FOREIGN KEY (id_place) REFERENCES places(id) ON DELETE CASCADE,
                             CONSTRAINT fk_place_rules_rule FOREIGN KEY (id_rule) REFERENCES rules(id) ON DELETE CASCADE,
                             CONSTRAINT uk_place_rule UNIQUE (id_place, id_rule)
);