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
