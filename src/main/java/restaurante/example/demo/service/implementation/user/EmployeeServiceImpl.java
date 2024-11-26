package restaurante.example.demo.service.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.error.EntityDataAccesException;
import restaurante.example.demo.error.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.user.IEmployeeDao;
import restaurante.example.demo.persistence.dao.interfaces.user.IUserDao;
import restaurante.example.demo.persistence.model.common.DateTimeActive;
import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.persistence.model.user.EmployeeEntity;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.persistence.enums.ActiveEnum;
import restaurante.example.demo.persistence.enums.NameRoleEnum;
import restaurante.example.demo.presentation.dto.user.EmployeeDto;
import restaurante.example.demo.presentation.dto.user.UserDto;
import restaurante.example.demo.service.interfaces.user.IEmployeeService;
import restaurante.example.demo.service.mapper.user.IEmployeeMapper;
import restaurante.example.demo.service.mapper.user.IUserMapper;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
// Implementación del servicio que maneja la lógica de negocio para la entidad Empleado.
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private IUserDao userDao;  // DAO para acceder a la base de datos para la entidad Usuario.
    @Autowired
    private IEmployeeDao employeeDao;  // DAO para acceder a la base de datos para la entidad Empleado.
    @Autowired
    private IEmployeeMapper employeeMapper;  // Mapper para convertir entre entidad Empleado y DTO.
    @Autowired
    private IUserMapper userMapper;  // Mapper para convertir entre entidad Usuario y DTO.

    @Override
    // Método que obtiene todos los empleados y los convierte a DTOs.
    public List<EmployeeDto> getAll() {
        return this.employeeDao.findAll().stream()
                .map(this.employeeMapper::entityToDto)  // Convierte cada entidad de empleado a DTO.
                .toList();
    }

    @Override
    // Método que obtiene un empleado por su ID y lo convierte en un DTO.
    public EmployeeDto getOneById(Long id) throws EntityNotFoundException {
        return this.employeeDao.findById(id)
                .map(this.employeeMapper::entityToDto)  // Convierte la entidad de empleado a DTO si se encuentra.
                .orElseThrow(() -> new EntityNotFoundException("El empleado con el id " + id + " no existe."));  // Lanza una excepción si no se encuentra el empleado.
    }

    @Override
    // Método que crea un nuevo empleado en la base de datos a partir de un DTO proporcionado.
    public EmployeeDto create(EmployeeDto entityDto) throws EntityDataAccesException {
        // Asignar el rol de EMPLEADO al nuevo usuario.
        RoleEntity roleEntity = RoleEntity.builder()
                .name(NameRoleEnum.Empleado)
                .build();

        // Configurar la información de activación: fecha de inicio y estado.
        DateTimeActive dateTimeActive = DateTimeActive.builder()
                .state(ActiveEnum.ACTIVE)
                .startDate(new Date())  // Se establece la fecha de inicio como la fecha actual.
                .build();

        // Convertir el DTO de empleado a un DTO de usuario.
        UserDto userDto = this.employeeMapper.employeeDtoToUserDto(entityDto);
        // Convertir el DTO de usuario a una entidad para la persistencia.
        UserEntity userEntity = this.userMapper.dtoToEntity(userDto);
        userEntity.setRoleList(Set.of(roleEntity));  // Asigna el rol al usuario.
        userEntity.setCreatedAt(new Date());  // Establece la fecha de creación del usuario.

        // Crear la entidad de empleado con los datos del usuario y la información de activación.
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .user(userEntity)
                .dateTimeActive(dateTimeActive)
                .build();

        try {
            // Guardar la entidad de empleado en la base de datos.
            this.employeeDao.save(employeeEntity);
            // Convertir la entidad guardada en un DTO y retornarlo.
            return this.employeeMapper.entityToDto(employeeEntity);
        } catch (DataAccessException e) {
            System.out.println("service/create :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Método que actualiza un empleado existente, validando su existencia antes.
    public EmployeeDto update(Long id, EmployeeDto entityDto) throws EntityNotFoundException, EntityDataAccesException {
        // Validar la existencia del empleado con el ID proporcionado.
        EmployeeEntity employeeEntity = this.employeeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El empleado con el ID " + id + " no existe."));

        // Obtener el ID de usuario asociado al empleado.
        Long userId = employeeEntity.getUser().getUserId();

        // Validar la existencia del usuario correspondiente.
        UserEntity userEntity = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con el ID " + userId + " no existe."));

        // Actualizar los datos de la entidad de usuario con los datos del DTO proporcionado.
        this.updateEntityFromDto(userEntity, entityDto);

        try {
            // Guardar la entidad de usuario actualizada en la base de datos.
            this.userDao.save(userEntity);
            // Convertir la entidad de usuario actualizada en un DTO y retornarlo.
            return this.userMapper.userEntityToEmployeeDto(userEntity);
        } catch (DataAccessException e) {
            System.out.println("service/update :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Método que elimina un empleado específico por su ID, validando su existencia primero.
    public String delete(Long id) throws EntityNotFoundException {
        this.employeeDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El empleado con el ID " + id + " no existe."));
        this.employeeDao.delete(id);  // Elimina al empleado de la base de datos.
        return "El empleado con el ID " + id + " fue eliminada.";
    }

    // Método encargado de actualizar los datos de la entidad de usuario con los datos de un DTO de empleado.
    private void updateEntityFromDto(UserEntity userEntity, EmployeeDto entityDto) {
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
