# RESTfulAPI

API RESTful desenvolvida no Spark Framework junto ao SQL2o.

## Instalação

1. Instale o [Eclipse Neon](http://www.eclipse.org/neon/);
2. Instale o [MySQL Workbench CE](https://www.mysql.com/products/workbench/);
3. Clone o projeto no Eclipse. Se o projeto apresentar algum erro, clique com o botão direito do mouse no projeto, vá em *Maven > Update Project* e mande atualizar o projeto;
4. Importe o arquivo .sql do repositório no MySQL Workbench;
5. Crie um usuário no MySQL Workbench com login *restfulapi* e senha *restfulapi*. Dê a ele acesso ao
schema *restfulapi* e garanta que ele tenha acesso de escrita e leitura no banco;
6. Para ter acesso aos serviços da API, é preciso realizar métodos POST e GET. Para tal, pode-se utilizar a ferramenta
[Postman](https://www.getpostman.com/);

## Funções

A aplicação roda na porta 8080. O retorno esperado é sempre do tipo JSON.

### Verificação de teste
* GET -> localhost:8080/helloworld
* POST -> localhost:8080/helloworld

### Consultar unidades de grandeza
* GET -> localhost:8080/units

### Consultar sensores de um usuário
* GET -> localhost:8080/users/:user
```
O parâmetro :user representa o username do usuário
```
### Consultar um sensor específico
* GET -> localhost:8080/sensors/:sensor
```
O parâmetro :sensor representa a chave do sensor
```
### Consultar dados de um stream específico
* GET -> localhost:8080/data/:stream
```
O parâmetro :stream representa a chave do stream
```
### Registrar sensor para um usuário
* POST -> localhost:8080/sensors/:user
```
O parâmetro :user representa o username do usuário
```
### Registrar stream para um sensor
* POST -> localhost:8080/streams/:sensor
```
O parâmetro :sensor representa a chave do sensor
```
### Publicar dado em um stream
* POST -> localhost:8080/data/:stream
```
O parâmetro :stream representa a chave do stream
```
### Criar um usuário
* POST -> localhost:8080/users

### Criar uma unidade
* POST -> localhost:8080/units
