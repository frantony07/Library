homework by university 
Crie um projeto Spring Boot do zero (Spring Initializr) com as dependências: Spring Web, Spring Data JPA, H2 Database e Lombok.


A API gerencia o acervo de uma biblioteca. Uma única entidade  sem relacionamentos.


A entidade Livro

ID,

TITULO,

AUTOR,

GENERO,

ANO PUBLICACAO

PAGINAS

DISPONIVEL


Desenvolver um CRUD COMPLETO:

POST /api/livros

GET /api/livros

GET /api/livros/{id}

PUT /api/livros/{id}

PATCH /api/livros/{id}

DELETE /api/livros/{id}


Parte 2: três buscas com query method


Nenhuma delas pode usar findAll() com filtro em Java. O filtro tem que sair do repository.


1. GET /api/livros/genero/{genero}; livros de um gênero

2. GET /api/livros/disponiveis; só os que estão com disponivel = true

3. GET /api/livros/buscar?autor=X&anoMin=Y; por autor e ano mínimo, usando @RequestParam


Parte 3 — uma regra de negócio


PATCH /api/livros/{id}/emprestar; marca o livro como indisponível.


Se o livro já estiver emprestado, retornar 409 CONFLICT com a mensagem "Livro já está emprestado". Se não existir, 404.
