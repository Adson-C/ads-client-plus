# ADS Client Plus

## Descrição do Projeto

ADS Client Plus é uma API RESTful desenvolvida para gerenciar um sistema de assinaturas e pagamentos. A aplicação permite o gerenciamento de usuários, diferentes tipos de assinaturas e processamento de pagamentos de forma segura e eficiente.

## Tecnologias Utilizadas

- **Java 8+**
- **Spring Boot**: Framework para criação de aplicações Java stand-alone
- **Spring MVC**: Para criação da camada de controle RESTful
- **Spring Data JPA**: Para persistência de dados
- **Spring Security**: Para autenticação e autorização com JWT
- **Swagger/SpringFox**: Para documentação da API
- **Maven**: Para gerenciamento de dependências
- **MySQL/PostgreSQL**: Banco de dados (altere conforme sua configuração)

## Estrutura do Projeto

O projeto segue uma arquitetura de camadas:

- **Controller**: Responsável por receber as requisições HTTP
- **Service**: Contém a lógica de negócio
- **Repository**: Acesso ao banco de dados
- **Model**: Entidades JPA
- **DTO**: Objetos de transferência de dados
- **Configuration**: Classes de configuração do sistema

## Principais Funcionalidades

### Gestão de Usuários
- Cadastro de usuários
- Upload e download de foto de perfil
- Diferentes tipos de usuário com permissões específicas

### Gestão de Assinaturas
- Cadastro, listagem, atualização e remoção de tipos de assinatura
- Consulta de assinaturas por ID

### Processamento de Pagamentos
- Processamento seguro de pagamentos
- Integração com gateway de pagamento

## Endpoints da API

### Usuários
- `POST /user`: Criar novo usuário
- `PATCH /user/{id}/uploadPhoto`: Upload de foto de perfil
- `GET /user/{id}/photo`: Download de foto de perfil

### Tipos de Usuário
- `GET /usertype`: Listar todos os tipos de usuário

### Tipos de Assinatura
- `GET /subscriptions-type`: Listar todos os tipos de assinatura
- `GET /subscriptions-type/{id}`: Buscar tipo de assinatura por ID
- `POST /subscriptions-type`: Criar novo tipo de assinatura
- `PUT /subscriptions-type/{id}`: Atualizar tipo de assinatura
- `DELETE /subscriptions-type/{id}`: Excluir tipo de assinatura

### Pagamentos
- `POST /payment/process`: Processar pagamento

## Segurança

A API utiliza autenticação JWT (JSON Web Token) para proteger os endpoints. A maioria dos endpoints requer um token válido no cabeçalho de autorização.

## Documentação da API

A documentação completa da API está disponível através do Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

## Como Executar o Projeto

1. Clone o repositório:
```
git clone https://github.com/seu-usuario/ads-client-plus.git
```

2. Entre na pasta do projeto:
```
cd ads-client-plus
```

3. Compile o projeto:
```
mvn clean install
```

4. Execute a aplicação:
```
mvn spring-boot:run
```

5. Acesse a documentação da API:
```
http://localhost:8080/swagger-ui.html
```

## Configuração do Banco de Dados

Configure o arquivo `application.properties` ou `application.yml` com as informações do seu banco de dados:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ads_client_plus
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Requisitos

- Java 8 ou superior
- Maven 3.6 ou superior
- MySQL/PostgreSQL

## Contribuição

Para contribuir com o projeto, siga os passos:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Faça commit das suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Faça push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença [Adson Sa]. Veja o arquivo LICENSE para mais detalhes.

## Contato

Para mais informações, entre em contato através do e-mail: [adsoncoconceicao442@gmail.com]

---

Desenvolvido por [Adson Sá] - [2025]
