# Controle de Ponto/Escala — Backend

API REST para um sistema de controle de ponto e escala com fluxo de aprovação, desenvolvida em Java + Spring Boot.

> Frontend deste projeto: [controle-ponto-frontend](https://github.com/Felipebuzo/controle-ponto-frontend)

## Sobre o projeto

Sistema onde funcionários registram batidas de ponto (entrada, saída para almoço, volta do almoço, saída) e podem solicitar ajustes quando esquecem de bater o ponto. Gestores visualizam e aprovam ou rejeitam essas solicitações — quando aprovada, o sistema atualiza automaticamente o registro de ponto do funcionário.

## Funcionalidades

- Autenticação via JWT, com senhas criptografadas (BCrypt)
- Autorização por papel: rotas exclusivas para `GESTOR` (ex: listar e decidir solicitações pendentes)
- Funcionário: bater ponto, consultar histórico próprio, solicitar ajuste de ponto
- Gestor: listar solicitações pendentes, aprovar ou rejeitar — aprovação gera automaticamente um novo registro de ponto

## Tecnologias

- Java 17
- Spring Boot
- Spring Security + JWT (biblioteca `jjwt`)
- Spring Data JPA / Hibernate
- MySQL
- Maven

## Arquitetura

O projeto segue uma separação em camadas:

- `model` — entidades JPA (`Usuario`, `RegistroPonto`, `SolicitacaoAjuste`)
- `repository` — interfaces `JpaRepository` para acesso ao banco
- `controller` — endpoints REST
- `dto` — objetos de transferência de dados entre API e cliente
- `security` — geração/validação de JWT e filtro de autenticação
- `config` — configuração do Spring Security (rotas públicas, protegidas por papel, CORS)

## Como rodar o projeto

### Pré-requisitos
- Java 17+
- MySQL rodando localmente
- Maven (ou use o `mvnw` incluso no projeto)

### Passos

1. Clone o repositório
2. Crie um banco de dados MySQL chamado `controle_ponto`
3. Crie um arquivo `.env` na raiz do projeto com:

DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=uma_chave_secreta_longa



4. Rode a aplicação:
./mvnw spring-boot:run

5. A API estará disponível em `http://localhost:8080`

## Principais endpoints

| Método | Rota | Descrição | Acesso |
|---|---|---|---|
| POST | `/auth/login` | Login, retorna token JWT | Público |
| POST | `/ponto` | Registra uma batida de ponto | Autenticado |
| GET | `/ponto/meu-historico` | Histórico de batidas do usuário logado | Autenticado |
| POST | `/solicitacoes` | Abre uma solicitação de ajuste | Autenticado |
| GET | `/solicitacoes/pendentes` | Lista solicitações pendentes | Gestor |
| PATCH | `/solicitacoes/{id}/aprovar` | Aprova solicitação e cria registro de ponto | Gestor |
| PATCH | `/solicitacoes/{id}/rejeitar` | Rejeita solicitação | Gestor |

## Autor

Felipe Buzo — [GitHub](https://github.com/Felipebuzo)