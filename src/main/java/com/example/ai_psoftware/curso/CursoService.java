package com.example.ai_psoftware.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    private static final String USUARIO_API_URL = "http://184.72.80.215:8080/usuario/";

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Curso cadastrarCurso(Curso curso) throws Exception {
        if (verificaUsuarioExiste(curso.getProfessorCpf())) {
            return cursoRepository.save(curso);
        } else {
            throw new Exception("Professor com CPF " + curso.getProfessorCpf() + " não encontrado.");
        }
    }

    public List<Curso> listarCursos(String nome) {
        if (nome == null || nome.isEmpty()) {
            return cursoRepository.findAll();
        } else {
            return cursoRepository.findByNome(nome);
        }
    }

    public Curso adicionarAluno(String cursoId, String alunoCpf) throws Exception {
        Optional<Curso> cursoOpt = cursoRepository.findById(cursoId);
        if (!cursoOpt.isPresent()) {
            throw new Exception("Curso não encontrado.");
        }

        Curso curso = cursoOpt.get();
        if (curso.getAlunos().size() >= curso.getNumeroMaximoAlunos()) {
            throw new Exception("O curso atingiu o número máximo de alunos.");
        }

        if (verificaUsuarioExiste(alunoCpf)) {
            curso.getAlunos().add(alunoCpf);
            return cursoRepository.save(curso);
        } else {
            throw new Exception("Aluno com CPF " + alunoCpf + " não encontrado.");
        }
    }

    private boolean verificaUsuarioExiste(String cpf) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(USUARIO_API_URL + cpf, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
