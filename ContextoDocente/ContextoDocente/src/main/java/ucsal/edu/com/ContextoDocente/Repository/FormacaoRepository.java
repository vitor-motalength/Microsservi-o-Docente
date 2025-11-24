package ucsal.edu.com.ContextoDocente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucsal.edu.com.ContextoDocente.Entity.Formacao;
import ucsal.edu.com.ContextoDocente.Entity.Professor;

import java.util.List;

@Repository
public interface FormacaoRepository extends JpaRepository<Formacao, Long> {
    Formacao findByProfessorId(Long professorId);
}
