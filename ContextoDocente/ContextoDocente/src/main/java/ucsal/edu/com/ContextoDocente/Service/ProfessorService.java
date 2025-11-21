package ucsal.edu.com.ContextoDocente.Service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import ucsal.edu.com.ContextoDocente.Entity.Formacao;
import ucsal.edu.com.ContextoDocente.Entity.Professor;

import ucsal.edu.com.ContextoDocente.Repository.FormacaoRepository;
import ucsal.edu.com.ContextoDocente.Repository.ProfessorRepository;

import java.util.List;


@Service
@Transactional
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final FormacaoRepository formacaoRepository;
    private final WebClient webClient;

    // URL FIXA DO MICROSSERVIÇO ESCOLA (Trocar pela sua porta real)
    private static final String ESCOLA_SERVICE_URL = "http://localhost:8081/escolas";

    public ProfessorService(ProfessorRepository professorRepository,
                            FormacaoRepository formacaoRepository) {
        this.professorRepository = professorRepository;
        this.formacaoRepository = formacaoRepository;

        this.webClient = WebClient.builder()
                .baseUrl(ESCOLA_SERVICE_URL)
                .build();
    }

    // ------------------ MÉTODOS PRINCIPAIS ---------------------

    public Professor criarProfessor(Professor professor) {

        // 1. Validar se a escola existe
        validarEscola(professor.getEscolaId());

        // 2. Validar registro único
        if (professorRepository.existsByRegistro(professor.getRegistro())) {
            throw new RuntimeException("Registro já está cadastrado!");
        }

        return professorRepository.save(professor);
    }

    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public Professor atualizarProfessor(Long id, Professor novo) {

        Professor atual = buscarPorId(id);

        // Validar escola novamente
        validarEscola(novo.getEscolaId());

        atual.setNome(novo.getNome());
        atual.setRegistro(novo.getRegistro());
        atual.setEscolaId(novo.getEscolaId());
        atual.setAtivo(novo.isAtivo());

        return professorRepository.save(atual);
    }

    public void deletarProfessor(Long id) {
        Professor professor = buscarPorId(id);
        professorRepository.delete(professor);
    }

    // ------------------ FORMAÇÕES ---------------------

    public Formacao adicionarFormacao(Long professorId, Formacao formacao) {
        Professor professor = buscarPorId(professorId);

        formacao.setProfessor(professor);

        return formacaoRepository.save(formacao);
    }

    public List<Formacao> listarFormacoes(Long professorId) {
        Professor professor = buscarPorId(professorId);
        return professor.getFormacoes();
    }

    // ------------------ VALIDAÇÃO DE ESCOLA VIA WEBCLIENT ---------------------

    private void validarEscola(Long escolaId) {
        try {
            Boolean existe = webClient.get()
                    .uri("/{id}/existe", escolaId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (existe == null || !existe) {
                throw new RuntimeException("Escola informada não existe!");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar escola no serviço de Escola", e);
        }
    }
}

