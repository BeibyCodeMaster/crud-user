package restaurante.example.demo.persistence.dao.implementation.role;

import org.springframework.transaction.annotation.Transactional;
import restaurante.example.demo.persistence.dao.interfaces.role.IRoleDao;
import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.persistence.repositoy.role.IRoleRepository;

import java.util.List;
import java.util.Optional;

public class RoleDaoImpl implements IRoleDao {

    private IRoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleEntity> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleEntity> findById(Long id) {
        return this.roleRepository.findById(id);
    }

    @Override
    @Transactional
    public RoleEntity save(RoleEntity entity) {
        return this.roleRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.roleRepository.deleteById(id);
    }
}
