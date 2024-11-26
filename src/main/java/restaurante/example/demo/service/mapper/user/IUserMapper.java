package restaurante.example.demo.service.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.presentation.dto.user.*;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
// Interfaz que define el mapeo entre las entidades UserEntity y sus respectivos DTOs.
// Utiliza MapStruct para la generación automática de código de mapeo.
// Los DTOs incluyen diferentes tipos de roles de usuario: Administrator, Employee, Super, y Customer.
public interface IUserMapper extends ISourceTargetMapper<UserEntity, UserDto> {

        // Mapea una entidad UserEntity a un DTO para un administrador (AdministratorDto).
        // Cada atributo de UserEntity se mapea al correspondiente atributo en AdministratorDto.
        @Mapping(source = "userId", target = "user.userId")  // Mapea 'userId' de UserEntity a 'userId' en el DTO.
        @Mapping(source = "name", target = "user.name")
        @Mapping(source = "lastName", target = "user.lastName")
        @Mapping(source = "email", target = "user.email")
        @Mapping(source = "address", target = "user.address")
        @Mapping(source = "phone", target = "user.phone")
        @Mapping(source = "userName", target = "user.userName")
        @Mapping(source = "birthDate", target = "user.birthDate")
        @Mapping(source = "roleList", target = "user.roleList")  // Lista de roles del usuario.
        @Mapping(source = "password", target = "user.password")
        @Mapping(source = "createdAt", target = "user.createdAt")
        @Mapping(source = "updatedAt", target = "user.updatedAt")
        AdministratorDto userEntityToAdminstratorDto(UserEntity entity);

        // Mapea una entidad UserEntity a un DTO para un empleado (EmployeeDto).
        @Mapping(source = "userId", target = "user.userId")
        @Mapping(source = "name", target = "user.name")
        @Mapping(source = "lastName", target = "user.lastName")
        @Mapping(source = "email", target = "user.email")
        @Mapping(source = "address", target = "user.address")
        @Mapping(source = "phone", target = "user.phone")
        @Mapping(source = "userName", target = "user.userName")
        @Mapping(source = "birthDate", target = "user.birthDate")
        @Mapping(source = "roleList", target = "user.roleList")
        @Mapping(source = "password", target = "user.password")
        @Mapping(source = "createdAt", target = "user.createdAt")
        @Mapping(source = "updatedAt", target = "user.updatedAt")
        EmployeeDto userEntityToEmployeeDto(UserEntity entity);

        // Mapea una entidad UserEntity a un DTO para un superusuario (SuperDto).
        @Mapping(source = "userId", target = "user.userId")
        @Mapping(source = "name", target = "user.name")
        @Mapping(source = "lastName", target = "user.lastName")
        @Mapping(source = "email", target = "user.email")
        @Mapping(source = "address", target = "user.address")
        @Mapping(source = "phone", target = "user.phone")
        @Mapping(source = "userName", target = "user.userName")
        @Mapping(source = "birthDate", target = "user.birthDate")
        @Mapping(source = "roleList", target = "user.roleList")
        @Mapping(source = "password", target = "user.password")
        @Mapping(source = "createdAt", target = "user.createdAt")
        @Mapping(source = "updatedAt", target = "user.updatedAt")
        SuperDto userEntityToSuperDto(UserEntity entity);

        // Mapea una entidad UserEntity a un DTO para un cliente (CustomerDto).
        @Mapping(source = "userId", target = "user.userId")
        @Mapping(source = "name", target = "user.name")
        @Mapping(source = "lastName", target = "user.lastName")
        @Mapping(source = "email", target = "user.email")
        @Mapping(source = "address", target = "user.address")
        @Mapping(source = "phone", target = "user.phone")
        @Mapping(source = "userName", target = "user.userName")
        @Mapping(source = "birthDate", target = "user.birthDate")
        @Mapping(source = "roleList", target = "user.roleList")
        @Mapping(source = "password", target = "user.password")
        @Mapping(source = "createdAt", target = "user.createdAt")
        @Mapping(source = "updatedAt", target = "user.updatedAt")
        CustomerDto userEntityToCustomerDto(UserEntity entity);

}