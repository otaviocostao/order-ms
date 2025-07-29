
![alt text](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![alt text](https://img.shields.io/badge/Spring_Boot-3.5.4-6DB33F?style=for-the-badge&logo=spring)
![alt text](https://img.shields.io/badge/MongoDB-Latest-47A248?style=for-the-badge&logo=mongodb)
![alt text](https://img.shields.io/badge/RabbitMQ-3.13-FF6600?style=for-the-badge&logo=rabbitmq)
![alt text](https://img.shields.io/badge/Maven-3.8+-C71A36?style=for-the-badge&logo=apache-maven)

### 📖 Sobre o Projeto

Order-MS é um microsserviço backend construído com a stack Spring, projetado para gerenciar pedidos em um ecossistema de e-commerce. A arquitetura é orientada a eventos, permitindo que o serviço reaja a eventos de criação de pedidos gerados por outros sistemas, garantindo alta coesão e baixo acoplamento.

Este projeto demonstra práticas modernas de desenvolvimento backend, incluindo:

- Arquitetura de Microsserviços.
- Comunicação assíncrona com mensageria (RabbitMQ).
- Uso de um banco de dados NoSQL (MongoDB) com criação automática de índices.
- Exposição de uma API REST para consulta de dados com paginação.

### 🏛️ Arquitetura

O sistema opera com dois fluxos principais:

Fluxo Assíncrono (Criação de Pedidos):
- Um sistema externo (ex: um gateway de checkout) publica um evento OrderCreatedEvent em uma fila do RabbitMQ.
- O OrderCreatedListener dentro do Order-MS consome a mensagem.
- O OrderService processa o evento, calcula o total e salva a nova OrderEntity no MongoDB.

Fluxo Síncrono (Consulta de Pedidos):
- Um cliente faz uma requisição GET para a API REST do Order-MS.
- O OrderController recebe a requisição.
- O OrderService busca os dados no MongoDB, realiza agregações e retorna uma resposta paginada.

<img width="1249" height="736" alt="image" src="https://github.com/user-attachments/assets/11bcc3e8-5521-499c-91c1-4fdb2d865cf3" />


### ✨ Principais Funcionalidades

- Consumo de Eventos com RabbitMQ: Processamento assíncrono e resiliente de criação de pedidos.
- Persistência em MongoDB: Armazenamento de pedidos com índices otimizados para consulta por cliente.
- API REST para Consulta: Endpoint GET para listar pedidos de um cliente com paginação e metadados.
- Agregação com MongoTemplate: Uso do Aggregation Framework para cálculos complexos diretamente no banco de dados.
- Mapeamento de DTOs e Entidades: Utilização de Records (OrderResponse) para expor dados de forma segura.

### ⚙️ Tecnologias e Dependências

A estrutura do projeto é baseada em Maven e utiliza as seguintes tecnologias principais, definidas no pom.xml:
- Linguagem: Java 21
- Framework Principal: Spring Boot 3.5.4
- spring-boot-starter-web: Para a construção da API REST.
- spring-boot-starter-data-mongodb: Para integração com o MongoDB.
- spring-boot-starter-amqp: Para integração com o RabbitMQ.
- Banco de Dados: MongoDB
- Mensageria: RabbitMQ
- Build Tool: Maven
- Containerização: Docker

### 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e executar o projeto localmente.

Pré-requisitos:
- JDK 21
- Apache Maven 3.8+
- Docker e Docker Compose

#### 1. Clone o Repositório
```
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```
#### 2. Inicie as Dependências com Docker Compose

docker-compose.yml:
```
services:
  mongodb:
    image: mongo:latest
    container_name: mongodb
    ports:
      - 27017:27017
    environment:
      - MONGO_INITDB_ROOT_USERNAME=admin
      - MONGO_INITDB_ROOT_PASSWORD=123

  rabbitmq:
    image: rabbitmq:3.13-management
    ports:
      - 15672:15672
      - 5672:5672
```

Para iniciar os contêineres, execute:

```
docker-compose up -d
```

#### 3. Verifique a Configuração da Aplicação

O arquivo src/main/resources/application.properties deve estar configurado para se conectar aos serviços do Docker. As configurações abaixo são as esperadas:

application.properties:
```
spring.application.name=orderms

# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.username=admin
spring.data.mongodb.password=123
spring.data.mongodb.database=ordermsdb
spring.data.mongodb.authentication_database=admin
spring.data.mongodb.auto-index-creation=true

# RabbitMQ Configuration (Default values work with the docker-compose)
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

#### 4. Compile e Execute o Projeto

Use o Maven para compilar e iniciar a aplicação Spring Boot.

```
mvn spring-boot:run
```

A aplicação estará disponível em http://localhost:8080.

#### 5. (Opcional) Acessando o Painel do RabbitMQ

O docker-compose expõe a interface de gerenciamento do RabbitMQ. Você pode acessá-la para visualizar filas, exchanges e publicar mensagens de teste.

- URL: http://localhost:15672
- Usuário: guest
- Senha: guest

### 📚 Endpoints da API

Listar Pedidos de um Cliente
Retorna uma lista paginada de pedidos e o valor total gasto por esse cliente.
- Método: GET
- URL: /customers/{customerId}/orders
- Parâmetros:
   - page (Opcional, default: 0)
   - size (Opcional, default: 10)

 Exemplo de retorno esperado:
 ```
   {
       "codigoPedido": 1001,
       "codigoCliente":1,
       "itens": [
           {
               "produto": "lápis",
               "quantidade": 100,
               "preco": 1.10
           },
           {
               "produto": "caderno",
               "quantidade": 10,
               "preco": 1.00
           }
       ]
   }
```
