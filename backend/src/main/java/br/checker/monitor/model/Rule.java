package br.checker.monitor.model;

import jakarta.persistence.*;
import br.checker.monitor.enums.RuleSeverity;

@Entity
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255, unique = true, nullable = false)
    private String name;

    @Column(name = "occurrence", length = 255, nullable = false)
    private String occurrence;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", length = 20, nullable = false)
    private RuleSeverity severity;

    // flag booleana para dizer se a regra é aplicavel ou nao a todos
    @Column(name = "apply_to_all", nullable = false)
    private boolean applyToAll = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "notification_channel", nullable = false)
    private String notificationChannel;

    public Rule() {}

    // --- Getters e Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOccurrence() { return occurrence; }
    public void setOccurrence(String occurrence) { this.occurrence = occurrence; }

    public RuleSeverity getSeverity() { return severity; }
    public void setSeverity(RuleSeverity severity) { this.severity = severity; }

    public Device getDevice() { return device; }
    public void setDevice(Device device) { this.device = device; }

    public Place getPlace() { return place; }
    public void setPlace(Place place) { this.place = place; }

    public boolean isApplyToAll() { return applyToAll; }
    public void setApplyToAll(boolean applyToAll) { this.applyToAll = applyToAll; }

    public String getNotificationChannel() { return notificationChannel; }
    public void setNotificationChannel(String notificationChannel) { this.notificationChannel = notificationChannel; }
}