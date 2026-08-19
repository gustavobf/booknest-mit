package br.edu.infnet.gustavo_figueiredo_api.integration;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class CargaInicialBancoTest {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private LivroService livroService;
    @Autowired
    private ExemplarService exemplarService;
    @Autowired
    private EmprestimoService emprestimoService;

    @Test
    void deveCarregarDadosIniciaisNoStartup () {
        assertEquals(5, usuarioService.obterLista().size());
        assertEquals(5, livroService.obterLista().size());
        assertEquals(8, exemplarService.obterLista().size());
        assertEquals(5, emprestimoService.obterLista().size());
    }

    @Test
    void deveManterRelacionamentosDaCargaInicial () {
        Livro horaDaEstrela = livroService.obterPorId(2);
        Usuario maria = usuarioService.obterPorId(2);
        Exemplar exemplarEmprestado = exemplarService.obterPorId(3);
        Emprestimo emprestimoAberto = emprestimoService.obterPorId(2);

        assertEquals(2, horaDaEstrela.getExemplares().size());
        assertEquals(1, horaDaEstrela.getQuantidadeExemplaresDisponiveis());
        assertEquals(1, maria.getEmprestimos().size());
        assertFalse(exemplarEmprestado.getDisponivel());
        assertFalse(emprestimoAberto.estaDevolvido());
    }
}
