# 📚 Sistema de Gestão de Biblioteca

Uma aplicação de gerenciamento de biblioteca desenvolvida com **Spring Boot**, **Java 21** e **Maven**, implementando conceitos de POO, relacionamentos entre entidades e carregamento de dados de arquivos.

## 🏗️ Entidades

| Classe | Atributos | Relacionamento |
|--------|-----------|----------------|
| **Autor** | id, nome, nacionalidade, anoNascimento, ativo | 1:N → Livro |
| **Categoria** | id, nome, descricao, taxaJuro, habilitada | 1:N → Livro |
| **Editora** | id, nome, cidade, emailContato, ativa | 1:N → Livro |
| **Livro** | id, titulo, isbn, preco, disponivel | N:1 ← Autor, Categoria, Editora |
| **Emprestimo** | id, nomeUsuario, dataEmprestimo, dataDevolucao, multa, devolvido | N:1 ← Livro |

## 🚀 Como Usar

### Pré-requisitos
- Java 21+
- Maven 3.6+

### Executar

```bash
# Compilar
./mvnw clean compile

# Rodar aplicação
./mvnw spring-boot:run
```

## 🛠️ Tecnologias

- Java 21
- Spring Boot 4.1.0
- Maven 3.9+

---