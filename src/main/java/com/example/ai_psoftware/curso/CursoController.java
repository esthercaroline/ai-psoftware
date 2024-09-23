package com.example.ai_psoftware.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CursoController {
    @Autowired
    private CursoService cursoService;

    // Rota para cadastrar curso
    @PostMapping
    public ResponseEntity<Curso> cadastrarCurso(@RequestBody Curso curso) {
        try {
            Curso novoCurso = cursoService.cadastrarCurso(curso);
            return ResponseEntity.ok(novoCurso);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Rota para listar cursos, com filtro opcional por nome
    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos(@RequestParam(required = false) String nome) {
        List<Curso> cursos = cursoService.listarCursos(nome);
        return ResponseEntity.ok(cursos);
    }

    // Rota para adicionar aluno ao curso
    @PutMapping("/{id}/alunos")
    public ResponseEntity<Curso> adicionarAluno(@PathVariable String id, @RequestParam String alunoCpf) {
        try {
            Curso cursoAtualizado = cursoService.adicionarAluno(id, alunoCpf);
            return ResponseEntity.ok(cursoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
