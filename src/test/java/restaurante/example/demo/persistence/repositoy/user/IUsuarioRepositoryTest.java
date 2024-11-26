package restaurante.example.demo.persistence.repositoy.user;


import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import restaurante.example.demo.persistence.model.common.DateTimeActive;
import restaurante.example.demo.persistence.model.role.RoleEntity;
import restaurante.example.demo.persistence.model.user.CustomerEntity;
import restaurante.example.demo.persistence.model.user.UserEntity;
import restaurante.example.demo.persistence.enums.ActiveEnum;
import restaurante.example.demo.persistence.enums.NameRoleEnum;
import restaurante.example.demo.persistence.repositoy.role.IRoleRepository;
import restaurante.example.demo.service.mapper.user.ICustomerMapper;


@SpringBootTest
public class IUsuarioRepositoryTest {
    
    @Autowired
    IRoleRepository rolRepository;
    
    @Autowired
    IUserRepository usuarioRepository;
     
    
    @Autowired
    ICustomerRepository clienteRepository;

    @Autowired
    ICustomerMapper customerMapper;


    @Test
    public void saveCliente(){

        RoleEntity roleEntity  = RoleEntity.builder()
                .name(NameRoleEnum.Cliente)
                .build();

        LocalDate fNacimientox = LocalDate.of(1972, Month.MAY, 23);
        LocalDate fNacimiento = LocalDate.of(1972, Month.MAY, 23);
        Date fechaNacimiento = localDateToDate(fNacimiento);


        UserEntity usuario = UserEntity.builder()
                .name("Felipe")
                .lastName("lopez")
                .email("lopez@example.com")
                .address("Calle asalto")
                .phone("111111112")
                .password("12345678") // Asegúrate de encriptar en un entorno real
                .userName("felipe.micalisoft")
                .birthDate(fechaNacimiento)
                .roleList(Set.of(roleEntity))
                .build();

        LocalDate fIngreso = LocalDate.of(1972, Month.MAY, 23);
        Date fechaIngreso = localDateToDate(fIngreso);

        DateTimeActive fechaTiempoActivo = DateTimeActive.builder()
                .state(ActiveEnum.ACTIVE)
                .startDate(fechaIngreso)
                .build();


        CustomerEntity cliente = CustomerEntity.builder()
                .user(usuario) // Asociar el usuario al cliente
                .dateTimeActive(fechaTiempoActivo)// Usando la enum ActivoEnum
                .build();
        clienteRepository.save(cliente);

    }
        
    private Date localDateToDate(LocalDate fechaLocalDate ){        
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(fechaLocalDate.atStartOfDay(defaultZoneId).toInstant());        
    }
    
    @Test
    public void getAllCustomer(){
        List<CustomerEntity>  customerlist = (List<CustomerEntity>) clienteRepository.findAll();
        System.out.println(customerlist);
    }

    @Test
    public void getOneById(){
     /*   CustomerEntity customerEntity = clienteRepository.findById(1L).orElseThrow();
        System.out.println("Este es el  customerEntity :" + customerEntity);
        CustomerDto customerDto = customerMapper.entityToDto(customerEntity);
        System.out.println("Este es el customerDto :" + customerDto);*/
    }
    
}
