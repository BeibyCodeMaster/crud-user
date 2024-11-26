package restaurante.example.demo.service.mapper.user;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.presentation.dto.user.CustomerDto;
import restaurante.example.demo.presentation.dto.user.UserDto;
import restaurante.example.demo.service.mapper.common.ISourceTargetMapper;

@Mapper(componentModel = "spring")
public interface ICustomerMapper extends ISourceTargetMapper<CustomerEntity, CustomerDto> {

    @Mapping(source = "dateTimeActive.startDate", target = "startDate")
    @Mapping(source = "dateTimeActive.endDate", target = "endDate")
    @Mapping(source = "dateTimeActive.state", target = "state")
    @Mapping(source = "user.roleList", target = "user.roleList")
    @Mapping(source = "customerId", target = "customerId")
    @Override
    CustomerDto entityToDto(CustomerEntity entity);

    @Mapping(source = "startDate", target = "dateTimeActive.startDate")
    @Mapping(source = "endDate", target = "dateTimeActive.endDate")
    @Mapping(source = "state", target = "dateTimeActive.state")
    @Mapping(source = "user.roleList", target = "user.roleList")
    @Mapping(source = "customerId", target = "customerId")
    @Override
    CustomerEntity dtoToEntity(CustomerDto dto);

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.address", target = "address")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.userName", target = "userName")
    @Mapping(source = "user.birthDate", target = "birthDate")
    @Mapping(source = "user.roleList", target = "roleList")
    @Mapping(source = "user.password", target = "password")
    @Mapping(source = "user.createdAt", target = "createdAt")
    @Mapping(source = "user.updatedAt", target = "updatedAt")
    UserDto customerDtoToUserDto(CustomerDto dto);

}
