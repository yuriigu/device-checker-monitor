package br.checker.monitor.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidade que representa a tabela {@code places}.
 *
 * <p>Um local físico (predio, sala, setor) com suporte a hierarquia autorrelacionada:
 * um lugar pode ter um lugar pai ({@code parent_id}), permitindo estruturas como
 * "Predio A > Sala 101 > Armario 5".
 *
 * <p>Regra de negócio: apenas lugares "raiz" ({@code parent_id == null})
 * podem ter um endereço físico cadastrado em {@link PlaceAddress}.
 *
 * <p>Anotações:
 * <ul>
 *   <li>{@link ManyToOne} (auto-relacionamento): um lugar tem no máximo um lugar pai.</li>
 *   <li>{@link OneToOne(mappedBy = "place")}: um lugar tem no máximo um endereço.
 *       O mapeamento é "inverso" — quem detém a FK é PlaceAddress.</li>
 * </ul>
 */
@Entity
@Table(name = "places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Place parent;

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private PlaceAddress address;

    public Place() {
    }

    public Place(String name, Place parent) {
        this.name = name;
        this.parent = parent;
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

    public Place getParent() {
        return parent;
    }

    public void setParent(Place parent) {
        this.parent = parent;
    }

    public PlaceAddress getAddress() {
        return address;
    }

    public void setAddress(PlaceAddress address) {
        this.address = address;
    }

    /**
     * Indica se este lugar é raiz, ou seja, não possui lugar pai.
     * Apenas lugares raiz podem ter endereço físico.
     */
    public boolean isRoot() {
        return parent == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Place place))
            return false;
        return Objects.equals(id, place.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Place{id=" + id + ", name='" + name + "', parentId="
            + (parent != null ? parent.getId() : null) + "}";
    }
}