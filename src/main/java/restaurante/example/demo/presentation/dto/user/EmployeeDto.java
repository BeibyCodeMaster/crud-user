package restaurante.example.demo.presentation.dto.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.enums.ActiveEnum;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    @Valid
    private UserDto user;
    private Long  employeeId ;
    private Date startDate;
    private Date  endDate;
    private ActiveEnum state;
}
