# ☁️ iBike - Cloud & DevOps (Sprint 4)

## 🚀 Visão Geral
  
Este repositório contém a **configuração de Cloud e DevOps** da aplicação **iBike**, plataforma inteligente para monitoramento de pátios de motocicletas.  
O foco deste projeto é aplicar **integração contínua (CI)** e **entrega contínua (CD)** com **Azure DevOps**, automatizando todo o fluxo de build, teste, containerização e deploy em ambiente cloud.

---

## 👥 Integrantes

| Nome | RM |
|------|----|
| **Gabriel Dias Menezes** | 555019 |
| **Júlia Soares Farias dos Santos** | 554609 |
| **Sofia Domingues Gonçalves** | 554920 |

---

## 📘 Sumário

1. [Resumo](#resumo)  
2. [Arquitetura da Solução](#arquitetura-da-solução)  
3. [Pipelines CI/CD](#pipelines-cicd)  
4. [Persistência de Dados](#persistência-de-dados)  
5. [Como Executar](#como-executar)  
6. [Vídeo da Demonstração](#vídeo-da-demonstração)  

---

## 🧩 Resumo

A aplicação **iBike Cloud** foi configurada no **Azure DevOps** para automatizar todo o ciclo de desenvolvimento, desde o build do backend até o deploy da imagem Docker em um **Azure Container Instance (ACI)**.

O projeto contempla:
- Integração com **GitHub** (trigger automática em `main`);
- Build Maven e geração de artefato `.jar`;
- Execução de testes automatizados;
- Criação e push de imagem Docker no **Azure Container Registry (ACR)**;
- Deploy automático no **Azure Container Instance (ACI)**;
- Variáveis de ambiente protegidas para conexão com o banco de dados.

---

## 🏗️ Arquitetura da Solução

A arquitetura é composta pelos seguintes componentes:

- **Azure DevOps Pipelines** → Responsável por CI/CD  
- **Azure Container Registry (ACR)** → Armazena as imagens Docker  
- **Azure Container Instance (ACI)** → Executa o container da aplicação  
- **PostgreSQL** → Banco de dados relacional  
- **GitHub Repository** → Armazena o código fonte e aciona o pipeline  

📊 **Fluxo Geral:**

```css
GitHub Push (branch main)
        ↓
Azure DevOps Pipeline (YAML)
        ↓
Build Maven + Testes
        ↓
Build Docker + Push no ACR
        ↓
Deploy automático no ACI
```

---

## 🔄 Pipelines CI/CD

O arquivo `azure-pipelines.yml` foi configurado em **YAML**, contendo três estágios principais:

### **1️⃣ CI - Build e Testes**
- Disparado automaticamente a cada `push` na branch `main`  
- Executa `mvn clean package -DskipTests` e testes unitários  
- Publica o artefato `.jar` no Azure DevOps  

### **2️⃣ CD - Build Docker**
- Constrói a imagem Docker usando o `scripts/build.sh`  
- Faz o push da imagem para o **Azure Container Registry (ACR)**  
- As credenciais do ACR estão configuradas via **Service Connection segura**  

### **3️⃣ Deploy no Azure**
- Executa o `scripts/deploy.sh`  
- Cria e atualiza o **Azure Container Instance**  
- Usa variáveis de ambiente seguras (`DB_USER`, `DB_PASSWORD`)  
- Exibe o IP público para acesso da aplicação  

---

## 🗄️ Persistência de Dados

A aplicação utiliza um **banco PostgreSQL hospedado no Azure**.  
As credenciais e configurações de conexão são armazenadas como **variáveis protegidas no Azure DevOps** para garantir segurança.

As entidades persistidas incluem:
- Administradores  
- Pátios  
- Motos  
- Eventos de monitoramento  

---

## ⚙️ Como Executar

### 🧰 Pré-requisitos
- Java 17  
- Maven  
- Docker Desktop  
- Acesso ao Azure DevOps  

### 🧩 Passos Locais

```bash
# Clonar o repositório
git clone https://github.com/jyx97/iBike_Cloud.git
cd iBike_Cloud

# Dar permissão e rodar scripts
dos2unix scripts/build.sh scripts/deploy.sh scripts/cleanUp.sh
chmod +x scripts/*.sh

# Build do container
./scripts/build.sh

# Deploy no Azure
./scripts/deploy.sh

# Após o deploy, a API estará disponível em:
http://<IP_PUBLICO>:8080/swagger-ui.html

# Para remover os recursos criados:
./scripts/cleanUp.sh
```
---

## 🎥 Vídeo da Demonstração

O vídeo mostra:

- Configuração do Azure DevOps

- Execução do pipeline (CI/CD)

- Publicação do artefato e imagem Docker

- Deploy no Azure Container Instance

- CRUD completo acessando o banco de dados

- 📹 [Link do vídeo YouTube](https://github.com/gabrieldiasmenezes/iBike_Cloud)

---

## ✅ Conclusão
O projeto cumpre todos os requisitos de DevOps da Sprint 4, incluindo:

- Integração com GitHub

- Pipeline automática (CI/CD)

- Geração e publicação de artefato

- Deploy automatizado em ACI com Docker

- Persistência de dados e variáveis seguras

