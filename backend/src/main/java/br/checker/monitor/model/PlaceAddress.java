package br.checker.monitor.model;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidade que representa a tabela {@code places_address}.
 *
 * <p>Endereço físico de um lugar. Usa um padrão chamado "shared primary key":
 * a PK desta tabela ({@code id}) é também a FK para {@code places.id}.
 * Isso garante o relacionamento 1:1 entre Place e PlaceAddress.
 *
 * <p>Anotações críticas:
 * <ul>
 *   <li>{@link Id}: o campo {@code id} é a chave primária.</li>
 *   <li>{@link OneToOne} + {@link MapsId}: indica que este @OneToOne compartilha
 *       a mesma chave primária da entidade relacionada ({@link Place}).
 *       O {@link MapsId} diz ao JPA que o valor de {@code id} vem do Place associado.</li>
 *   <li>{@link JoinColumn(name = "id")}: a coluna FK se chama {@code id} (mesmo nome da PK).</li>
 * </ul>
 */
@Entity
@Table(name = "places_address")
public class PlaceAddress {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Place place;

    @Column(name = "street", length = 255)
    private String street;

    @Column(name = "number", length = 20)
    private String number;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "zip_code", length = 20)
    private String zipCode;

    public PlaceAddress() {
    }

    public PlaceAddress(Place place, String street, String number, String city, String state, String zipCode) {
        this.place = place;
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PlaceAddress that))
            return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PlaceAddress{id=" + id + ", street='" + street + "', number='" + number
            + "', city='" + city + "', state='" + state + "', zipCode='" + zipCode + "'}";
    }
}