package br.edu.infnet.gustavo_figueiredo_api.loader;

import br.edu.infnet.gustavo_figueiredo_api.model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArquivoLoader {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private List<Autor> autores = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();
    private List<Editora> editoras = new ArrayList<>();
    private List<Livro> livros = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public void carregarDados(String caminhoAutores, String caminhoCategoria, String caminhoEditoras, String caminhoLivros, String caminhoEmprestimos) {
        carregarAutores(caminhoAutores);
        carregarCategorias(caminhoCategoria);
        carregarEditoras(caminhoEditoras);
        carregarLivros(caminhoLivros);
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

    private void carregarLivros(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 8) {
                    int idAutor = Integer.parseInt(partes[4].trim());
                    int idCategoria = Integer.parseInt(partes[5].trim());
                    int idEditora = Integer.parseInt(partes[7].trim());
                    boolean disponivel = Boolean.parseBoolean(partes[6].trim());

                    Optional<Autor> autor = autores.stream().filter(a -> a.getId() == idAutor).findFirst();
                    Optional<Categoria> categoria = categorias.stream().filter(c -> c.getId() == idCategoria).findFirst();
                    Optional<Editora> editora = editoras.stream().filter(e -> e.getId() == idEditora).findFirst();

                    Livro livro = new Livro(
                            Integer.parseInt(partes[0].trim()),
                            partes[1].trim(),
                            partes[2].trim(),
                            Double.parseDouble(partes[3].trim()),
                            disponivel
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

    private void carregarEmprestimos(String caminho) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 7) {
                    int idLivro = Integer.parseInt(partes[6].trim());
                    Optional<Livro> livro = livros.stream().filter(l -> l.getId() == idLivro).findFirst();

                    LocalDate dataEmprestimo = LocalDate.parse(partes[2].trim(), DATE_FORMATTER);
                    LocalDate dataEsperadaDevolucao = partes[3].isEmpty() ? null : LocalDate.parse(partes[3].trim(), DATE_FORMATTER);
                    LocalDate dataDevolucao = partes[4].isEmpty() ? null : LocalDate.parse(partes[4].trim(), DATE_FORMATTER);

                    Emprestimo emprestimo = new Emprestimo(
                            Integer.parseInt(partes[0].trim()),
                            partes[1].trim(),
                            dataEmprestimo,
                            dataEsperadaDevolucao,
                            dataDevolucao,
                            Double.parseDouble(partes[5].trim())
                    );

                    if (livro.isPresent()) {
                        emprestimo.setLivro(livro.get());
                        livro.get().adicionarEmprestimo(emprestimo);
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

        System.out.println("\n========== LIVROS ==========");
        livros.forEach(Entidade::exibir);

        System.out.println("\n========== EMPRÉSTIMOS ==========");
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

    public List<Livro> getLivros() {
        return livros;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
}
