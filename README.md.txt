Orientações para rodar a aplicação:
 - realize o clone do projeto disponível em https://bitbucket.org/JeisonRu/desafio_southsystem_2/src/forkDesafio2 e entre na branch feature/AllFiles, ou obtenha apenas o arquivo docker-compose.yml na raiz do projeto;
 - abra o terminal na pasta principal do projeto ou na pasta que contiver o arquivo docker-compose.yml;
 - execute o comando "docker-compose up --build -d".

O banco de dados possui dois usuários cadastrados com as seguintes identificações:
 - username: user, password: 123456;
 - username: admin, password: 654321.

O phpMyAdmin estará disponível em http://localhost:8082, sob Utilizador: root, Palavra-passe: 3698741.

A URI base disponível será http://localhost:8080

A aplicação conta com autenticação via token JWT, que pode ser obtido via POST em http://localhost:8080/login, a partir de arquivo json com os dados de usuário informados acima.

A documentação de acesso ao servidor web encontra-se em http://localhost:8080/swagger-ui.html

Como o banco de dados possui apenas os usuários cadastrados, recomenda-se a adição inicial de produtos via POST em /products/file, através do arquivo mostruario_fabrica.csv.

Apenas o usuário admin possui permissão de deletar produtos e categorias.

As imagens do projeto estão disponíveis em https://hub.docker.com/repository/docker/jeickt/southsystem-desafio2
