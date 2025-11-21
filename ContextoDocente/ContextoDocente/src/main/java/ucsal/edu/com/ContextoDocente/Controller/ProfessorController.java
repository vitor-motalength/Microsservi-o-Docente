package ucsal.edu.com.ContextoDocente.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.edu.com.ContextoDocente.Entity.Formacao;
import ucsal.edu.com.ContextoDocente.Entity.Professor;
import ucsal.edu.com.ContextoDocente.Service.ProfessorService;

import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    // ------------------ CRUD PROFESSOR ------------------

    @PostMapping
    public ResponseEntity<Professor> criarProfessor(@RequestBody Professor professor) {
        Professor criado = professorService.criarProfessor(professor);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> buscarPorId(@PathVariable Long id) {
        Professor professor = professorService.buscarPorId(id);
        return ResponseEntity.ok(professor);
    }

    @GetMapping
    public ResponseEntity<List<Professor>> listarTodos() {
        return ResponseEntity.ok(professorService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> atualizarProfessor(@PathVariable Long id,
                                                        @RequestBody Professor professor) {
        Professor atualizado = professorService.atualizarProfessor(id, professor);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        professorService.deletarProfessor(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------ FORMACAO ------------------

    @PostMapping("/{professorId}/formacoes")
    public ResponseEntity<Formacao> adicionarFormacao(@PathVariable Long professorId,
                                                      @RequestBody Formacao formacao) {
        Formacao criada = professorService.adicionarFormacao(professorId, formacao);
        return new ResponseEntity<>(criada, HttpStatus.CREATED);
    }

    @GetMapping("/{professorId}/formacoes")
    public ResponseEntity<List<Formacao>> listarFormacoes(@PathVariable Long professorId) {
        List<Formacao> formacoes = professorService.listarFormacoes(professorId);
        return ResponseEntity.ok(formacoes);
    }
}
