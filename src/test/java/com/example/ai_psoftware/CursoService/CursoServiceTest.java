package com.example.ai_psoftware.CursoService;

import com.example.ai_psoftware.curso.Curso;
import com.example.ai_psoftware.curso.CursoRepository;
import com.example.ai_psoftware.curso.CursoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CursoServiceTest {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCadastrarCursoComProfessorExistente() throws Exception {
        // Dados de entrada
        Curso curso = new Curso("Java", "Curso de Java", 30, "12345678900");

        // Simular a resposta da API de usuários e salvar no repositório
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("", HttpStatus.OK));
        when(cursoRepository.save(any(Curso.class))).thenReturn(curso);

        // Executar
        Curso resultado = cursoService.cadastrarCurso(curso);

        // Verificações
        assertNotNull(resultado);
        assertEquals("Java", resultado.getNome());
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

    @Test
    public void testCadastrarCursoComProfessorInexistente() {
        // Dados de entrada
        Curso curso = new Curso("Java", "Curso de Java", 30, "12345678900");

        // Simular a resposta da API de usuários (professor não encontrado)
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Executar e verificar exceção
        Exception exception = assertThrows(Exception.class, () -> {
            cursoService.cadastrarCurso(curso);
        });

        assertEquals("Professor com CPF 12345678900 não encontrado.", exception.getMessage());
    }

    @Test
    public void testListarCursosSemNome() {
        // Simular retorno do repositório
        List<Curso> cursos = new ArrayList<>();
        cursos.add(new Curso("Java", "Curso de Java", 30, "12345678900"));
        when(cursoRepository.findAll()).thenReturn(cursos);

        // Executar
        List<Curso> resultado = cursoService.listarCursos(null);

        // Verificações
        assertEquals(1, resultado.size());
        assertEquals("Java", resultado.get(0).getNome());
    }

    @Test
    public void testAdicionarAlunoComSucesso() throws Exception {
        // Simular curso
        Curso curso = new Curso("Java", "Curso de Java", 30, "12345678900");
        when(cursoRepository.findById(anyString())).thenReturn(Optional.of(curso));

        // Simular aluno existente
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("", HttpStatus.OK));

        // Executar
        Curso resultado = cursoService.adicionarAluno("1", "98765432100");

        // Verificações
        assertTrue(curso.getAlunos().contains("98765432100"));
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

    @Test
    public void testAdicionarAlunoInexistente() {
        // Simular curso
        Curso curso = new Curso("Java", "Curso de Java", 30, "12345678900");
        when(cursoRepository.findById(anyString())).thenReturn(Optional.of(curso));

        // Simular aluno não encontrado
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Executar e verificar exceção
        Exception exception = assertThrows(Exception.class, () -> {
            cursoService.adicionarAluno("1", "98765432100");
        });

        assertEquals("Aluno com CPF 98765432100 não encontrado.", exception.getMessage());
    }

    @Test
    public void testAdicionarAlunoEmCursoCheio() {
        // Simular curso cheio
        Curso curso = new Curso("Java", "Curso de Java", 1, "12345678900");
        curso.getAlunos().add("11111111111");
        when(cursoRepository.findById(anyString())).thenReturn(Optional.of(curso));

        // Executar e verificar exceção
        Exception exception = assertThrows(Exception.class, () -> {
            cursoService.adicionarAluno("1", "98765432100");
        });

        assertEquals("O curso atingiu o número máximo de alunos.", exception.getMessage());
    }
}
