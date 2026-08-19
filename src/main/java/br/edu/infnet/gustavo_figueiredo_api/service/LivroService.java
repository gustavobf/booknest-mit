package br.edu.infnet.gustavo_figueiredo_api.service;

import br.edu.infnet.gustavo_figueiredo_api.exception.*;
import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.repository.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

@Service
public class LivroService extends BaseService<Livro> {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;
    private final EditoraRepository editoraRepository;

    public LivroService (LivroRepository livroRepository, AutorRepository autorRepository,
                         CategoriaRepository categoriaRepository, EditoraRepository editoraRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
        this.editoraRepository = editoraRepository;
    }

    @Override
    protected JpaRepository<Livro, Integer> getRepository () {
        return livroRepository;
    }

    @Override
    protected Integer obterId (Livro entidade) {
        return entidade.getId();
    }

    @Override
    protected void validarEntidade (Livro entidade) {
        if (entidade == null) {
            throw new DadosInvalidosException("Livro não pode ser nulo.");
        }
        if (entidade.getTitulo() == null || entidade.getTitulo().isBlank()) {
            throw new DadosInvalidosException("Livro deve possuir título.");
        }
        if (entidade.getIsbn() == null || entidade.getIsbn().isBlank()) {
            throw new DadosInvalidosException("Livro deve possuir ISBN.");
        }
        if (entidade.getAutor() == null || entidade.getAutor().getId() == null) {
            throw new DadosInvalidosException("Livro deve possuir autor.");
        }
        if (entidade.getCategoria() == null || entidade.getCategoria().getId() == null) {
            throw new DadosInvalidosException("Livro deve possuir categoria.");
        }
        if (entidade.getEditora() == null || entidade.getEditora().getId() == null) {
            throw new DadosInvalidosException("Livro deve possuir editora.");
        }
    }

    @Override
    protected String getNomeEntidade () {
        return "Livro";
    }

    @Override
    @Transactional
    public Livro incluir (Livro entidade) {
        validarEntidade(entidade);
        prepararRelacionamentos(entidade);
        return super.incluir(entidade);
    }

    @Override
    @Transactional
    public Livro alterar (Livro entidade) {
        validarEntidade(entidade);
        prepararRelacionamentos(entidade);
        return super.alterar(entidade);
    }

    @Transactional(readOnly = true)
    public List<Livro> listarOrdenadosPorTitulo () {
        return livroRepository.findAllByOrderByTituloAsc();
    }

    @Transactional(readOnly = true)
    public List<Livro> listarDisponiveis () {
        return livroRepository.findDisponiveis();
    }

    @Transactional(readOnly = true)
    public List<Livro> listarIndisponiveis () {
        return livroRepository.findIndisponiveis();
    }

    @Transactional(readOnly = true)
    public List<Livro> listarPorDisponibilidade (Boolean disponivel) {
        if (disponivel == null) {
            return obterLista();
        }
        return disponivel ? listarDisponiveis() : listarIndisponiveis();
    }

    @Transactional(readOnly = true)
    public List<Livro> buscarPorAutor (Integer idAutor) {
        return livroRepository.findByAutorId(idAutor);
    }

    @Transactional(readOnly = true)
    public List<String> listarTitulosMaiusculos () {
        return livroRepository.findAll().stream().map(Livro::getTitulo).map(String::toUpperCase).toList();
    }

    private void prepararRelacionamentos (Livro livro) {
        Integer idAutor = livro.getAutor().getId();
        Integer idCategoria = livro.getCategoria().getId();
        Integer idEditora = livro.getEditora().getId();

        Autor autor = autorRepository.findById(idAutor)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Autor com id " + idAutor + " não encontrado."));
        Categoria categoria = categoriaRepository.findById(idCategoria).orElseThrow(
                () -> new RegistroNaoEncontradoException("Categoria com id " + idCategoria + " não encontrada."));
        Editora editora = editoraRepository.findById(idEditora).orElseThrow(
                () -> new RegistroNaoEncontradoException("Editora com id " + idEditora + " não encontrada."));

        livro.setAutor(autor);
        livro.setCategoria(categoria);
        livro.setEditora(editora);
    }
}
