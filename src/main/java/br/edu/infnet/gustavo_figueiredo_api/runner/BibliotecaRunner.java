package br.edu.infnet.gustavo_figueiredo_api.runner;

import br.edu.infnet.gustavo_figueiredo_api.loader.*;
import org.springframework.boot.*;
import org.springframework.stereotype.*;

@Component
public class BibliotecaRunner implements ApplicationRunner {
    private final ArquivoLoader loader;

    public BibliotecaRunner (ArquivoLoader loader) {
        this.loader = loader;
    }

    @Override
    public void run (ApplicationArguments args) {
        loader.carregarDadosDoClasspath();
    }
}
