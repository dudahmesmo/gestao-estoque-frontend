# 💻 STOCKTOOL - FRONTEND (CLIENTE JAVA SWING)

Interface Gráfica desenvolvida em Java Swing, responsável pela interação com o usuário e pela comunicação HTTP com o Backend (API RESTful).

---

## 👥 Integrantes

| Nome | RA | GitHub |
| :--- | :--- | :--- |
| KAUE SANTANA DE OLIVEIRA | 10725116177 | @kaue-santana |
| MARIA EDUARDA SOUZA DOS SANTOS FERREIRA | 10724111943 | @dudahmesmo |
| MILLENA FERREIRA RODRIGUES | 10724112348 | @Miaunisul |


---

## 🛠️ Tecnologias e Comunicação

* **Linguagem & Plataforma:** Java Development Kit (JDK) 21
* **Interface Gráfica:** Java Swing
* **Comunicação:** Cliente HTTP
* **Serialização:** Biblioteca Gson (para DTOs JSON)
* **Gerenciador de Dependências:** Apache Maven
* **IDE de Desenvolvimento:** Apache NetBeans IDE 21

A comunicação com o Backend é realizada através de uma camada de cliente HTTP (e.g., `ApiClient.java`), que traduz as ações do usuário em requisições REST (GET, POST, etc.) na porta `8080`.

---

## 🚀 Guia de Configuração e Execução

Para iniciar a Interface Gráfica do StockTool, siga estas etapas:

### 1. Pré-requisitos

* Java JDK 21+
* Apache Maven
* **PRIMEIRO PASSO CRUCIAL:** O **Backend (API RESTful)** deve estar rodando e acessível em `http://localhost:8080` antes de iniciar o Frontend.

### 2. Clonagem do Repositório

Clone o repositório do Frontend e navegue até a pasta:

```bash
git clone [https://github.com/dudahmesmo/gestao-estoque-frontend.git](https://github.com/dudahmesmo/gestao-estoque-frontend.git)
cd gestao-estoque-frontend
3. Execução da Aplicação (Entry Point)
O ponto de entrada da aplicação é a classe ProjetoA3SQL.java. Recomenda-se o uso do VSCode para a execução:

Bash

# 1. Abra o projeto no VSCode.
# 2. Clique com o botão direito na classe 'ProjetoA3SQL.java' e selecione 'Run File'.
Se a conexão falhar, verifique se a API do Backend está de pé na porta 8080.
```
### 4. Telas e Funcionalidades Principais
A interface permite:

- Gestão de Estoque: Cadastro de Ferramentas com parâmetros de estoque (Mín/Máx) e Categoria.

- Controle de Empréstimos/Devoluções: Interface para registro das transações.

- Relatórios Gerenciais: Visualização de alertas de estoque baixo e relatórios de devedores.

### Requisitos Não Funcionais (RNFs) 🔒
- RNF01: O sistema deve rodar localmente como um cliente desktop (Java Swing).

- RNF03: Interface gráfica intuitiva e fácil de usar.

- RNF08: Trata erros de comunicação HTTP com o Backend, exibindo mensagens claras ao usuário.

### Repositórios 🔗
Repositório do Backend (API): gestao-estoque-backend (https://github.com/dudahmesmo/gestao-estoque-backend)

### Licença: Este projeto está licenciado sob a [Licença](https://github.com/dudahmesmo/gestao-estoque-frontend/blob/main/LICENSE) MIT.
