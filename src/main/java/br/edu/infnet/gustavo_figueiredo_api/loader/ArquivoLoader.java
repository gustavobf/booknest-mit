package br.edu.infnet.gustavo_figueiredo_api.loader;

import br.edu.infnet.gustavo_figueiredo_api.model.*;
import br.edu.infnet.gustavo_figueiredo_api.service.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

@Component
public class ArquivoLoader {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final AutorService autorService = new AutorService();
    private final CategoriaService categoriaService = new CategoriaService();
    private final EditoraService editoraService = new EditoraService();
    private final UsuarioService usuarioService = new UsuarioService();
    private final LivroService livroService = new LivroService();
    private final ExemplarService exemplarService = new ExemplarService();
    private final EmprestimoService emprestimoService = new EmprestimoService();

    public void carregarDados (String caminhoAutores, String caminhoCategoria, String caminhoEditoras,
                               String caminhoUsuarios, String caminhoLivros, String caminhoExemplares,
                               String caminhoEmprestimos) {
        carregarAutores(caminhoAutores);
        carregarCategorias(caminhoCategoria);
        carregarEditoras(caminhoEditoras);
        carregarUsuarios(caminhoUsuarios);
        carregarLivros(caminhoLivros);
        carregarExemplares(caminhoExemplares);
        carregarEmprestimos(caminhoEmprestimos);
    }

    private void carregarAutores (String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4) {
                    Autor autor = new Autor(Integer.parseInt(partes[0].trim()), partes[1].trim(), partes[2].trim(),
                            Integer.parseInt(partes[3].trim()));
                    autorService.incluir(autor);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar autores: " + caminho, e);
        }
    }

    private void carregarCategorias (String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    Categoria categoria = new Categoria(Integer.parseInt(partes[0].trim()), partes[1].trim(),
                            partes[2].trim());
                    categoriaService.incluir(categoria);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar categorias: " + caminho, e);
        }
    }

    private void carregarEditoras (String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 5) {
                    Editora editora = new Editora(Integer.parseInt(partes[0].trim()), partes[1].trim(),
                            partes[2].trim(), partes[3].trim(), Boolean.parseBoolean(partes[4].trim()));
                    editoraService.incluir(editora);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar editoras: " + caminho, e);
        }
    }

    private void carregarUsuarios (String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 5) {
                    Usuario usuario = new Usuario(Integer.parseInt(partes[0].trim()), partes[1].trim(),
                            partes[2].trim(), partes[3].trim(), Boolean.parseBoolean(partes[4].trim()));
                    usuarioService.incluir(usuario);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar usuários: " + caminho, e);
        }
    }

    private void carregarLivros (String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 6) {
                    Integer idAutor = Integer.parseInt(partes[3].trim());
                    Integer idCategoria = Integer.parseInt(partes[4].trim());
                    Integer idEditora = Integer.parseInt(partes[5].trim());

                    Livro livro = new Livro(Integer.parseInt(partes[0].trim()), partes[1].trim(), partes[2].trim());
                    livro.setAutor(autorService.obterPorId(idAutor));
                    livro.setCategoria(categoriaService.obterPorId(idCategoria));
                    livro.setEditora(editoraService.obterPorId(idEditora));

                    livroService.incluir(livro);
                    livro.getAutor().adicionarLivro(livro);
                    livro.getCategoria().adicionarLivro(livro);
                    livro.getEditora().adicionarLivro(livro);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar livros: " + caminho, e);
        }
    }

    private void carregarExemplares (String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 5) {
                    Integer idLivro = Integer.parseInt(partes[4].trim());

                    Exemplar exemplar = new Exemplar(Integer.parseInt(partes[0].trim()), partes[1].trim(),
                            EstadoConservacao.fromDescricao(partes[2].trim()), Boolean.parseBoolean(partes[3].trim()));
                    exemplar.setLivro(livroService.obterPorId(idLivro));

                    exemplarService.incluir(exemplar);
                    exemplar.getLivro().adicionarExemplar(exemplar);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar exemplares: " + caminho, e);
        }
    }

    private void carregarEmprestimos (String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 7) {
                    Integer idUsuario = Integer.parseInt(partes[1].trim());
                    Integer idExemplar = Integer.parseInt(partes[6].trim());

                    Emprestimo emprestimo = new Emprestimo(Integer.parseInt(partes[0].trim()),
                            LocalDate.parse(partes[2].trim(), DATE_FORMATTER),
                            partes[3].isEmpty() ? null : LocalDate.parse(partes[3].trim(), DATE_FORMATTER),
                            partes[4].isEmpty() ? null : LocalDate.parse(partes[4].trim(), DATE_FORMATTER),
                            Double.parseDouble(partes[5].trim()));

                    emprestimo.setUsuario(usuarioService.obterPorId(idUsuario));
                    emprestimo.setExemplar(exemplarService.obterPorId(idExemplar));

                    emprestimoService.incluir(emprestimo);
                    emprestimo.getUsuario().adicionarEmprestimo(emprestimo);
                    emprestimo.getExemplar().adicionarEmprestimo(emprestimo);
                    emprestimo.getExemplar().getLivro().adicionarEmprestimo(emprestimo);
                    if (emprestimo.estaDevolvido()) {
                        emprestimo.getExemplar().registrarDevolucao();
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar empréstimos: " + caminho, e);
        }
    }

    public void exibirDados () {
        System.out.println("\n========== AUTORES ==========");
        autorService.obterLista().forEach(Entidade::exibir);

        System.out.println("\n========== CATEGORIAS ==========");
        categoriaService.obterLista().forEach(Entidade::exibir);

        System.out.println("\n========== EDITORAS ==========");
        editoraService.obterLista().forEach(Entidade::exibir);

        System.out.println("\n========== USUÁRIOS ==========");
        usuarioService.obterLista().forEach(Entidade::exibir);

        System.out.println("\n========== LIVROS ==========");
        livroService.obterLista().forEach(Entidade::exibir);

        System.out.println("\n========== EXEMPLARES ==========");
        exemplarService.obterLista().forEach(Entidade::exibir);

        System.out.println("\n========== HISTÓRICO DE EMPRÉSTIMOS ==========");
        emprestimoService.obterLista().forEach(Entidade::exibir);
    }

    public List<Autor> getAutores () {
        return autorService.obterLista();
    }

    public List<Categoria> getCategorias () {
        return categoriaService.obterLista();
    }

    public List<Editora> getEditoras () {
        return editoraService.obterLista();
    }

    public List<Usuario> getUsuarios () {
        return usuarioService.obterLista();
    }

    public List<Livro> getLivros () {
        return livroService.obterLista();
    }

    public List<Exemplar> getExemplares () {
        return exemplarService.obterLista();
    }

    public List<Emprestimo> getEmprestimos () {
        return emprestimoService.obterLista();
    }

    public AutorService getAutorService () {
        return autorService;
    }

    public CategoriaService getCategoriaService () {
        return categoriaService;
    }

    public EditoraService getEditoraService () {
        return editoraService;
    }

    public UsuarioService getUsuarioService () {
        return usuarioService;
    }

    public LivroService getLivroService () {
        return livroService;
    }

    public ExemplarService getExemplarService () {
        return exemplarService;
    }

    public EmprestimoService getEmprestimoService () {
        return emprestimoService;
    }
}
