package ucsal.edu.com.ContextoDocente.DTO;

import java.util.List;

public record ProfessorDTO(Long id,
                           String registro,
                           String nome,
                           Long escolaId,
                           String escolaNome,
                           boolean ativo,
                           List<FormacaoDTO> formacoes) {}
