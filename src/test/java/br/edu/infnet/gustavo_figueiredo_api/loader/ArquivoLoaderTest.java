package br.edu.infnet.gustavo_figueiredo_api.loader;

import br.edu.infnet.gustavo_figueiredo_api.model.Emprestimo;
import br.edu.infnet.gustavo_figueiredo_api.model.Exemplar;
import br.edu.infnet.gustavo_figueiredo_api.model.Livro;
import br.edu.infnet.gustavo_figueiredo_api.model.Usuario;
import br.edu.infnet.gustavo_figueiredo_api.service.AutorService;
import br.edu.infnet.gustavo_figueiredo_api.service.CategoriaService;
import br.edu.infnet.gustavo_figueiredo_api.service.EditoraService;
import br.edu.infnet.gustavo_figueiredo_api.service.EmprestimoService;
import br.edu.infnet.gustavo_figueiredo_api.service.ExemplarService;
import br.edu.infnet.gustavo_figueiredo_api.service.LivroService;
import br.edu.infnet.gustavo_figueiredo_api.service.UsuarioService;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ArquivoLoaderTest {

    @Test
    void deveCarregarRelacionamentosDaBiblioteca() throws URISyntaxException {
        ArquivoLoader loader = criarLoader();

        assertEquals(5, loader.getUsuarios().size());
        assertEquals(5, loader.getLivros().size());
        assertEquals(8, loader.getExemplares().size());
        assertEquals(5, loader.getEmprestimos().size());

        Livro horaDaEstrela = loader.getLivros().stream()
                .filter(livro -> livro.getId().equals(2))
                .findFirst()
                .orElseThrow();

        assertEquals(2, horaDaEstrela.getExemplares().size());
        assertEquals(1, horaDaEstrela.getQuantidadeExemplaresDisponiveis());
        assertTrue(horaDaEstrela.getDisponivel());
    }

    @Test
    void deveManterHistoricoEDisponibilidadePorExemplar() throws URISyntaxException {
        ArquivoLoader loader = criarLoader();

        Usuario maria = loader.getUsuarios().stream()
                .filter(usuario -> usuario.getId().equals(2))
                .findFirst()
                .orElseThrow();
        Exemplar exemplarEmprestado = loader.getExemplares().stream()
                .filter(exemplar -> exemplar.getId().equals(3))
                .findFirst()
                .orElseThrow();
        Emprestimo emprestimoAberto = loader.getEmprestimos().stream()
                .filter(emprestimo -> emprestimo.getId().equals(2))
                .findFirst()
                .orElseThrow();
        Livro capitaesDaAreia = loader.getLivros().stream()
                .filter(livro -> livro.getId().equals(4))
                .findFirst()
                .orElseThrow();

        assertEquals(1, maria.getEmprestimos().size());
        assertEquals(1, maria.getEmprestimosEmAberto());
        assertFalse(exemplarEmprestado.getDisponivel());
        assertFalse(emprestimoAberto.estaDevolvido());
        assertTrue(emprestimoAberto.estaAtrasado());
        assertFalse(capitaesDaAreia.getDisponivel());
    }

    private ArquivoLoader criarLoader() throws URISyntaxException {
        ArquivoLoader loader = new ArquivoLoader(
                new AutorService(),
                new CategoriaService(),
                new EditoraService(),
                new UsuarioService(),
                new LivroService(),
                new ExemplarService(),
                new EmprestimoService()
        );
        URL resourceUrl = getClass().getClassLoader().getResource("data");
        assertNotNull(resourceUrl);

        Path dataDir = Path.of(resourceUrl.toURI());
        String baseDir = dataDir.toString() + "\\";

        loader.carregarDados(
                baseDir + "autores.txt",
                baseDir + "categorias.txt",
                baseDir + "editoras.txt",
                baseDir + "usuarios.txt",
                baseDir + "livros.txt",
                baseDir + "exemplares.txt",
                baseDir + "emprestimos.txt"
        );
        return loader;
    }
}
