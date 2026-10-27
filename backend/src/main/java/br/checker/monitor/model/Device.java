package br.checker.monitor.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entidade que representa a tabela {@code devices}.
 *
 * <p>Substitui a classe esquelética existente, adicionando todos os campos, anotações JPA
 * e relacionamentos necessários para a HU001 (Cadastrar dispositivo).
 *
 * <p>Relacionamentos:
 * <ul>
 *   <li>{@code brand} (ManyToOne, obrigatório): cada dispositivo pertence a exatamente uma marca.</li>
 *   <li>{@code model} (ManyToOne, opcional): um dispositivo pode ou não ter um modelo específico.</li>
 *   <li>{@code place} (ManyToOne, opcional): um dispositivo pode estar alocado a um local.</li>
 *   <li>{@code deviceRoles} (OneToMany): um dispositivo possui zero ou mais vínculos DeviceRole.</li>
 * </ul>
 *
 * <p>Anotações de ciclo de vida:
 * <ul>
 *   <li>{@link PrePersist}: executado antes de salvar (INSERT) — preenche {@code createdAt} e {@code updatedAt}.</li>
 *   <li>{@link PreUpdate}: executado antes de atualizar (UPDATE) — preenche {@code updatedAt}.</li>
 * </ul>
 */
@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_brand", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_model")
    private DeviceModel model;

    @Column(name = "ip_address", nullable = false, unique = true, length = 45)
    private String ipAddress;

    @Column(name = "mac_address", length = 17)
    private String macAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_place")
    private Place place;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DeviceRole> deviceRoles = new HashSet<>();

    public Device() {
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public DeviceModel getModel() {
        return model;
    }

    public void setModel(DeviceModel model) {
        this.model = model;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<DeviceRole> getDeviceRoles() {
        return deviceRoles;
    }

    public void setDeviceRoles(Set<DeviceRole> deviceRoles) {
        this.deviceRoles = deviceRoles;
    }

    /**
     * Adiciona um DeviceRole ao dispositivo, mantendo a consistência dos dois lados do relacionamento.
     */
    public void addDeviceRole(DeviceRole deviceRole) {
        deviceRoles.add(deviceRole);
        deviceRole.setDevice(this);
    }

    /**
     * Remove um DeviceRole do dispositivo.
     */
    public void removeDeviceRole(DeviceRole deviceRole) {
        deviceRoles.remove(deviceRole);
        deviceRole.setDevice(null);
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Device device))
            return false;
        return Objects.equals(id, device.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Device{id=" + id + ", name='" + name + "', ipAddress='" + ipAddress + "'}";
    }
}
