package br.checker.monitor.model;

import jakarta.persistence.*;

@Entity
@Table(name = "device_rules")
public class DeviceRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_device", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rule", nullable = false)
    private Rule rule;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    DeviceRule() {}

    //getters e setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }
    public void setDevice(Device device) {
        this.device = device;
    }

    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Rule getRule() {
        return rule;
    }
    public void setRule(Rule rule) {
        this.rule = rule;
    }
}