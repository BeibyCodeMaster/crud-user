package restaurante.example.demo.persistence.model.user;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurante.example.demo.persistence.model.role.RoleEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="usuario")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long userId; // Identificador único del usuario. Se genera automáticamente.

    @Column(name = "nombre", nullable = false)
    private String name; // Nombre del usuario. No puede ser nulo.

    @Column(name = "apellido", nullable = false)
    private String lastName; // Apellido del usuario. No puede ser nulo.

    @Column(name = "correo_electronico", unique = true, nullable = false)
    private String email; // Correo electrónico del usuario. Es único y no puede ser nulo.

    @Column(name = "direccion")
    private String address; // Dirección del usuario.

    @Column(name = "telefono")
    private String phone; // Número de teléfono del usuario.

    @Column(name = "clave", nullable = false)
    private String password; // Contraseña del usuario. No puede ser nula.

    @Column(name = "nombre_usuario", nullable = false)
    private String userName; // Nombre de usuario. No puede ser nulo.

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date birthDate; // Fecha de nacimiento del usuario.

    @Column(name = "creado_en", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt; // Fecha de creación del usuario.

    @Column(name = "actualizado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt; // Fecha de última actualización del usuario.

    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name="usuario_rol",
            joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name="rol_id")
    )
    private Set<RoleEntity> roleList; // Lista de roles asignados al usuario. Relación muchos a muchos con RoleEntity.
}


