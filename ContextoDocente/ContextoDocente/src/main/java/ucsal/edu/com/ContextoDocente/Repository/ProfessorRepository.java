package ucsal.edu.com.ContextoDocente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucsal.edu.com.ContextoDocente.Entity.Professor;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    boolean existsByRegistro(String registro);

    Optional<Professor> findByRegistro(String registro);


    List<Professor> findByEscolaId(Long escolaId);
}

