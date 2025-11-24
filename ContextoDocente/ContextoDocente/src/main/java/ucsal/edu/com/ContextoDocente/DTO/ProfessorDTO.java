package ucsal.edu.com.ContextoDocente.DTO;

public record ProfessorDTO(
        Long id,
        String registro,
        String nome,
        Long escolaId,
        Boolean ativo
) {}
