package br.checker.monitor.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidade que representa a tabela {@code roles}.
 *
 * <p>Um papel de acesso (ex: ADMIN, USER, GUEST) que pode ser atribuído a dispositivos.
 *
 * <p>Anotações:
 * <ul>
 *   <li>{@link Entity}: marca como entidade JPA.</li>
 *   <li>{@link Table(name = "roles")}: nome da tabela no banco.</li>
 *   <li>{@link Column(unique = true)}: o nome da role deve ser único no sistema.</li>
 * </ul>
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    public Role() {
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Role role))
            return false;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Role{id=" + id + ", name='" + name + "'}";
    }
}