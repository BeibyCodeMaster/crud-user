package restaurante.example.demo.persistence.repositoy.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restaurante.example.demo.persistence.model.user.UserEntity;

@Repository
// Interfaz que extiende de CrudRepository, proporcionando un conjunto de operaciones CRUD básicas para la entidad admin.
public interface IUserRepository extends CrudRepository<UserEntity, Long> {
    // Aquí puedes definir sentencias personalizadas para consultas específicas sobre la entidad admin, si es necesario.
}
