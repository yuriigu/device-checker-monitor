package br.checker.monitor.model;

import jakarta.persistence.*;
import java.util.List;
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

    @Column(name = "name", length = 255, unique = true, nullable = false)
    private String name;

    @Column(name = "occurrence", length = 255, nullable = false)
    private String occurrence;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", length = 20, nullable = false) //nao precisa de 255 visto que os textos são predefinidos e nao passam de 10 (20 por precaucao)
    private RuleSeverity severity;

    @ManyToOne(fetch = FetchType.LAZY) //relacionamento de 1 regra com varios dispositivos
    @JoinColumn(name = "device_id")
    private Device device;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_channel", nullable = false) //tipo de notificacao (se vai ser telegram, gmail etc.)
    private String notificationChannel;

    @ManyToMany(fetch = FetchType.LAZY) //query que vai montar uma lista dos usuarios que devem ser notificados.
    @JoinTable(
            name = "rule_notified_users",
            joinColumns = @JoinColumn(name = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> notifiedUsers;

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

    public boolean isApplyToAll() { return applyToAll; } //flag booleana para dizer se a regra é aplicavel ou não á todos.
    public void setApplyToAll(boolean applyToAll) { this.applyToAll = applyToAll; }

    public String getNotificationChannel() { return notificationChannel; }
    public void setNotificationChannel(String notificationChannel) { this.notificationChannel = notificationChannel; }

    public List<User> getNotifiedUsers() { return notifiedUsers; }
    public void setNotifiedUsers(List<User> notifiedUsers) { this.notifiedUsers = notifiedUsers; }




}
