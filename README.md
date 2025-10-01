# iBike Backend


**Backend da plataforma iBike — monitoramento inteligente de pátios de motos**

## Integrantes

| Nomes | RM                  |  
| ----------- | -------------------------- | 
| `Gabriel Dias Menezes`      | `555019`       
| `Júlia Soares Farias Dos Santos`  | `554609`       |
| `Sofia Domingues Gonçalves`  | `554920`       |

---

## 🛠️ Sobre o Projeto

O **iBike Backend** é a API responsável pela gestão da plataforma iBike, que monitora pátios de motocicletas conectando dados de motos, funcionários (administradores), pátios e eventos de monitoramento.

A plataforma facilita o controle do status das motos e da ocupação dos pátios, com eventos detalhados de entrada e saída. Os dados de monitoramento são simulados via integração com o MockAPI da Mottu, replicando dados externos reais.

---

## 🎯 Funcionalidades

* **Gerenciamento de Administradores**
  Funcionários que gerenciam os pátios, com autenticação e autorização para acesso e modificação apenas dos seus próprios dados.

* **Controle de Motos**
  Cadastro, atualização e visualização das motos, filtrando por status e local de pátio.

* **Gestão de Pátios**
  Monitoramento da capacidade e status (disponível, cheio, sobrecarregado) dos pátios.

* **Eventos de Monitoramento**
  Registro dos eventos de entrada e saída das motos, permitindo rastreamento do fluxo.

* **Segurança JWT**
  Acesso seguro usando tokens JWT para autenticação e controle de permissão.

* **Cache para Performance**
  Cache de dados frequentes para otimizar consultas.

---

## 📁 Estrutura do Projeto

```
src/
└── main/
    ├── java/br/com/fiap/ibike/
    │   ├── config/           # Configurações gerais e Swagger
    │   ├── controller/       # REST Controllers
    │   ├── model/            # Entidades JPA, enums
    │   ├── repository/       # Spring Data JPA Repositories
    │   ├── security/         # Configurações de segurança e JWT
    │   └── service/          # Serviços e regras de negócio
    └── resources/
        ├── application.properties # Configurações do projeto
        └── data.sql                # Dados iniciais para banco em memória
```

---

## ⚙️ Tecnologias Utilizadas

| Tecnologia            | Finalidade                                   |
| --------------------- | -------------------------------------------- |
| Java 17               | Linguagem principal                          |
| Spring Boot           | Framework para API REST                      |
| Spring Data JPA       | Acesso ao banco com JPA                      |
| Spring Security (JWT) | Autenticação e autorização                   |
| H2 Database           | Banco em memória para desenvolvimento        |
| Lombok                | Redução de código boilerplate                |
| Swagger/OpenAPI       | Documentação interativa da API               |
| MockAPI (Mottu)       | Simulação de dados externos de monitoramento |

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

* Java 17 instalado
* Maven instalado (ou wrapper Maven incluso)
* (Opcional) Docker para rodar banco externo

### Rodando localmente com Maven

1. Clone o repositório:

```bash
git clone https://github.com/jyx97/iBike_Cloud.git
cd iBike_Cloud
```
2. Abra o projeto no VsCode:
```bash
code iBike_Cloud
```
3. Para rodar os arquivos scripts execute um de cada vez:

```bash
dos2unix scripts/build.sh scripts/deploy.sh scripts/cleanUp.sh
```

```bash
chmod +x scripts/build.sh scripts/deploy.sh scripts/cleanUp.sh 
```

```bash
 ./scripts/build.sh
```

```bash
 ./scripts/deploy.sh
```

4. Depois do ultimo codigo ele te mostrará o id e com ele poderá testar a api rodando:

```bash
<IP_GERADO>:8080/
```

5. Caso queira excluir o grupo d recurso
```bash
./scripts/cleanUp.sh
```

6. Para acessar a documentação Swagger (UI interativa):

```
http://<IP_PUBLICO>:8080/swagger-ui.html
```

---

## 📋 Exemplos de Endpoints

| Método | Endpoint              | Descrição                                  |
| ------ | --------------------- | ------------------------------------------ |
| POST   | `/administrador`      | Cadastro de administrador                  |
| PUT    | `/administrador/{id}` | Atualizar dados do administrador (próprio) |
| DELETE | `/administrador/{id}` | Deletar conta do administrador (próprio)   |
| GET    | `/patio`              | Listar todos os pátios                     |
| POST   | `/patio`              | Criar novo pátio                           |
| GET    | `/patio/{id}`         | Buscar pátio por ID                        |
| GET    | `/moto`               | Listar motos com filtros                   |
| GET    | `/monitoramento`      | Listar eventos de monitoramento            |

---

## 🔒 Segurança

* **JWT** para autenticação.
* Usuários só podem acessar e alterar seus próprios dados.
* Spring Security protege todos endpoints sensíveis.
* Configuração de roles e permissões.

---

## 📈 Melhorias Futuras

* Dashboard web para visualização em tempo real.
* Alertas e notificações automáticas para eventos críticos.
* API pública para acesso externo controlado.
* Implementação de testes automatizados (unitários e integração).


