## API PARK 2025 — Estacionamento

- Controle de entrada e saída de veículos com cálculo de cobranças e taxas.
- Autenticação via JWT, perfis `ADMIN` e `CLIENTE`, documentação OpenAPI.

### Tecnologias
- Java 17, Spring Boot 3.5
- Spring Security, JPA/Hibernate
- PostgreSQL/MySQL (produção), H2 (testes)
- Maven (com `mvnw`), Docker
- Springdoc OpenAPI/Swagger

### Pré‑requisitos
- `Java 17` instalado
- Banco de dados disponível (PostgreSQL recomendado)
- Variáveis de ambiente para o banco configuradas:
  - `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` conforme `src/main/resources/application.properties:13`
  - Exemplo (PowerShell):
    - `$env:DB_URL="jdbc:postgresql://localhost:5432/demo_park"`
    - `$env:DB_USERNAME="postgres"`
    - `$env:DB_PASSWORD="postgres"`

### Configuração
- Por padrão usa PostgreSQL via `application.properties` (`spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect`).
- Para MySQL, ajuste propriedades de datasource e dialeto e forneça credenciais válidas.
- Endpoints públicos e protegidos são definidos em `src/main/java/com/apirest/demo_park_api/config/SpringSecurityConfig.java:22`.

### Execução local
- Windows: `.\mvnw.cmd spring-boot:run`
- Linux/macOS: `./mvnw spring-boot:run`
- Maven global: `mvn spring-boot:run`
- Aplicação sobe em `http://localhost:8080`.

### Testes
- `.\mvnw.cmd test`
- Testes de integração utilizam H2 e `WebTestClient` sob `src/test/java`.

### Documentação
- Swagger UI local: `http://localhost:8080/docs-demo-park-api.html` (configurado em `src/main/resources/application.properties:29`)
- OpenAPI JSON: `http://localhost:8080/docs-demo-park-api`
- Deploy (Render): `https://deploy-api-park.onrender.com/swagger-ui/index.html#/`

### Fluxo de autenticação
- Criar usuário: `POST /api/v1/usuarios` (`src/main/java/.../UsuarioController.java:51`)
- Autenticar: `POST /api/v1/auth` com body `{"username":"email","password":"senha"}` (`src/main/java/.../AutenticacaoController.java:50`)
- Header para chamadas autenticadas: `Authorization: Bearer <token>`
- Healthcheck público: `GET /api/v1/ping` (`src/main/java/.../AutenticacaoController.java:74`)

### Endpoints principais
- Usuários
  - `POST /api/v1/usuarios` (criar)
  - `GET /api/v1/usuarios/{id}` (ADMIN ou dono) (`src/main/java/.../UsuarioController.java:65`)
  - `PATCH /api/v1/usuarios/{id}` (alterar senha, dono) (`src/main/java/.../UsuarioController.java:95`)
  - `GET /api/v1/usuarios` (listar, ADMIN) (`src/main/java/.../UsuarioController.java:114`)
- Clientes
  - `POST /api/v1/clientes` (CLIENTE) (`src/main/java/.../ClienteController.java:61`)
  - `GET /api/v1/clientes/{id}` (ADMIN) (`src/main/java/.../ClienteController.java:82`)
  - `GET /api/v1/clientes/detalhes` (CLIENTE) (`src/main/java/.../ClienteController.java:112`)
- Vagas
  - `POST /api/v1/vagas` (ADMIN) (`src/main/java/.../VagaController.java:41`)
- Estacionamentos
  - `POST /api/v1/estacionamentos/check-in` (ADMIN) (`src/main/java/.../EstacionamentoController.java:71`)
  - `GET /api/v1/estacionamentos/check-in/{recibo}` (ADMIN/CLIENTE) (`src/main/java/.../EstacionamentoController.java:91`)
  - `PUT /api/v1/estacionamentos/check-out/{recibo}` (ADMIN) (`src/main/java/.../EstacionamentoController.java:110`)
  - `GET /api/v1/estacionamentos/cpf/{cpf}` (ADMIN, paginação) (`src/main/java/.../EstacionamentoController.java:132`)
  - `GET /api/v1/estacionamentos` (CLIENTE, paginação) (`src/main/java/.../EstacionamentoController.java:141`)
  - `GET /api/v1/estacionamentos/relatorio` (CLIENTE, PDF) (`src/main/java/.../EstacionamentoController.java:151`)

### Docker
- Build: `docker build -t demo-park-api .`
- Run: `docker run -p 8080:8080 -e DB_URL="jdbc:postgresql://host:5432/demo_park" -e DB_USERNAME="user" -e DB_PASSWORD="pass" demo-park-api`
- O container lê as variáveis `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` em tempo de execução.

### Banco de dados
- Scripts de criação/carga (MySQL) em `SQL/`.
- Para testes, há scripts em `src/test/resources/sql/` e configuração H2 em `src/test/resources/application.properties:1`.

### Observações de segurança
- O JWT é emitido e validado pelo utilitário em `src/main/java/com/apirest/demo_park_api/jwt/JwtUtils.java:41`. Altere a chave de assinatura antes de produção.

### Galeria
| Item | Preview |
| --- | --- |
| Diagrama BD | ![Diagrama](https://github.com/acrisiopb/API_PARK_2025/blob/main/IMG%20GIT/Diagrama%20-%20BD.png?raw=true) |
| Postman Report 1 | ![Postman Report 1](https://github.com/acrisiopb/API_PARK_2025/blob/main/IMG%20GIT/REPORT%202.png?raw=true) |
| Postman Report 2 | ![Postman Report 2](https://github.com/acrisiopb/API_PARK_2025/blob/main/IMG%20GIT/Report.png?raw=true) |
| Swagger UI | ![Swagger UI](https://github.com/acrisiopb/API_PARK_2025/blob/main/IMG%20GIT/Swagger.png?raw=true) |



