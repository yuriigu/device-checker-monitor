package br.checker.monitor.model;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Entidade que representa a tabela associativa {@code devices_roles}.
 *
 * <p>Esta tabela implementa um relacionamento N:N com atributos entre {@link Device} e {@link Role}.
 * Além das duas chaves estrangeiras, armazena:
 * <ul>
 *   <li>{@code time_start}: horário de início de atuação da role para este dispositivo.</li>
 *   <li>{@code time_end}: horário de término de atuação.</li>
 *   <li>{@code active}: indica se o vínculo está ativo.</li>
 * </ul>
 *
 * <p>Anotações JPA:
 * <ul>
 *   <li>{@link EmbeddedId}: a PK é embutida ({@link DeviceRoleId}).</li>
 *   <li>{@link ManyToOne} + {@link MapsId}: mapeia o relacionamento com Device e Role,
 *       usando {@code MapsId} para dizer que a FK também preenche parte da PK embarcada.</li>
 * </ul>
 */
@Entity
@Table(name = "devices_roles")
public class DeviceRole {

    @EmbeddedId
    private DeviceRoleId id;

    @Column(name = "time_start")
    private LocalTime timeStart;

    @Column(name = "time_end")
    private LocalTime timeEnd;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("idDevice")
    @JoinColumn(name = "id_device", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("idRole")
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

    public DeviceRole() {
    }

    public DeviceRole(DeviceRoleId id, Device device, Role role, LocalTime timeStart,
                      LocalTime timeEnd, Boolean active) {
        this.id = id;
        this.device = device;
        this.role = role;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.active = active != null ? active : true;
    }

    public DeviceRoleId getId() {
        return id;
    }

    public void setId(DeviceRoleId id) {
        this.id = id;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DeviceRole that))
            return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DeviceRole{id=" + id + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd
            + ", active=" + active + "}";
    }
}