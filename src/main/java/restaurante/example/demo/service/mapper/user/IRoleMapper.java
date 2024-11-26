package restaurante.example.demo.service.mapper.user;

import org.mapstruct.Mapper;
import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.presentation.dto.role.RoleDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface IRoleMapper extends ISourceTargetMapper<RoleEntity, RoleDto> {
}
