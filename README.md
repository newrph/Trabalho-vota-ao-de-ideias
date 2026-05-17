# Sistema de Votação de Ideias

## Sobre o Projeto

O Sistema de Votação de Ideias é uma aplicação web desenvolvida utilizando Java Web com JSP, Servlets e MySQL. O objetivo principal do sistema é permitir que usuários cadastrem ideias e realizem votações nas propostas consideradas mais relevantes.

---

# Tecnologias Utilizadas

- Java
- JSP
- Servlet
- HTML5
- CSS3
- Maven
- MySQL
- XAMPP
- Apache Tomcat

---

# Arquitetura do Sistema

O sistema utiliza o padrão MVC:

- Model → Banco de dados
- View → JSP
- Controller → Servlets

Fluxo:

Usuário → JSP → Controller → Banco MySQL

---

# Estrutura do Projeto

```text
src
 └── main
      ├── java
      │     ├── controller
      │     │      ├── IdeiaController.java
      │     │      ├── LoginController.java
      │     │      └── VotacaoController.java
      │     │
      │     └── dao
      │            └── Conexao.java
      │
      └── webapp
            ├── css
            │     └── style.css
            │
            ├── login.jsp
            ├── inicio.jsp
            ├── ideias.jsp
            ├── votos.jsp
            └── WEB-INF
```

---

# Funcionalidades

## Login

Permite acesso ao sistema através de email e senha.

## Cadastro de Ideias

Usuários podem cadastrar:
- título
- descrição

As informações são salvas no banco de dados.

## Sistema de Votação

Usuários podem votar nas ideias cadastradas.

---

# Controllers

## IdeiaController

Responsável por:
- receber dados do formulário
- salvar ideias
- conectar ao banco

## VotacaoController

Responsável por:
- registrar votos
- relacionar ideias e usuários

## LoginController

Responsável por:
- validar login
- controlar acesso

---

# Banco de Dados

Banco utilizado:

```sql
escola
```

## Tabela usuarios

Armazena os usuários do sistema.

| Campo | Função |
|---|---|
| id | identificador |
| nome | nome do usuário |
| email | login |
| senha | senha |

---

## Tabela ideias

Armazena ideias cadastradas.

| Campo | Função |
|---|---|
| id | identificador |
| titulo | título |
| descricao | descrição |
| usuario_id | autor |

---

## Tabela votos

Armazena votos realizados.

| Campo | Função |
|---|---|
| id | identificador |
| usuario_id | usuário |
| ideia_id | ideia votada |

---

# Fluxo do Sistema

## Cadastro de Ideia

Usuário preenche formulário  
↓  
IdeiaController  
↓  
Banco MySQL

---

## Processo de Votação

Usuário clica em votar  
↓  
VotacaoController  
↓  
Banco MySQL

---


# Como Executar

## 1. Iniciar XAMPP

Ativar:
- Apache
- MySQL

---

## 2. Criar banco

```sql
CREATE DATABASE escola;
```

---

## 3. Compilar projeto

```bash
mvn clean package
```

---

## 4. Copiar WAR

Copiar:

```text
target/escola.war
```

para:

```text
Tomcat/webapps
```

---

## 5. Iniciar Tomcat

```text
startup.bat
```

---

## 6. Acessar sistema

```text
http://localhost:8080/escola
```

---

# Conclusão

O projeto demonstra o funcionamento de uma aplicação Java Web utilizando MVC, JSP, Servlets e MySQL, permitindo compreender na prática o fluxo entre interface, lógica e banco de dados.
