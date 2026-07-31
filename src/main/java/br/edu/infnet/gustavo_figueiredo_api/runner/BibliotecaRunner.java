package br.edu.infnet.gustavo_figueiredo_api.runner;

import br.edu.infnet.gustavo_figueiredo_api.loader.ArquivoLoader;
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
                File dataDir = new File(resourceUrl.getFile());
                String baseDir = dataDir.getAbsolutePath() + File.separator;
                
        loader.carregarDados(
                baseDir + "autores.txt",
                baseDir + "categorias.txt",
                baseDir + "editoras.txt",
                baseDir + "livros.txt",
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
        System.out.println("Total de livros: " + loader.getLivros().size());
        System.out.println("Total de empréstimos: " + loader.getEmprestimos().size());
    }
}
