package restaurante.example.demo.service.mapper.role;

import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.presentation.dto.role.RoleDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

public interface IRoleMapper  extends ISourceTargetMapper<RoleEntity, RoleDto> {
}
