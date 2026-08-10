package br.edu.infnet.gustavo_figueiredo_api.runner;

import br.edu.infnet.gustavo_figueiredo_api.loader.*;
import org.springframework.boot.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.net.*;

@Component
public class BibliotecaRunner implements ApplicationRunner {

    @Override
    public void run (ApplicationArguments args) {
        System.out.println("\n========== SISTEMA DE BIBLIOTECA ==========\n");

        ArquivoLoader loader = new ArquivoLoader();

        try {
            URL resourceUrl = getClass().getClassLoader().getResource("data");
            if (resourceUrl != null) {
                File dataDir = new File(resourceUrl.toURI());
                String baseDir = dataDir.getAbsolutePath() + File.separator;

                loader.carregarDados(baseDir + "autores.txt", baseDir + "categorias.txt", baseDir + "editoras.txt",
                        baseDir + "usuarios.txt", baseDir + "livros.txt", baseDir + "exemplares.txt",
                        baseDir + "emprestimos.txt");
            } else {
                System.err.println("Diretório de recursos 'data' não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao obter diretório de recursos: " + e.getMessage());
        }

        loader.exibirDados();

        System.out.println("\n========== RESUMO ==========");
        System.out.println("Total de autores: " + loader.getAutores().size());
        System.out.println("Total de categorias: " + loader.getCategorias().size());
        System.out.println("Total de editoras: " + loader.getEditoras().size());
        System.out.println("Total de usuários: " + loader.getUsuarios().size());
        System.out.println("Total de livros: " + loader.getLivros().size());
        System.out.println("Total de exemplares: " + loader.getExemplares().size());
        System.out.println("Exemplares disponíveis: " + loader.getExemplares().stream()
                .filter(exemplar -> Boolean.TRUE.equals(exemplar.getDisponivel())).count());
        System.out.println("Total de empréstimos: " + loader.getEmprestimos().size());

        System.out.println("\n========== CONSULTAS ==========");
        System.out.println("Autores em ordem alfabética: " + loader.getAutorService().listarOrdenadosPorNome().stream()
                .map(br.edu.infnet.gustavo_figueiredo_api.model.Autor::getNome).toList());
        System.out.println("Usuários ativos: " + loader.getUsuarioService().listarAtivos().stream()
                .map(br.edu.infnet.gustavo_figueiredo_api.model.Usuario::getNome).toList());
        System.out.println(
                "Livros ordenados por título: " + loader.getLivroService().listarOrdenadosPorTitulo().stream()
                        .map(br.edu.infnet.gustavo_figueiredo_api.model.Livro::getTitulo).toList());
        System.out.println("Livros do autor 1: " + loader.getLivroService().buscarPorAutor(1).stream()
                .map(br.edu.infnet.gustavo_figueiredo_api.model.Livro::getTitulo).toList());
        System.out.println("Livros disponíveis: " + loader.getLivroService().listarDisponiveis().stream()
                .map(br.edu.infnet.gustavo_figueiredo_api.model.Livro::getTitulo).toList());
        System.out.println("Títulos em maiúsculo: " + loader.getLivroService().listarTitulosMaiusculos());
        System.out.println("Empréstimos em aberto: " + loader.getEmprestimoService().listarEmAberto().size());
        System.out.println("Empréstimos atrasados: " + loader.getEmprestimoService().listarAtrasados().size());
        System.out.println("Editoras ativas: " + loader.getEditoraService().listarAtivas().stream()
                .map(br.edu.infnet.gustavo_figueiredo_api.model.Editora::getNome).toList());
    }
}
