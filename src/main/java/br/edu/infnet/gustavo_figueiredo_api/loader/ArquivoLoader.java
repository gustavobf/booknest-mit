package br.edu.infnet.gustavo_figueiredo_api.loader;

import br.edu.infnet.gustavo_figueiredo_api.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ArquivoLoader {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private List<Autor> autores = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private List<Editora> editoras = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Livro> livros = new ArrayList<>();
    private List<Exemplar> exemplares = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public void carregarDados(String caminhoAutores, String caminhoCategoria, String caminhoEditoras,
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

    private void carregarAutores(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 4) {
                    Autor autor = new Autor(
                            Integer.parseInt(partes[0].trim()),
                            partes[1].trim(),
                            partes[2].trim(),
                            Integer.parseInt(partes[3].trim())
                    );
                    autores.add(autor);
                }
            }
            System.out.println(autores.size() + " autores carregados");
        } catch (IOException e) {
            System.err.println("Erro ao carregar autores: " + e.getMessage());
        }
    }

    private void carregarCategorias(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    Categoria categoria = new Categoria(
                            Integer.parseInt(partes[0].trim()),
                            partes[1].trim(),
                            partes[2].trim()
                    );
                    categorias.add(categoria);
                }
            }
            System.out.println(categorias.size() + " categorias carregadas");
        } catch (IOException e) {
            System.err.println("Erro ao carregar categorias: " + e.getMessage());
        }
    }

    private void carregarEditoras(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 5) {
                    Editora editora = new Editora(
                            Integer.parseInt(partes[0].trim()),
                            partes[1].trim(),
                            partes[2].trim(),
                            partes[3].trim(),
                            Boolean.parseBoolean(partes[4].trim())
                    );
                    editoras.add(editora);
                }
            }
            System.out.println(editoras.size() + " editoras carregadas");
        } catch (IOException e) {
            System.err.println("Erro ao carregar editoras: " + e.getMessage());
        }
    }

    private void carregarUsuarios(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 5) {
                    Usuario usuario = new Usuario(
                            Integer.parseInt(partes[0].trim()),
                            partes[1].trim(),
                            partes[2].trim(),
                            partes[3].trim(),
                            Boolean.parseBoolean(partes[4].trim())
                    );
                    usuarios.add(usuario);
                }
            }
            System.out.println(usuarios.size() + " usuários carregados");
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    private void carregarLivros(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 6) {
                    int idAutor = Integer.parseInt(partes[3].trim());
                    int idCategoria = Integer.parseInt(partes[4].trim());
                    int idEditora = Integer.parseInt(partes[5].trim());

                    Optional<Autor> autor = autores.stream().filter(a -> Objects.equals(a.getId(), idAutor)).findFirst();
                    Optional<Categoria> categoria = categorias.stream().filter(c -> Objects.equals(c.getId(), idCategoria)).findFirst();
                    Optional<Editora> editora = editoras.stream().filter(e -> Objects.equals(e.getId(), idEditora)).findFirst();

                    Livro livro = new Livro(
                            Integer.parseInt(partes[0].trim()),
                            partes[1].trim(),
                            partes[2].trim()
                    );

                    if (autor.isPresent()) {
                        livro.setAutor(autor.get());
                        autor.get().adicionarLivro(livro);
                    }
                    if (categoria.isPresent()) {
                        livro.setCategoria(categoria.get());
                        categoria.get().adicionarLivro(livro);
                    }
                    if (editora.isPresent()) {
                        livro.setEditora(editora.get());
                        editora.get().adicionarLivro(livro);
                    }

                    livros.add(livro);
                }
            }
            System.out.println(livros.size() + " livros carregados");
        } catch (IOException e) {
            System.err.println("Erro ao carregar livros: " + e.getMessage());
        }
    }

    private void carregarExemplares(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 5) {
                    int idLivro = Integer.parseInt(partes[4].trim());
                    Optional<Livro> livro = livros.stream().filter(item -> Objects.equals(item.getId(), idLivro)).findFirst();

                    Exemplar exemplar = new Exemplar(
                            Integer.parseInt(partes[0].trim()),
                            partes[1].trim(),
                            EstadoConservacao.fromDescricao(partes[2].trim()),
                            Boolean.parseBoolean(partes[3].trim())
                    );

                    if (livro.isPresent()) {
                        exemplar.setLivro(livro.get());
                        livro.get().adicionarExemplar(exemplar);
                    }

                    exemplares.add(exemplar);
                }
            }
            System.out.println(exemplares.size() + " exemplares carregados");
        } catch (IOException e) {
            System.err.println("Erro ao carregar exemplares: " + e.getMessage());
        }
    }

    private void carregarEmprestimos(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 7) {
                    int idUsuario = Integer.parseInt(partes[1].trim());
                    int idExemplar = Integer.parseInt(partes[6].trim());
                    Optional<Usuario> usuario = usuarios.stream().filter(item -> Objects.equals(item.getId(), idUsuario)).findFirst();
                    Optional<Exemplar> exemplar = exemplares.stream().filter(item -> Objects.equals(item.getId(), idExemplar)).findFirst();

                    LocalDate dataEmprestimo = LocalDate.parse(partes[2].trim(), DATE_FORMATTER);
                    LocalDate dataEsperadaDevolucao = partes[3].isEmpty() ? null : LocalDate.parse(partes[3].trim(), DATE_FORMATTER);
                    LocalDate dataDevolucao = partes[4].isEmpty() ? null : LocalDate.parse(partes[4].trim(), DATE_FORMATTER);

                    Emprestimo emprestimo = new Emprestimo(
                            Integer.parseInt(partes[0].trim()),
                            dataEmprestimo,
                            dataEsperadaDevolucao,
                            dataDevolucao,
                            Double.parseDouble(partes[5].trim())
                    );

                    if (usuario.isPresent()) {
                        emprestimo.setUsuario(usuario.get());
                        usuario.get().adicionarEmprestimo(emprestimo);
                    }

                    if (exemplar.isPresent()) {
                        emprestimo.setExemplar(exemplar.get());
                        exemplar.get().adicionarEmprestimo(emprestimo);
                        if (exemplar.get().getLivro() != null) {
                            exemplar.get().getLivro().adicionarEmprestimo(emprestimo);
                        }
                        if (emprestimo.estaDevolvido()) {
                            exemplar.get().registrarDevolucao();
                        }
                    }

                    emprestimos.add(emprestimo);
                }
            }
            System.out.println(emprestimos.size() + " empréstimos carregados");
        } catch (IOException e) {
            System.err.println("Erro ao carregar empréstimos: " + e.getMessage());
        }
    }

    public void exibirDados() {
        System.out.println("\n========== AUTORES ==========");
        autores.forEach(Entidade::exibir);

        System.out.println("\n========== CATEGORIAS ==========");
        categorias.forEach(Entidade::exibir);

        System.out.println("\n========== EDITORAS ==========");
        editoras.forEach(Entidade::exibir);

        System.out.println("\n========== USUÁRIOS ==========");
        usuarios.forEach(Entidade::exibir);

        System.out.println("\n========== LIVROS ==========");
        livros.forEach(Entidade::exibir);

        System.out.println("\n========== EXEMPLARES ==========");
        exemplares.forEach(Entidade::exibir);

        System.out.println("\n========== HISTÓRICO DE EMPRÉSTIMOS ==========");
        emprestimos.forEach(Entidade::exibir);
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Editora> getEditoras() {
        return editoras;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
}
