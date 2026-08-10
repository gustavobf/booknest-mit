package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LivroServiceTest {

    @Test
    void deveExecutarCrudEmMemoria () {
        LivroService livroService = criarService();

        Livro livro = criarLivro(10, "Clean Code");
        livroService.incluir(livro);

        assertEquals(1, livroService.obterLista().size());
        assertEquals("Clean Code", livroService.obterPorId(10).getTitulo());

        livro.setTitulo("Clean Code Atualizado");
        livroService.alterar(livro);

        assertEquals("Clean Code Atualizado", livroService.obterPorId(10).getTitulo());

        livroService.excluir(10);

        assertTrue(livroService.obterLista().isEmpty());
    }

    @Test
    void deveLancarExcecaoAoBuscarRegistroInexistente () {
        LivroService livroService = criarService();

        assertThrows(RegistroNaoEncontradoException.class, () -> livroService.obterPorId(99));
    }

    private LivroService criarService () {
        return new LivroService();
    }

    private Livro criarLivro (int id, String titulo) {
        Autor autor = new Autor(1, "Autor", "Brasileiro", 1980);
        Categoria categoria = new Categoria(1, "Categoria", "Descricao");
        Editora editora = new Editora(1, "Editora", "Cidade", "contato@editora.com", true);

        Livro livro = new Livro(id, titulo, "ISBN-001");
        livro.setAutor(autor);
        livro.setCategoria(categoria);
        livro.setEditora(editora);
        return livro;
    }
}
