package restaurante.example.demo.service.mapper.common;

// Interfaz que define las operaciones básicas de mapeo entre objetos (entidad y DTO).
public interface ISourceTargetMapper<Entity,DTO> {
    DTO entityToDto(Entity entity); // Convierte un objeto entidad en un objeto DTO.
    Entity dtoToEntity(DTO dto); // Convierte un objeto DTO en un objeto entidad.
}
