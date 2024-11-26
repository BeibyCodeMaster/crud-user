package restaurante.example.demo.persistence.repositoy.role;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurante.example.demo.persistence.model.role.RoleEntity;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    
}
