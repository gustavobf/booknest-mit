package br.edu.infnet.gustavo_figueiredo_api.runner;

import br.edu.infnet.gustavo_figueiredo_api.loader.ArquivoLoader;
import br.edu.infnet.gustavo_figueiredo_api.model.Emprestimo;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;

@Component
public class BibliotecaRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("\n========== SISTEMA DE BIBLIOTECA ==========\n");

        ArquivoLoader loader = new ArquivoLoader();

        try {
            URL resourceUrl = getClass().getClassLoader().getResource("data");
            if (resourceUrl != null) {
                File dataDir = new File(resourceUrl.toURI());
                String baseDir = dataDir.getAbsolutePath() + File.separator;

                loader.carregarDados(
                        baseDir + "autores.txt",
                        baseDir + "categorias.txt",
                        baseDir + "editoras.txt",
                        baseDir + "usuarios.txt",
                        baseDir + "livros.txt",
                        baseDir + "exemplares.txt",
                        baseDir + "emprestimos.txt"
                );
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
                .filter(exemplar -> Boolean.TRUE.equals(exemplar.getDisponivel()))
                .count());
        System.out.println("Total de empréstimos: " + loader.getEmprestimos().size());
        System.out.println("Empréstimos em aberto: " + loader.getEmprestimos().stream()
                .filter(emprestimo -> !emprestimo.estaDevolvido())
                .count());
        System.out.println("Empréstimos atrasados: " + loader.getEmprestimos().stream()
                .filter(Emprestimo::estaAtrasado)
                .count());
    }
}
