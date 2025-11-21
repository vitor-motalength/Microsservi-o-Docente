package ucsal.edu.com.ContextoDocente.DTO;

import ucsal.edu.com.ContextoDocente.Enums.Titulacao;

public record FormacaoDTO(Long id,
                          Titulacao categoria,
                          String instituicao,
                          String curso,
                          Integer anoConclusao) {}
