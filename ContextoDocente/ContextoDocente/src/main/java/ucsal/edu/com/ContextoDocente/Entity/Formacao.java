package ucsal.edu.com.ContextoDocente.Entity;





import jakarta.persistence.*;
import ucsal.edu.com.ContextoDocente.Enums.Titulacao;

/**
 * Entity representing a qualification (Formação) of a professor.  Each
 * formação has a category (titulacao), an institution name, a course name
 * and the year of completion【713817450268639†L60-L65】.
 */
@Entity
@Table(name = "formacoes")
public class Formacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Titulacao categoria;

    @Column(nullable = false)
    private String instituicao;

    @Column(nullable = false)
    private String curso;

    @Column(name = "ano_conclusao")
    private Integer anoConclusao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    public Formacao() {
    }

    public Formacao(Titulacao categoria, String instituicao, String curso, Integer anoConclusao, Professor professor) {
        this.categoria = categoria;
        this.instituicao = instituicao;
        this.curso = curso;
        this.anoConclusao = anoConclusao;
        this.professor = professor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Titulacao getCategoria() {
        return categoria;
    }

    public void setCategoria(Titulacao categoria) {
        this.categoria = categoria;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getAnoConclusao() {
        return anoConclusao;
    }

    public void setAnoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
