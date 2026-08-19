package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LivroServiceTest {
    @Autowired
    private LivroService livroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private EditoraService editoraService;

    @Test
    void deveExecutarCrudPersistido () {
        Livro livro = criarLivro("Clean Code");
        Livro livroCriado = livroService.incluir(livro);
        Integer idCriado = livroCriado.getId();

        assertEquals("Clean Code", livroService.obterPorId(idCriado).getTitulo());

        livroCriado.setTitulo("Clean Code Atualizado");
        livroService.alterar(livroCriado);

        assertEquals("Clean Code Atualizado", livroService.obterPorId(idCriado).getTitulo());

        livroService.excluir(idCriado);
        assertThrows(RegistroNaoEncontradoException.class, () -> livroService.obterPorId(idCriado));
    }

    @Test
    void deveRetornarLivrosDisponiveisComConsultaCustomizada () {
        assertFalse(livroService.listarDisponiveis().isEmpty());
        assertFalse(livroService.listarOrdenadosPorTitulo().isEmpty());
    }

    private Livro criarLivro (String titulo) {
        Livro livro = new Livro(null, titulo, "ISBN-001");
        livro.setAutor(autorService.obterPorId(1));
        livro.setCategoria(categoriaService.obterPorId(1));
        livro.setEditora(editoraService.obterPorId(1));
        return livro;
    }
}
