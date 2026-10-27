package br.checker.monitor.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidade que representa a tabela {@code brands}.
 *
 * <p>Uma marca de dispositivo (ex: Dell, HP, Cisco, Samsung).
 * Cada marca pode ter zero ou mais modelos associados (relação 1:N com DeviceModel).
 *
 * <p>Anotações JPA usadas:
 * <ul>
 *   <li>{@link Entity}: marca esta classe como uma entidade JPA, ou seja, uma tabela do banco de dados.</li>
 *   <li>{@link Table}: especifica o nome da tabela no banco ({@code brands}).</li>
 *   <li>{@link Id}: define o campo como chave primária (PK).</li>
 *   <li>{@link GeneratedValue(strategy = IDENTITY)}: o banco (PostgreSQL) gera o ID automaticamente
 *       usando uma sequence/identity column.</li>
 *   <li>{@link Column}: mapeia o campo para uma coluna específica da tabela.</li>
 * </ul>
 */
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 255)
    private String name;

    // Construtor padrão requerido pelo JPA (Hibernate usa reflection para instanciar entidades)
    public Brand() {
    }

    public Brand(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Brand brand))
            return false;
        return Objects.equals(id, brand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Brand{id=" + id + ", name='" + name + "'}";
    }
}