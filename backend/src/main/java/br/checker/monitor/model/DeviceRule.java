package br.checker.monitor.model;

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

    private Boolean enabled = true;

}