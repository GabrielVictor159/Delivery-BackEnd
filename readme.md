
 Método | Rota                                 | Produz            | Descrição                                                                                      |
|--------|--------------------------------------|-------------------|------------------------------------------------------------------------------------------------|
| GET    | /Admins/{nome}/{senha}               | application/json  | Obtém um objeto `Admin` com base no nome de usuário e senha fornecidos.             

| Método   | Rota                                 | Produz            | Descrição                                                                                                     |
|----------|--------------------------------------|-------------------|---------------------------------------------------------------------------------------------------------------|
| GET      | /Categorias                          | application/json  | Obtém uma lista de todas as categorias.                                                                        |
| GET      | /Categorias/{id}                     | application/json  | Obtém uma categoria específica com base no ID fornecido.                                                       |
| POST     | /Categorias/{nomeAdmin}/{senhaAdmin}  | application/json  | Adiciona uma nova categoria com base no nome de usuário e senha do administrador fornecidos.                  |
| PUT      | /Categorias/{id}/{nomeAdmin}/{senhaAdmin} | application/json | Atualiza uma categoria existente com base no ID fornecido e nas credenciais do administrador fornecidas.    |
| DELETE   | /Categorias/{id}/{nomeAdmin}/{senhaAdmin} | application/json | Exclui uma categoria existente com base no ID fornecido e nas credenciais do administrador fornecidas.        |

| Método   | Rota                                     | Produz            | Descrição                                                                                                            |
|----------|------------------------------------------|-------------------|----------------------------------------------------------------------------------------------------------------------|
| POST     | /Produtos/getAll                          | application/json  | Obtém produtos paginados com base nos parâmetros fornecidos.                                                         |
| GET      | /Produtos/{id}                            | application/json  | Obtém um produto específico com base no ID fornecido.                                                                 |
| POST     | /Produtos/getByIds                        | application/json  | Obtém produtos por uma lista de IDs fornecida.                                                                        |
| POST     | /Produtos/{nomeAdmin}/{senhaAdmin}         | application/json  | Adiciona um novo produto com base nas credenciais do administrador fornecidas.                                       |
| PUT      | /Produtos/{id}/{nomeAdmin}/{senhaAdmin}    | application/json  | Atualiza um produto existente com base no ID fornecido e nas credenciais do administrador fornecidas.               |
| DELETE   | /Produtos/{id}/{nomeAdmin}/{senhaAdmin}    | application/json  | Exclui um produto existente com base no ID fornecido e nas credenciais do administrador fornecidas.                  |

| Método   | Rota                               | Produz                    | Descrição                                                                                                   |
|----------|------------------------------------|---------------------------|-------------------------------------------------------------------------------------------------------------|
| GET    | /Pedidos/{nomeAdmin}/{senhaAdmin}        | application/json        | Busca pedidos paginados. Requer autenticação de administrador.                                               |
| GET    | /Pedidos/{id}                            | application/json        | Busca um pedido pelo ID.                                                                                     |
| POST   | /Pedidos                                | application/json        | Adiciona um novo pedido.                                                                                     |
| PUT    | /Pedidos/BanirIp/{id}/{nomeAdmin}/{senhaAdmin} | N/A                       | Bane um endereço IP. Requer autenticação de administrador.                                                   |

