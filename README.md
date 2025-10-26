# Sistema de Gestão de Estoque (Front-end)

Este repositório contém o código-fonte da camada de **cliente (front-end)** do projeto da A3 da Unidade Curricular de Sistemas Distribuídos e Mobile.

---

### Integrantes:

* Maria Eduarda Eduarda Souza dos Santos Ferreira
* Millena Ferreira Rodrigues

---

### Professor:

* Jorge Werner

---

### 📌 Arquitetura e Tecnologias

* **Linguagem:** Java
* **Interface Gráfica:** Java Swing
* **Arquitetura:** Aplicação cliente que consome uma API RESTful.
* **Comunicação:** Cliente HTTP

---

### Descrição do Projeto

Este projeto é a interface gráfica (GUI) com a qual o usuário interage. Ele é responsável por exibir os dados, coletar as informações do usuário e se comunicar com a API RESTful do [gestao-estoque-backend](https://github.com/seu-usuario/gestao-estoque-backend) (link para o outro repositório).

**Importante:** Esta aplicação não possui acesso direto ao banco de dados. Toda a persistência de dados, regras de negócio e acesso ao banco são delegados ao serviço de back-end.

Este código é uma adaptação do projeto A3 de Programação de Soluções Computacionais, refatorado para funcionar em uma arquitetura distribuída.

### Funcionalidades Principais

Este cliente é responsável por consumir e apresentar as seguintes funcionalidades da API:

1.  **Cadastro de Produtos**:
    * Nome, Preço, Unidade, Estoque, etc.
2.  **Cadastro de Categorias**:
    * Nome, Tamanho, Marca.
3.  **Movimentação de Estoque:**
    * Registrar Entrada de produtos.
    * Registrar Saída de produtos.
4.  **Relatórios**:
    * Consumir e exibir os 5 relatórios gerados pelo back-end (Lista de Preços, Balanço, Estoque Mínimo, etc.).

### 📍 Instruções de Instalação e Uso

Este projeto **não funciona sozinho**. Ele é a camada de cliente e depende 100% do back-end estar a funcionar.

1.  **Execute o Back-end:** Primeiro, certifique-se de que o projeto `gestao-estoque-backend` está em execução.
2.  **Abra o Projeto:** Abra esta pasta (`gestao-estoque-frontend`) numa IDE que tenha suporte a Java Swing (recomenda-se o NetBeans para a gestão visual).
3.  **Execute o Projeto:** Encontre a classe principal da aplicação (o arquivo .java que contém o método main, geralmente a tela de menu ou login) e execute-o.
4.  **Use o Sistema:** A aplicação irá abrir e estará pronta para se comunicar com o back-end.

### Licença

Este projeto está licenciado sob a licença MIT.
