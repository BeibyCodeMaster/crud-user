package restaurante.example.demo.presentation.dto.booking;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurante.example.demo.persistence.enums.LocationEnum;
import restaurante.example.demo.persistence.enums.StatusEnum;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Objeto de Transferencia de Datos (DTO) que representa una mesa en el sistema.
public class MesaDto {

    private Long id;// Identificador único de la mesa
    // Código único que identifica a la mesa en el sistema.
    // Debe ser proporcionado y no debe exceder los 20 caracteres.
    @NotBlank(message = "Ingrese el código de la mesa.")
    @Size(max = 20, message = "El código debe tener un máximo de 20 caracteres.")
    private String codigo;

    // Capacidad máxima de personas que la mesa puede acomodar.
    // Valor mínimo permitido: 1 persona, y máximo permitido: 20 personas.
    @Min(value = 1, message = "La capacidad debe ser al menos 1.")
    @Max(value = 20, message = "La capacidad máxima permitida es 20.")
    private byte capacidad;

    // Estado actual de la mesa, que indica su disponibilidad o uso.
    // Campo obligatorio que utiliza un enumerador para definir posibles valores (por ejemplo, LIBRE, OCUPADA, RESERVADA).
    @NotNull(message = "Ingrese el estado.")
    private StatusEnum estado;

    @NotNull(message = "Ingrese si está decorada.")
    private Boolean decorada;

    // Ubicación de la mesa dentro del establecimiento (por ejemplo, INTERIOR o EXTERIOR).
    // Campo obligatorio que utiliza un enumerador para especificar la ub
    @NotNull(message = "Ingrese la ubicación.") 
    private LocationEnum ubicacion;

    // Imagen de la mesa en formato binario (byte array).
    // La imagen no debe exceder 1 MB de tamaño (1048576 bytes).
    private byte[] imagen;

    // Ruta de almacenamiento de la imagen en el sistema de archivos.
    // La ruta no debe exceder los 255 caracteres.
    @NotBlank(message = "Ingrese la ruta de la imagen.")
    private String rutaImagen;        
}
