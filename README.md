# Customer Manager API

Microsserviço desenvolvido em **Java 21** e **Spring Boot** para o gerenciamento completo de clientes, contemplando persistência híbrida, segurança baseada em perfis e integração resiliente com um serviço externo de score de crédito.

---

## 🚀 Tecnologias Utilizadas

*   Java 21 (LTS)
*   Spring Boot 3
*   Spring Data JPA & JdbcTemplate (com Native Queries)
*   Spring Security (Basic Authentication)
*   H2 Database (Em memória)
*   Spring Validation & Bean Validation
*   Springdoc OpenAPI (Swagger UI)
*   JUnit 5, Mockito & WireMock (Testes Automatizados)
*   Maven

---

## 📋 Pré-requisitos

Para executar este projeto localmente, você precisará ter instalado:
*   **Java Development Kit (JDK) 21** ou superior configurado no PATH.
*   Maven (ou utilizar o Maven Wrapper `./mvnw` incluso no projeto).

---

## ⚙️ Configurações Necessárias (Variáveis de Ambiente)

A aplicação utiliza configurações externalizadas via `application.yml`. Para o correto funcionamento em ambiente de desenvolvimento, recomenda-se configurar as seguintes variáveis de ambiente na sua IDE ou terminal:

*   `DB_USER` (Padrão: `sa`)
*   `DB_PASS` (Padrão: `password`)
*   `SCORE_API_URL` (Padrão: `http://localhost:8081`)
*   `ADMIN_USER` / `ADMIN_PASS` (Credenciais com perfil de administrador)
*   `APP_USER` / `APP_PASS` (Credenciais com perfil de usuário comum)

---

## ▶️ Como Iniciar a Aplicação

1. Clone o repositório ou descompacte o projeto em sua máquina.
2. Abra o terminal na raiz do projeto.
3. Execute o comando do Maven Wrapper para compilar e iniciar:
    * No Linux/macOS: `./mvnw spring-boot:run`
    * No Windows: `mvnw.cmd spring-boot:run`

A aplicação estará rodando em `http://localhost:8080`.

---

## 🧪 Como Executar ou Simular o Serviço Externo (Score)

Como a aplicação realiza uma integração HTTP com um serviço externo de score, você pode simular esse serviço utilizando o **WireMock**:

1. Baixe o [WireMock Standalone JAR](https://wiremock.org/).
2. Inicie o WireMock na porta `8081` (conforme configurado na aplicação):
   ```bash
   java -jar wiremock-standalone-3.x.x.jar --port 8081