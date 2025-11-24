package ucsal.edu.com.ContextoDocente.Entity;

import jakarta.persistence.*;
import ucsal.edu.com.ContextoDocente.Entity.Formacao;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professores")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Registro funcional do professor (único)
    @Column(nullable = false, unique = true)
    private String registro;

    @Column(nullable = false)
    private String nome;

    // Apenas o vínculo com a escola (ID da escola)
    @Column(name = "escola_id", nullable = false)
    private Long escolaId;

    @Column(nullable = false)
    private Boolean ativo = true;

    /**
     * Lista de formações (titulações) do professor.
     * Exemplo: "Graduação", "Especialização", "Mestrado", "Doutorado".
     */
//    @OneToOne
//    private Formacao formacao;

    public Professor() {}

    public Professor(String registro, String nome, Long escolaId) {
        this.registro = registro;
        this.nome = nome;
        this.escolaId = escolaId;
        this.ativo = true;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getEscolaId() {
        return escolaId;
    }

    public void setEscolaId(Long escolaId) {
        this.escolaId = escolaId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

//    public Formacao getFormacoes() {
//        return formacao;
//    }
//
//    public void setFormacoes(Formacao formacoes) {
//        this.formacao = formacoes;
//    }
}
