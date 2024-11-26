package restaurante.example.demo.service.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.error.EntityDataAccesException;
import restaurante.example.demo.error.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.user.ICustomerDao;
import restaurante.example.demo.persistence.dao.interfaces.user.IUserDao;
import restaurante.example.demo.persistence.model.common.DateTimeActive;
import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.persistence.enums.ActiveEnum;
import restaurante.example.demo.persistence.enums.NameRoleEnum;
import restaurante.example.demo.presentation.dto.user.CustomerDto;
import restaurante.example.demo.presentation.dto.user.UserDto;
import restaurante.example.demo.service.interfaces.user.ICustomerService;
import restaurante.example.demo.service.mapper.user.ICustomerMapper;
import restaurante.example.demo.service.mapper.user.IUserMapper;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
// Implementación del servicio que maneja la lógica de negocio para la entidad Cliente.
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private IUserDao userDao;  // DAO para acceder a la base de datos para la entidad Usuario.
    @Autowired
    private ICustomerDao customerDao;  // DAO para acceder a la base de datos para la entidad Cliente.
    @Autowired
    private ICustomerMapper customerMapper;  // Mapper para convertir entre entidad Cliente y DTO.
    @Autowired
    private IUserMapper userMapper;  // Mapper para convertir entre entidad Usuario y DTO.

    @Override
    // Método que obtiene todos los clientes y los convierte en DTOs.
    public List<CustomerDto> getAll() {
        return this.customerDao.findAll().stream()
                .map(customerMapper::entityToDto)  // Convierte cada entidad de cliente a DTO.
                .toList();
    }

    @Override
    // Método que obtiene un cliente por su ID y lo convierte en un DTO.
    public CustomerDto getOneById(Long id) throws EntityNotFoundException {
        return this.customerDao.findById(id)
                .map(customerMapper::entityToDto)  // Convierte la entidad de cliente a DTO si se encuentra.
                .orElseThrow(() -> new EntityNotFoundException("El cliente con el id " + id + " no existe."));  // Lanza una excepción si no se encuentra el cliente.
    }

    @Override
    // Método que crea un nuevo cliente en la base de datos a partir de un DTO proporcionado.
    public CustomerDto create(CustomerDto entityDto) throws EntityDataAccesException {
        // Asignar el rol de CLIENTE al nuevo usuario.
        RoleEntity roleEntity = RoleEntity.builder()
                .name(NameRoleEnum.Empleado)
                .build();

        // Configurar la información de activación: fecha de inicio y estado.
        DateTimeActive dateTimeActive = DateTimeActive.builder()
                .state(ActiveEnum.ACTIVE)
                .startDate(new Date())  // Se establece la fecha de inicio como la fecha actual.
                .build();

        // Convertir el DTO de cliente a un DTO de usuario.
        UserDto userDto = this.customerMapper.customerDtoToUserDto(entityDto);
        // Convertir el DTO de usuario a una entidad para la persistencia.
        UserEntity userEntity = this.userMapper.dtoToEntity(userDto);
        userEntity.setRoleList(Set.of(roleEntity));  // Asigna el rol al usuario.
        userEntity.setCreatedAt(new Date());  // Establece la fecha de creación del usuario.

        // Crear la entidad de cliente con los datos del usuario y la información de activación.
        CustomerEntity customerEntity = CustomerEntity.builder()
                .user(userEntity)
                .dateTimeActive(dateTimeActive)
                .build();

        try {
            // Guardar la entidad de cliente en la base de datos.
            this.customerDao.save(customerEntity);
            // Convertir la entidad guardada en un DTO y retornarlo.
            return this.customerMapper.entityToDto(customerEntity);
        } catch (DataAccessException e) {
            System.out.println("service/create :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Método que actualiza un cliente existente, validando su existencia antes.
    public CustomerDto update(Long id, CustomerDto entityDto) throws EntityNotFoundException, EntityDataAccesException {
        // Validar la existencia del cliente con el ID proporcionado.
        CustomerEntity customerEntity = this.customerDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El cliente con el ID " + id + " no existe."));

        // Obtener el ID de usuario asociado al cliente.
        Long userId = customerEntity.getUser().getUserId();

        // Validar la existencia del usuario correspondiente.
        UserEntity userEntity = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con el ID " + userId + " no existe."));

        // Actualizar los datos de la entidad de usuario con los datos del DTO proporcionado.
        this.updateEntityFromDto(userEntity, entityDto);

        try {
            // Guardar la entidad de usuario actualizada en la base de datos.
            this.userDao.save(userEntity);
            // Convertir la entidad de usuario actualizada en un DTO y retornarlo.
            return this.userMapper.userEntityToCustomerDto(userEntity);
        } catch (DataAccessException e) {
            System.out.println("service/update :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Método que elimina un cliente específico por su ID, validando su existencia primero.
    public String delete(Long id) throws EntityNotFoundException {
        this.customerDao.findById(id).orElseThrow(() -> new EntityNotFoundException("El cliente con el ID " + id + " no existe."));
        this.customerDao.delete(id);  // Elimina al cliente de la base de datos.
        return "El cliente ID " + id + " fue eliminada.";
    }

    // Método encargado de actualizar los datos de la entidad de usuario con los datos de un DTO de cliente.
    private void updateEntityFromDto(UserEntity userEntity, CustomerDto entityDto) {
        userEntity.setName(entityDto.getUser().getName());
        userEntity.setLastName(entityDto.getUser().getLastName());
        userEntity.setEmail(entityDto.getUser().getEmail());
        userEntity.setAddress(entityDto.getUser().getAddress());
        userEntity.setPhone(entityDto.getUser().getPhone());
        userEntity.setPassword(entityDto.getUser().getPassword());
        userEntity.setUserName(entityDto.getUser().getUserName());
        userEntity.setBirthDate(entityDto.getUser().getBirthDate());
        userEntity.setUpdatedAt(new Date());  // Actualiza la fecha de modificación.
    }
}
