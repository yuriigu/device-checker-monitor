package br.checker.monitor.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidade que representa a tabela {@code models}.
 *
 * <p>Um modelo de dispositivo pertence a uma marca ({@link Brand}) e pode estar associado
 * a zero ou mais dispositivos ({@link Device}).
 *
 * <p>Anotações JPA importantes:
 * <ul>
 *   <li>{@link ManyToOne}: relacionamento muitos-para-um. Vários modelos podem pertencer à mesma marca.</li>
 *   <li>{@link JoinColumn(name = "id_brand")}: cria a coluna FK {@code id_brand} na tabela models.</li>
 *   <li>{@link UniqueConstraint}: garante unicidade composta de (id_brand, name), ou seja,
 *       não pode haver dois modelos com o mesmo nome para a mesma marca.</li>
 * </ul>
 */
@Entity
@Table(
    name = "models",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_brand", "name"})
)
public class DeviceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_brand", nullable = false)
    private Brand brand;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    public DeviceModel() {
    }

    public DeviceModel(Brand brand, String name) {
        this.brand = brand;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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
        if (!(o instanceof DeviceModel that))
            return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeviceModel{id=" + id + ", brand=" + (brand != null ? brand.getId() : null)
            + ", name='" + name + "'}";
    }
}