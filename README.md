# Lab Padrões de Projeto — Spring Boot

Projeto desenvolvido para praticar **Spring Boot, JPA, arquitetura em camadas e padrões de projeto**, aplicando conceitos de orientação a objetos e princípios SOLID em uma API REST de produtos.

## Tecnologias

* Java 21
* Spring Boot 4.1.1
* Spring Web
* Spring Data JPA
* Hibernate
* H2 Database
* Maven
* JUnit 5
* SpringDoc OpenAPI

## Objetivo

A aplicação disponibiliza uma API REST para gerenciamento de produtos, permitindo:

* cadastrar produtos;
* consultar todos os produtos;
* consultar um produto por ID;
* excluir produtos;
* calcular descontos de acordo com o tipo configurado.

O projeto também foi estruturado para demonstrar padrões de projeto como **Strategy, Factory e Facade**, além de conceitos do Spring como **Inversão de Controle (IoC)** e **Injeção de Dependência (DI)**.

## Arquitetura

O fluxo principal da aplicação é:

```text
Cliente HTTP
     ↓
ProductController
     ↓
ProductFacade
     ↓
ProductService
     ↓
ProductRepository
     ↓
JPA / Hibernate
     ↓
H2 Database
```

Para o cálculo de desconto:

```text
ProductFacade
     ↓
DiscountStrategyFactory
     ↓
DiscountStrategy
     ├── PercentageDiscountStrategy
     ├── FixedDiscountStrategy
     └── NoDiscountStrategy
```

### Responsabilidade das principais camadas

| Componente                   | Responsabilidade                              |
| ---------------------------- | --------------------------------------------- |
| `ProductController`          | Receber requisições HTTP e retornar respostas |
| `ProductFacade`              | Simplificar e coordenar o fluxo da operação   |
| `ProductService`             | Executar operações relacionadas aos produtos  |
| `ProductRepository`          | Acessar os dados através do Spring Data JPA   |
| `Product`                    | Representar a entidade persistida             |
| `ProductRequest`             | Representar os dados recebidos pela API       |
| `ProductResponse`            | Representar os dados retornados pela API      |
| `DiscountStrategy`           | Definir o contrato para cálculo de desconto   |
| `PercentageDiscountStrategy` | Aplicar desconto percentual                   |
| `FixedDiscountStrategy`      | Aplicar desconto fixo                         |
| `NoDiscountStrategy`         | Não aplicar desconto                          |
| `DiscountStrategyFactory`    | Selecionar a estratégia correspondente        |

## Padrões de Projeto

### Strategy

O padrão Strategy é utilizado para encapsular diferentes formas de calcular descontos.

A interface define o contrato:

```java
public interface DiscountStrategy {
    BigDecimal calculateDiscount(BigDecimal price);
}
```

Cada implementação possui seu próprio algoritmo:

* `PercentageDiscountStrategy`
* `FixedDiscountStrategy`
* `NoDiscountStrategy`

Assim, o cálculo do desconto fica separado da lógica principal do produto.

### Factory

A `DiscountStrategyFactory` é responsável por selecionar qual estratégia deve ser utilizada:

```java
public DiscountStrategy getStrategy(DiscountType type) {
    return switch (type) {
        case PERCENTAGE -> percentageStrategy;
        case FIXED -> fixedStrategy;
        case NONE -> noDiscountStrategy;
    };
}
```

### Facade

A `ProductFacade` fornece uma interface simplificada para o Controller.

O Controller não precisa conhecer todos os componentes envolvidos na operação. Ele apenas solicita a operação à Facade:

```text
Controller
    ↓
Facade
    ├── Service
    └── Strategy Factory
```

## SOLID

O projeto utiliza os princípios SOLID principalmente da seguinte forma:

### SRP — Single Responsibility Principle

As responsabilidades são distribuídas entre diferentes classes:

* Controller → comunicação HTTP;
* Service → operações dos produtos;
* Repository → persistência;
* Strategy → cálculo específico de desconto;
* Factory → seleção da estratégia;
* Facade → coordenação do fluxo.

### OCP — Open/Closed Principle

Novos algoritmos de desconto podem ser adicionados criando uma nova implementação de `DiscountStrategy`, sem alterar as estratégias existentes.

Por exemplo:

```java
@Component
public class PromotionalDiscountStrategy implements DiscountStrategy {
    // novo algoritmo
}
```

> Observação: a Factory atual utiliza `switch`, portanto a inclusão de um novo `DiscountType` ainda exige alteração na Factory. O princípio é aplicado principalmente na separação dos algoritmos de desconto.

### LSP — Liskov Substitution Principle

As implementações concretas podem ser utilizadas através do tipo `DiscountStrategy`:

```java
DiscountStrategy strategy;
```

Qualquer implementação deve poder cumprir o contrato definido pela interface.

### ISP — Interface Segregation Principle

A interface `DiscountStrategy` possui apenas a operação necessária para seu contexto:

```java
BigDecimal calculateDiscount(BigDecimal price);
```

### DIP — Dependency Inversion Principle

As classes recebem suas dependências por meio do construtor, utilizando a injeção de dependência do Spring.

Exemplo:

```java
public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
}
```

A aplicação também utiliza abstrações como `DiscountStrategy` para trabalhar com diferentes implementações.

## API

### Criar produto

```http
POST /products
Content-Type: application/json
```

Exemplo:

```json
{
    "name": "Notebook",
    "price": 3000.00,
    "discountType": "PERCENTAGE"
}
```

Resposta esperada:

```json
{
    "id": 1,
    "name": "Notebook",
    "price": 3000.00,
    "discountType": "PERCENTAGE",
    "discount": 300.00,
    "finalPrice": 2700.00
}
```

### Listar produtos

```http
GET /products
```

### Buscar produto por ID

```http
GET /products/{id}
```

### Excluir produto

```http
DELETE /products/{id}
```

## Executando o projeto

### Pré-requisitos

* Java 21
* Maven

O projeto também possui o Maven Wrapper, portanto não é necessário instalar o Maven globalmente.

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

A aplicação será executada em:

```text
http://localhost:8080
```

## Executando os testes

Windows:

```bash
mvnw.cmd test
```

Linux/macOS:

```bash
./mvnw test
```

O projeto possui testes automatizados com JUnit 5 e Spring Boot Test.

## Banco de dados

O projeto utiliza o **H2 em memória**.

Configuração:

```properties
spring.datasource.url=jdbc:h2:mem:productsdb
spring.jpa.hibernate.ddl-auto=create-drop
```

Isso significa que o banco é criado quando a aplicação inicia e descartado quando ela é encerrada.

A aplicação também disponibiliza o console do H2 em:

```text
http://localhost:8080/h2-console
```

## CI — Integração Contínua

O projeto utiliza GitHub Actions para executar automaticamente os testes a cada alteração enviada ao repositório.

O objetivo é verificar automaticamente se o código continua compilando e se os testes estão passando antes de ser integrado ao projeto.

```text
Push / Pull Request
        ↓
GitHub Actions
        ↓
Configura Java 21
        ↓
Executa Maven
        ↓
mvn test
        ↓
Build aprovado ou falha
```

## Aprendizados

Este projeto foi desenvolvido para consolidar conhecimentos sobre:

* Java e POO;
* Spring Boot;
* Inversão de Controle;
* Injeção de Dependência;
* REST;
* JPA e Hibernate;
* Maven;
* arquitetura em camadas;
* Strategy;
* Factory;
* Facade;
* SOLID;
* testes automatizados;
* Integração Contínua com GitHub Actions.
