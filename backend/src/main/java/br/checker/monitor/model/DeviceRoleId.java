package br.checker.monitor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Classe {@code @Embeddable} que representa a chave primária composta da tabela {@code devices_roles}.
 *
 * <p>Uma chave composta (ou composite key) é formada por mais de um campo.
 * Neste caso, a PK de devices_roles é o par ({@code id_device}, {@code id_role}).
 *
 * <p>Para ser usada como @Embeddable, a classe deve:
 * <ul>
 *   <li>Implementar {@link Serializable}.</li>
 *   <li>Implementar {@link equals} e {@link hashCode} baseado em todos os campos da chave.</li>
 *   <li>Ter um construtor padrão (sem argumentos).</li>
 * </ul>
 *
 * <p>Esta classe é embutida em {@link DeviceRole} via {@code @EmbeddedId}.
 */
@Embeddable
public class DeviceRoleId implements Serializable {

    @Column(name = "id_device")
    private Long idDevice;

    @Column(name = "id_role")
    private Long idRole;

    public DeviceRoleId() {
    }

    public DeviceRoleId(Long idDevice, Long idRole) {
        this.idDevice = idDevice;
        this.idRole = idRole;
    }

    public Long getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Long idDevice) {
        this.idDevice = idDevice;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DeviceRoleId that))
            return false;
        return Objects.equals(idDevice, that.idDevice) && Objects.equals(idRole, that.idRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDevice, idRole);
    }

    @Override
    public String toString() {
        return "DeviceRoleId{idDevice=" + idDevice + ", idRole=" + idRole + "}";
    }
}