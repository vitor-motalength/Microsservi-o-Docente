package ucsal.edu.com.ContextoDocente.Service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import ucsal.edu.com.ContextoDocente.DTO.FormacaoDTO;
import ucsal.edu.com.ContextoDocente.DTO.ProfessorDTO;
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
    private static final String ESCOLA_SERVICE_URL = "http://localhost:8080/api/escolas";

    public ProfessorService(ProfessorRepository professorRepository,
                            FormacaoRepository formacaoRepository) {
        this.professorRepository = professorRepository;
        this.formacaoRepository = formacaoRepository;

        this.webClient = WebClient.builder()
                .baseUrl(ESCOLA_SERVICE_URL)
                .build();
    }

    // ------------------ MÉTODOS PRINCIPAIS ---------------------

    public Professor criarProfessor(ProfessorDTO dto) {

        // 1. Validar se a escola existe
        validarEscola(dto.escolaId());

        // 2. Validar registro único
        if (professorRepository.existsByRegistro(dto.registro())) {
            throw new RuntimeException("Registro já está cadastrado!");
        }

        Professor novo = new Professor(dto.registro(), dto.nome(), dto.escolaId());
        return professorRepository.save(novo);
    }

    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
    }

    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }

    public Professor atualizarProfessor(Long id, ProfessorDTO novo) {

        Professor atual = buscarPorId(id);

        // Validar escola novamente
        validarEscola(novo.escolaId());

        atual.setNome(novo.nome());
        atual.setRegistro(novo.registro());
        atual.setEscolaId(novo.escolaId());
        atual.setAtivo(novo.ativo());

        return professorRepository.save(atual);
    }

    public void deletarProfessor(Long id) {
        Professor professor = buscarPorId(id);
        professorRepository.delete(professor);
    }

    // ------------------ FORMAÇÕES ---------------------

    public Formacao adicionarFormacao(Long professorId, FormacaoDTO dto) {

        try{
            Professor professor = buscarPorId(professorId);

            if(professor == null) {
                throw new RuntimeException("Professor informado não existe!");
            }

            Formacao formacao = new Formacao(dto.categoria(), dto.instituicao(), dto.curso(), dto.anoConclusao(), professor);

            return formacaoRepository.save(formacao);
        } catch (Exception e){
            throw new RuntimeException("Erro ao criar Formação no serviço.", e);
        }
    }
    public Formacao buscarFormacao(Long professorId) {
        Formacao formacao = formacaoRepository.findByProfessorId(professorId);
        return formacao;
    }

    // ------------------ VALIDAÇÃO DE ESCOLA VIA WEBCLIENT ---------------------


    private void validarEscola(Long escolaId) {
        try {
            Boolean existe = webClient.get()
                    .uri("/{id}", escolaId)
                    .exchangeToMono((ClientResponse response) -> {
                        if (response.statusCode().is2xxSuccessful()) {
                            // 2xx: escola existe (pode ter corpo com o objeto escola)
                            return Mono.just(Boolean.TRUE);
                        } else if (response.statusCode().value() == 404) {
                            // 404: escola não encontrada
                            return Mono.just(Boolean.FALSE);
                        } else {
                            // outros códigos: tratar como erro
                            return response.bodyToMono(String.class)
                                    .defaultIfEmpty(response.statusCode().toString())
                                    .flatMap(body -> Mono.error(new RuntimeException(
                                            "Erro ao validar escola. Status: " + response.statusCode() + ", body: " + body)));
                        }
                    })
                    .block();

            if (existe == null || !existe) {
                throw new RuntimeException("Escola informada não existe!");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar escola no serviço de Escola", e);
        }
    }
}

