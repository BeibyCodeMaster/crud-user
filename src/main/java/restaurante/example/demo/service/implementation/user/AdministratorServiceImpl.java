package restaurante.example.demo.service.implementation.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import restaurante.example.demo.error.EntityDataAccesException;
import restaurante.example.demo.error.EntityNotFoundException;
import restaurante.example.demo.persistence.dao.interfaces.user.IAdministratorDao;
import restaurante.example.demo.persistence.model.common.DateTimeActive;
import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.persistence.model.user.AdministratorEntity;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.persistence.enums.ActiveEnum;
import restaurante.example.demo.persistence.enums.NameRoleEnum;
import restaurante.example.demo.presentation.dto.user.AdministratorDto;
import restaurante.example.demo.presentation.dto.user.UserDto;
import restaurante.example.demo.service.interfaces.user.IAdministratorService;
import restaurante.example.demo.service.mapper.user.IAdministratorMapper;
import restaurante.example.demo.service.mapper.user.IUserMapper;

import java.util.Date;
import java.util.List;
import java.util.Set;
import restaurante.example.demo.persistence.dao.interfaces.user.IUserDao;

@Service
// Servicio encargado de implementar la lógica de negocio para la entidad Administrador.
public class AdministratorServiceImpl implements IAdministratorService {

    @Autowired
    private IUserDao userDao; // DAO para acceder a la base de datos para la entidad Usuario
    @Autowired
    private IAdministratorDao administratorDao; // DAO para acceder a la base de datos para la entidad Administrador
    @Autowired
    private IAdministratorMapper administratorMapper; // Mapper para convertir entre entidad Administrador y DTO
    @Autowired
    private IUserMapper userMapper; // Mapper para convertir entre entidad Usuario y DTO

    @Override
    // Obtiene la lista de todos los administradores en forma de DTOs.
    public List<AdministratorDto> getAll() {
        return this.administratorDao.findAll().stream()
                .map(this.administratorMapper::entityToDto) // Convierte cada entidad a DTO
                .toList(); // Recolecta los DTOs en una lista
    }

    @Override
    // Obtiene un administrador específico por su ID y lo convierte en un DTO.
    public AdministratorDto getOneById(Long id) throws EntityNotFoundException {
        return this.administratorDao.findById(id)
                .map(this.administratorMapper::entityToDto) // Convierte la entidad a DTO
                .orElseThrow(() -> new EntityNotFoundException("El administrador con el ID " + id + " no existe."));
    }

    @Override
    // Crea un nuevo administrador en la base de datos a partir de un DTO proporcionado.
    public AdministratorDto create(AdministratorDto entityDto) throws EntityDataAccesException {
        // Asigna el rol de ADMINISTRADOR al nuevo usuario.
        RoleEntity roleEntity = RoleEntity.builder()
                .name(NameRoleEnum.Administrador) // Rol asignado es ADMINISTRATOR
                .build();

        // Configura la información de activación del usuario (fecha de inicio y estado).
        DateTimeActive dateTimeActive = DateTimeActive.builder()
                .state(ActiveEnum.ACTIVE) // El estado es activo
                .startDate(new Date()) // Fecha de inicio es la fecha actual
                .build();

        // Convierte el DTO de administrador a DTO de usuario.
        UserDto userDto = this.administratorMapper.adminDtoToUserDto(entityDto);

        // Convierte el DTO de usuario a entidad de usuario.
        UserEntity userEntity = this.userMapper.dtoToEntity(userDto);

        // Asocia el rol al usuario y establece la fecha de creación.
        userEntity.setRoleList(Set.of(roleEntity)); // El rol de ADMINISTRATOR se asigna al usuario
        userEntity.setCreatedAt(new Date()); // Fecha de creación es la fecha actual

        // Crea la entidad de administrador con los datos de usuario y activación.
        AdministratorEntity administratorEntity = AdministratorEntity.builder()
                .user(userEntity) // Asocia el usuario al administrador
                .dateTimeActive(dateTimeActive) // Asocia la información de activación
                .build();

        // Guarda la entidad de administrador en la base de datos.
        try {
            this.administratorDao.save(administratorEntity); // Persistencia de datos
            // Convierte la entidad guardada a DTO y la retorna.
            return this.administratorMapper.entityToDto(administratorEntity);
        } catch (DataAccessException e) {
            // Maneja los errores de acceso a datos y lanza una excepción personalizada.
            System.out.println("service/create :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Actualiza un administrador existente, validando primero su existencia.
    public AdministratorDto update(Long id, AdministratorDto entityDto) throws EntityNotFoundException, EntityDataAccesException {
        // Valida si el administrador con el ID proporcionado existe.
        AdministratorEntity administratorEntity = this.administratorDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El administrador con el ID " + id + " no existe."));

        // Obtiene el ID de usuario asociado al administrador.
        Long userId = administratorEntity.getUser().getUserId();

        // Valida si el usuario correspondiente existe.
        UserEntity userEntity = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario con el ID " + userId + " no existe."));

        // Se actualiza la entidad de usuario con los datos del DTO proporcionado.
        this.updateEntityFromDto(userEntity, entityDto);

        try {
            // Guarda la entidad de usuario actualizada en la base de datos.
            this.userDao.save(userEntity);
            // Convierte la entidad de usuario actualizada a un DTO de administrador y lo retorna.
            return this.userMapper.userEntityToAdminstratorDto(userEntity);
        } catch (DataAccessException e) {
            // Maneja los errores de acceso a datos y lanza una excepción personalizada.
            System.out.println("service/update :" + e.getMessage());
            throw new EntityDataAccesException("Error interno del servidor.");
        }
    }

    @Override
    // Elimina un administrador específico por su ID, verificando su existencia.
    public String delete(Long id) throws EntityNotFoundException {
        // Valida si el administrador con el ID proporcionado existe.
        this.administratorDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("El administrador con el ID " + id + " no existe."));

        // Elimina la entidad de administrador de la base de datos.
        this.administratorDao.delete(id);
        return "El administrador con el ID " + id + " fue eliminado.";
    }

    // Método encargado de actualizar los datos de la entidad Usuario con los del DTO proporcionado.
    private void updateEntityFromDto(UserEntity userEntity, AdministratorDto entityDto) {
        userEntity.setName(entityDto.getUser().getName()); // Actualiza el nombre
        userEntity.setLastName(entityDto.getUser().getLastName()); // Actualiza el apellido
        userEntity.setEmail(entityDto.getUser().getEmail()); // Actualiza el correo electrónico
        userEntity.setAddress(entityDto.getUser().getAddress()); // Actualiza la dirección
        userEntity.setPhone(entityDto.getUser().getPhone()); // Actualiza el teléfono
        userEntity.setPassword(entityDto.getUser().getPassword()); // Actualiza la contraseña
        userEntity.setUserName(entityDto.getUser().getUserName()); // Actualiza el nombre de usuario
        userEntity.setBirthDate(entityDto.getUser().getBirthDate()); // Actualiza la fecha de nacimiento
        userEntity.setUpdatedAt(new Date()); // Actualiza la fecha de modificación
    }
}