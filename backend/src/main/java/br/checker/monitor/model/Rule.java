package br.checker.monitor.model;

import jakarta.persistence.*;
import java.util.Objects;
import br.checker.monitor.enums.RuleSeverity;


/*
nome, ocorrência, severidade e os dispositivos ou locais afetados,
**/

@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Identity
    private Long id;

    @Column(name = "name", length = 255, unique = true)
    private String name;

    @Column(name = "occurrence", length = 255)
    private String occurrence;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", length = 255)
    private RuleSeverity severity;

    public Rule() {} //construtor vazio.

    //getters e setters

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

    public String getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(String occurrence) {
        this.occurrence = occurrence;
    }

    public RuleSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(RuleSeverity severity) {
        this.severity = severity;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
