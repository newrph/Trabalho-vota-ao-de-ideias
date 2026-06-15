# Sistema de Votação de Ideias

## Sobre o Projeto

O Sistema de Votação de Ideias é uma aplicação web desenvolvida em Java utilizando JSP, Servlets, MySQL e uma API REST protegida por JWT.

O sistema permite que usuários realizem login, cadastrem ideias, votem em propostas e interajam por meio de comentários. A aplicação utiliza arquitetura MVC para as páginas web e disponibiliza endpoints REST para integração com aplicações externas ou front-ends modernos.

---

# Tecnologias Utilizadas

* Java 17+
* JSP
* Servlet
* Maven
* MySQL
* XAMPP
* Apache Tomcat
* JWT (JSON Web Token)
* Gson
* HTML5
* CSS3

---

# Arquitetura do Sistema

A aplicação utiliza dois modelos de acesso:

## Interface Web (MVC)

Model → Banco de Dados

View → JSP

Controller → Servlets

Fluxo:

Usuário → JSP → Controller → Banco MySQL

## API REST

Cliente → API REST → Service → Banco MySQL

Todas as rotas da API são protegidas por JWT, exceto o endpoint de autenticação.

---

# Estrutura do Projeto

src
└── main
├── java
│
├── api
│ ├── AuthApiController.java
│ ├── IdeiaApiController.java
│ └── VotacaoApiController.java
│
├── controller
│ ├── IdeiaController.java
│ ├── LoginController.java
│ └── VotacaoController.java
│
├── dao
│ └── Conexao.java
│
├── dto
│ ├── LoginDTO.java
│ ├── VotoDTO.java
│ └── ComentarioDTO.java
│
├── model
│ ├── Usuario.java
│ ├── Ideia.java
│ └── Voto.java
│
├── service
│ ├── UsuarioService.java
│ ├── IdeiaService.java
│ └── VotacaoService.java
│
└── security
├── JwtUtil.java
└── JwtFilter.java

└── webapp
├── css
│ └── style.css
│
├── inicio.jsp
├── login.jsp
├── ideias.jsp
├── votos.jsp
│
└── WEB-INF

---

# Funcionalidades

## Login

Permite autenticação através de:

* Email
* Senha

Após autenticação pela API é gerado um Token JWT.

---

## Cadastro de Ideias

Usuários podem cadastrar:

* Título
* Descrição

As informações são persistidas no banco MySQL.

---

## Sistema de Votação

Usuários autenticados podem:

* Visualizar ideias
* Registrar votos
* Participar do processo de seleção

---

## Sistema de Comentários

Usuários autenticados podem comentar ideias cadastradas.

---

# API REST

## Autenticação

### Login

POST

/api/auth/login

Request:

```json
{
  "email": "usuario@email.com",
  "senha": "123456"
}
```

Response:

```json
{
  "token": "jwt-token",
  "usuario": "Nome do Usuário"
}
```

---

## Ideias

### Criar Ideia

POST

/api/ideias

Header:

Authorization: Bearer TOKEN

Request:

```json
{
  "titulo": "Nova Ideia",
  "descricao": "Descrição da ideia"
}
```

Response:

```json
{
  "mensagem": "Ideia criada"
}
```

---

## Votação

### Registrar Voto

POST

/api/votacao/voto

Header:

Authorization: Bearer TOKEN

Request:

```json
{
  "ideiaId": 1,
  "usuarioId": 2
}
```

Response:

```json
{
  "mensagem": "Voto registrado"
}
```

---

## Comentários

### Registrar Comentário

POST

/api/votacao/comentario

Header:

Authorization: Bearer TOKEN

Request:

```json
{
  "ideiaId": 1,
  "usuarioId": 2,
  "texto": "Excelente proposta."
}
```

Response:

```json
{
  "mensagem": "Comentário registrado"
}
```

---

# Segurança

A API utiliza JWT (JSON Web Token).

## Endpoints Públicos

* /api/auth/login

## Endpoints Protegidos

* /api/ideias
* /api/votacao/voto
* /api/votacao/comentario

O filtro JwtFilter intercepta todas as requisições para:

/api/*

e valida automaticamente o token enviado no cabeçalho Authorization.

Exemplo:

Authorization: Bearer eyJhbGciOiJIUzI1Ni...

---

# Banco de Dados

Banco:

escola

## Tabela usuarios

| Campo | Função        |
| ----- | ------------- |
| id    | Identificador |
| nome  | Nome          |
| email | Login         |
| senha | Senha         |

## Tabela ideias

| Campo      | Função        |
| ---------- | ------------- |
| id         | Identificador |
| titulo     | Título        |
| descricao  | Descrição     |
| usuario_id | Autor         |

## Tabela votos

| Campo      | Função        |
| ---------- | ------------- |
| id         | Identificador |
| usuario_id | Usuário       |
| ideia_id   | Ideia votada  |

---

# Fluxo da Aplicação

## Login

Usuário
↓
AuthApiController
↓
JWT
↓
Acesso liberado

---

## Cadastro de Ideia

Usuário
↓
IdeiaApiController
↓
IdeiaService
↓
Banco MySQL

---

## Registro de Voto

Usuário
↓
VotacaoApiController
↓
VotacaoService
↓
Banco MySQL

---

# Como Executar

## 1. Iniciar XAMPP

Ativar:

* Apache
* MySQL

---

## 2. Criar Banco

```sql
CREATE DATABASE escola;
```

---

## 3. Compilar Projeto

```bash
mvn clean package
```

---

## 4. Gerar Arquivo WAR

O Maven criará:

target/escola.war

---

## 5. Implantar no Tomcat

Copiar:

target/escola.war

para:

Tomcat/webapps

---

## 6. Iniciar Tomcat

Windows:

```bash
startup.bat
```

Linux:

```bash
./startup.sh
```

---

## 7. Acessar Sistema

Interface Web:

http://localhost:8080/escola

API REST:

http://localhost:8080/escola/api

---

# Conclusão

O projeto demonstra a construção de uma aplicação Java Web completa utilizando JSP, Servlets, MySQL e API REST com autenticação JWT. A solução integra interface web tradicional e serviços REST seguros, aplicando conceitos de MVC, DTO, Service Layer, autenticação baseada em tokens e boas práticas de desenvolvimento corporativo.
