package restaurante.example.demo.persistence.repositoy.user;

import org.springframework.data.repository.CrudRepository;
import restaurante.example.demo.persistence.model.user.SuperEntity;

public interface ISuperRepository  extends CrudRepository<SuperEntity, Long> {
    
}
