package restaurante.example.demo.persistence.model.booking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurante.example.demo.persistence.enums.LocationEnum;
import restaurante.example.demo.persistence.enums.StatusEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="mesa")
// Clase que representa la entidad "Mesa", que corresponde a la tabla "mesa" en la base de datos.
public class MesaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_mesa")
    private Long id; // Identificador único de la mesa

    private String codigo; // Código único de la mesa

    private byte capacidad; // Capacidad máxima de personas que puede acomodar la mesa

    @Enumerated(EnumType.STRING)
    private StatusEnum estado; // Estado actual de la mesa (disponible, ocupada, reservada)

    private Boolean decorada; // Indica si la mesa está decorada

    @Enumerated(EnumType.STRING)
    private LocationEnum ubicacion; // Ubicación de la mesa dentro del establecimiento (interior, exterior, terraza)

    @Column(length = 50000000)
    private byte[] imagen; // Imagen asociada a la mesa (puede ser una foto, etc.)

    @Column(name="ruta_imagen")
    private String rutaImagen; // Ruta del archivo de imagen de la mesa en el sistema
}




