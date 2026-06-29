# Barber Schedule API 💈

API desenvolvida para o gerenciamento de agendamentos de barbearia

## Objetivo do Projeto
Este projeto foi desenvolvido como atividade avaliativa da disciplina de Frameworks de desenvolvimento backend,

### O que foi usado
* **Arquitetura Limpa (Clean Architecture):** Desacoplamento total entre lógica de negócio e frameworks.
* **Testes Automatizados:** testes unitários (Mockito) e de integração (usando o H2).
* **Spring Boot 3 + Java 21**
* **Docker**

## Funcionalidades
- Agendamento de horários com validação de conflitos.
- Listagem paginada de agendamentos por prestador.
- Cancelamento de agendamentos (Idempotente).
- Finalização de agendamentos.
- Tratamento centralizado de exceções (GlobalExceptionHandler).

## Arquitetura
O projeto segue a **Clean Architecture**, dividindo a aplicação em camadas bem definidas:
1. **Core (Domain):** Regras de negócio puras, sem dependências de frameworks.
2. **Application:** Casos de uso que orquestram o fluxo entre domínio e portas.
3. **Infrastructure:** Onde o Spring, JPA e outros frameworks ganham vida, implementando as interfaces (portas) definidas na aplicação.

> **Por que?** Esta separação garante que a lógica de negócio sobreviva a mudanças tecnológicas (ex: trocar de banco de dados ou framework) sem necessidade de reescrever as regras centrais.

## como executar

### Requisitos
- Docker.
- Java 21+ instalado.
- Maven 3.9+.

### Executando a Aplicação
Para rodar a API junto com o banco de dados PostgreSQL:
```bash
docker compose up -d --build
```
A API estará disponível em: http://localhost:8080/swagger-ui/index.html

#### Executando os Testes
Para garantir a integridade do código, rode os testes automatizados em ambiente de memória (H2):
```bash
mvn clean test
```

### Rotas Disponíveis
- POST/api/v1/agendamentos
- GET/api/v1/agendamentos/prestadores/{id}
- PATCH/api/v1/agendamentos/{id}/
- PATCH/api/v1/agendamentos/{id}/