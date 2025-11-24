package ucsal.edu.com.ContextoDocente.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucsal.edu.com.ContextoDocente.DTO.FormacaoDTO;
import ucsal.edu.com.ContextoDocente.DTO.ProfessorDTO;
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
    public ResponseEntity<Professor> criarProfessor(@RequestBody ProfessorDTO dto) {
        Professor criado = professorService.criarProfessor(dto);
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
                                                        @RequestBody ProfessorDTO dto) {
        Professor atualizado = professorService.atualizarProfessor(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        professorService.deletarProfessor(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------ FORMACAO ------------------

    @PostMapping("/{professorId}/formacao")
    public ResponseEntity<Formacao> adicionarFormacao(@PathVariable Long professorId,
                                                      @RequestBody FormacaoDTO formacaoDto) {
        Formacao criada = professorService.adicionarFormacao(professorId, formacaoDto);
        return new ResponseEntity<>(criada, HttpStatus.CREATED);
    }

    @GetMapping("/{professorId}/formacao")
    public ResponseEntity<Formacao> buscarFormacao(@PathVariable Long professorId) {
        Formacao formacao = professorService.buscarFormacao(professorId);
        return ResponseEntity.ok(formacao);
    }

    @PostMapping("/debug")
    public ResponseEntity<Void> debug(@RequestBody String raw) {
        System.out.println("RAW BODY: " + raw);
        return ResponseEntity.ok().build();
    }

}
