package restaurante.example.demo.persistence.model.role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.enums.NameRoleEnum;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="rol")
public class RoleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rol_id")
    private Long roleId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NameRoleEnum name;

}
