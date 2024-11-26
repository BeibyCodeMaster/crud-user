package restaurante.example.demo.persistence.dao.interfaces.user;

import restaurante.example.demo.persistence.dao.interfaces.common.ICrudDao;
import restaurante.example.demo.persistence.model.user.UserEntity;

// Interfaz que define las operaciones básicas de acceso a datos para la entidad "Usuario".
public interface IUserDao extends ICrudDao<UserEntity> {
    // Aquí puedes definir operaciones personalizadas si es necesario.
}
