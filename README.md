# Java Web REST API - Clean Architecture

Este projeto demonstra a implementação de uma API REST em Java seguindo os princípios de **Clean Architecture** e **Clean Code**.

## Arquitetura

O projeto está estruturado em camadas bem definidas:

### 🏗️ Camadas da Aplicação

1. **Borders (Fronteiras)**
   - `entities/` - Entidades de domínio com regras de negócio
   - `interfaces/` - Contratos/interfaces dos repositórios e use cases
   - `dtos/` - Objetos de transferência de dados
   - `validators/` - Validadores customizados de negócio

2. **UseCases (Casos de Uso)**
   - `usecases/` - Lógica de negócio e orquestração

3. **Repositories (Repositórios)**
   - `repositories/` - Implementações de persistência de dados

4. **API (Interface Externa)**
   - `api/controllers/` - Controllers REST
   - `config/` - Configurações da aplicação

5. **Tests (Testes)**
   - Testes unitários

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (in-memory para desenvolvimento)
- **Bean Validation**
- **OpenAPI/Swagger** (documentação da API)
- **JUnit 5** (testes)
- **Mockito** (mocks para testes)
- **Maven** (gerenciamento de dependências)

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/example/cleanapi/
│   │   ├── CleanApiApplication.java
│   │   ├── api/controllers/          # Camada de API
│   │   ├── borders/                  # Camada de Fronteiras
│   │   │   ├── entities/
│   │   │   ├── interfaces/
│   │   │   ├── dtos/
│   │   │   └── validators/
│   │   ├── usecases/                 # Camada de Casos de Uso
│   │   ├── repositories/             # Camada de Repositórios
│   │   └── config/                   # Configurações
│   └── resources/
│       └── application.properties
└── test/                             # Camada de Testes
```

## 🏃‍♂️ Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+

### Executando a aplicação

1. Clone o repositório
2. Navegue até o diretório do projeto
3. Execute o comando:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

### Executando os testes

```bash
mvn test
```

## 📖 Documentação da API

Após iniciar a aplicação, a documentação da API estará disponível em:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## 🎯 Endpoints Principais

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/users` | Criar novo usuário |
| GET | `/api/v1/users` | Listar todos os usuários |
| GET | `/api/v1/users/{id}` | Buscar usuário por ID |
| PUT | `/api/v1/users/{id}` | Atualizar usuário |
| DELETE | `/api/v1/users/{id}` | Deletar usuário |
| GET | `/api/v1/users/health` | Health check |

## 💡 Exemplo de Uso

### Criar um usuário
```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao.silva@example.com"
  }'
```

### Buscar usuário por ID
```bash
curl http://localhost:8080/api/v1/users/1
```

## 🗄️ Banco de Dados

O projeto utiliza H2 Database em memória para desenvolvimento. 

**Console H2**: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (vazio)

## 🏛️ Princípios de Clean Architecture

Este projeto segue os princípios fundamentais da Clean Architecture:

1. **Independência de Frameworks**: A lógica de negócio não depende de frameworks específicos
2. **Testabilidade**: Todas as camadas são testáveis de forma isolada
3. **Independência da UI**: A interface pode ser alterada sem afetar o negócio
4. **Independência do Banco de Dados**: O tipo de banco pode ser alterado facilmente
5. **Regra de Dependência**: Dependências apontam sempre para dentro (domínio)

## 🧪 Testes

O projeto possui cobertura de testes em todas as camadas:

- **Testes de Unidade**: Para entidades, validadores e use cases
- **Testes de Integração**: Para controllers e fluxos completos
- **Mocks**: Utilizando Mockito para isolar dependências

## 🔧 Configurações

As configurações da aplicação estão no arquivo `application.properties`:

- Porta da aplicação: 8080
- Banco H2 em memória
- Logs de SQL habilitados (desenvolvimento)
- Console H2 habilitado

## 🌐 Deploy e URL da API

### 🚂 Railway 
1. Acesse [railway.app](https://railway.app)
2. Faça login com GitHub
3. **New Project** → **Deploy from GitHub repo**
4. Selecione este repositório
5. Aguarde o deploy automático (5-10 minutos)
6. **URL será**: `https://java-web-rest-api-production.railway.app`

**Configuração automática**: O arquivo `railway.json`

### 📋 URLs Importantes Após Deploy
Substitua `{URL_DA_APLICACAO}` pela URL da sua plataforma:

- **🏠 API Base**: `{URL_DA_APLICACAO}/`
- **📚 Swagger UI**: `{URL_DA_APLICACAO}/swagger-ui.html`
- **💚 Health Check**: `{URL_DA_APLICACAO}/api/v1/users/health`
- **📖 OpenAPI Docs**: `{URL_DA_APLICACAO}/api-docs`

### 🧪 Testando a API Online
```bash
# Health check
curl https://sua-app.railway.app/api/v1/users/health

# Criar usuário
curl -X POST https://sua-app.railway.app/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"name": "João Silva", "email": "joao@example.com"}'

# Listar usuários
curl https://sua-app.railway.app/api/v1/users
```

### ⚡ Deploy Automático
Toda vez que você fizer `git push origin main`, a aplicação será automaticamente atualizada na plataforma!

## 📚 Benefícios desta Arquitetura

1. **Manutenibilidade**: Código organizado e fácil de manter
2. **Testabilidade**: Cada camada pode ser testada isoladamente
3. **Flexibilidade**: Fácil troca de componentes (banco, framework, etc.)
4. **Escalabilidade**: Estrutura preparada para crescimento
5. **Reutilização**: Lógica de negócio independente de infraestrutura

## 🎓 Aprendizados

Este projeto demonstra como aplicar conceitos de:
- Clean Architecture
- SOLID Principles
- Dependency Injection
- Repository Pattern
- DTO Pattern
- Validation
- Exception Handling
- API Documentation
- Testing Strategies

---
## 📋 Tecnologias Utilizadas — Explicação

### ☕ Java 17
- O que é: Versão LTS do Java (lançada em 2021).  
- Por que usar: Melhor desempenho, recursos modernos (records, pattern matching, text blocks) e suporte a longo prazo.  
- No projeto: Linguagem base, aproveitando recursos modernos e segurança de tipo.

### 🌱 Spring Boot 3.2.0
- O que é: Framework que simplifica a criação de aplicações Java enterprise.  
- Por que usar: Autoconfiguração, servidor embarcado, starters e prontidão para produção.  
- No projeto: Base da API REST, gerencia injeção de dependência e configuração.

### 🗃️ Spring Data JPA
- O que é: Abstração sobre JPA para acesso a dados.  
- Por que usar: Reduz boilerplate, fornece repositórios prontos e queries automáticas.  
- No projeto: Camada de persistência com operações CRUD automáticas.

### 💾 H2 Database
- O que é: Banco de dados em memória, leve e rápido.  
- Por que usar: Ideal para desenvolvimento e testes, sem instalação e com console web.  
- No projeto: Armazenamento temporário para desenvolvimento e testes automatizados.

### ✅ Bean Validation
- O que é: Especificação Java para validação declarativa via anotações.  
- Por que usar: Validações como @NotNull e @Email integradas ao fluxo do Spring.  
- No projeto: Validação de DTOs de entrada para garantir integridade dos dados.

### 📚 OpenAPI / Swagger
- O que é: Especificação para documentação de APIs REST.  
- Por que usar: Documentação interativa automática e padronizada.  
- No projeto: Gera interface em /swagger-ui.html e JSON em /api-docs.

### 🧪 JUnit 5
- O que é: Framework moderno para testes unitários em Java.  
- Por que usar: Assertions avançadas, extensibilidade e integração com IDEs.  
- No projeto: Execução de testes unitários e de integração.

### 🎭 Mockito
- O que é: Framework para criação de mocks em testes.  
- Por que usar: Isola dependências e permite testar unidades isoladas.  
- No projeto: Mock de repositórios e dependências nos testes unitários.

### 📦 Maven
- O que é: Ferramenta de build e gerenciamento de dependências.  
- Por que usar: Gerencia dependências, build e ciclo de vida do projeto.  
- No projeto: Compila, testa e empacota a aplicação.

### 🔗 Como elas trabalham juntas
Maven → Java 17 → Spring Boot → Spring Data JPA → H2 Database → Bean Validation → OpenAPI/Swagger → JUnit 5 + Mockito

(Fluxo: gerenciamento de dependências → linguagem → framework → persistência → banco em memória → validação → documentação → testes)

### 🎯 Benefícios da stack
- Produtividade: configuração mínima e recursos prontos.  
- Qualidade: validações e testes automatizados.  
- Manutenibilidade: código padronizado e modular.  
- Desenvolvimento rápido: hot reload e banco em memória.  
- Pronto para produção: suporte a métricas, health checks e logging.

---

**Desenvolvido por Thales Allan Dev, usando Clean Architecture em Java**
