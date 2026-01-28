# 📚 Sistema de Gerenciamento de Biblioteca

[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)](https://github.com)
[![Java](https://img.shields.io/badge/Java-17+-orange)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 Sobre o Projeto

Sistema desktop desenvolvido em Java para gerenciamento completo de bibliotecas, permitindo o controle de acervo, cadastro de usuários e gerenciamento de empréstimos de livros.

Este projeto faz parte do **Projeto Integrador** do curso, aplicando conhecimentos de programação orientada a objetos, interface gráfica, banco de dados e versionamento de código.

---

## 🚀 Status do Projeto

**🔄 Em Desenvolvimento**

O projeto encontra-se em fase de desenvolvimento ativo, com implementação contínua de novas funcionalidades e melhorias.

### Versão Atual: 1.0.0

**Última Atualização:** Janeiro de 2026

---

## 💻 Tecnologias Aplicadas

Este projeto utiliza as seguintes tecnologias e ferramentas abordadas no curso:

### Linguagens e Frameworks
- **Java 17+** - Linguagem de programação principal
- **Java Swing** - Framework para interface gráfica (GUI)
- **JDBC** - Conectividade com banco de dados

### Banco de Dados
- **MySQL** / **PostgreSQL** - Sistema de gerenciamento de banco de dados relacional
- **SQL** - Linguagem de consulta estruturada

### Ferramentas de Desenvolvimento
- **NetBeans IDE** / **Eclipse** - Ambiente de desenvolvimento integrado
- **Git** - Sistema de controle de versão
- **GitHub** - Plataforma de hospedagem de código
- **GitHub Desktop** - Interface gráfica para Git

### Arquitetura e Padrões
- **MVC (Model-View-Controller)** - Padrão de arquitetura de software
- **DAO (Data Access Object)** - Padrão de acesso a dados
- **POO** - Programação Orientada a Objetos

---

## 🎯 Objetivo do Software

O **Sistema de Gerenciamento de Biblioteca** tem como objetivo principal:

- **Automatizar** o processo de controle de acervo bibliográfico
- **Facilitar** o cadastro e gerenciamento de usuários (leitores)
- **Agilizar** o processo de empréstimo e devolução de livros
- **Organizar** o acervo de forma eficiente e acessível
- **Gerar relatórios** sobre movimentação do acervo
- **Reduzir** erros manuais no controle de empréstimos
- **Melhorar** a experiência dos usuários da biblioteca

### Público-Alvo

- Bibliotecas escolares
- Bibliotecas comunitárias
- Pequenas bibliotecas institucionais
- Centros de leitura

---

## ⚙️ Funcionalidades do Sistema

### 📖 Módulo de Livros

**Requisitos Funcionais:**
- [x] **RF01** - Cadastrar novo livro no sistema
  - Título, autor, ISBN, editora, ano de publicação
  - Categoria/gênero, número de páginas
  - Quantidade de exemplares disponíveis
- [x] **RF02** - Consultar livros cadastrados
  - Busca por título, autor, ISBN ou categoria
  - Visualização de informações detalhadas
- [x] **RF03** - Atualizar informações de livros
  - Edição de dados cadastrais
  - Atualização de quantidade de exemplares
- [x] **RF04** - Excluir livro do acervo
  - Verificação de empréstimos ativos
  - Confirmação de exclusão
- [ ] **RF05** - Controlar status de disponibilidade
  - Disponível, emprestado, em manutenção

### 👤 Módulo de Usuários

**Requisitos Funcionais:**
- [x] **RF06** - Cadastrar novo usuário/leitor
  - Nome completo, CPF, e-mail, telefone
  - Endereço completo
  - Tipo de usuário (aluno, professor, funcionário)
- [x] **RF07** - Consultar usuários cadastrados
  - Busca por nome, CPF ou e-mail
  - Histórico de empréstimos
- [x] **RF08** - Atualizar dados de usuários
  - Edição de informações cadastrais
  - Atualização de contatos
- [x] **RF09** - Inativar usuário
  - Verificação de empréstimos pendentes
  - Manutenção de histórico

### 📚 Módulo de Empréstimos

**Requisitos Funcionais:**
- [x] **RF10** - Registrar novo empréstimo
  - Seleção de usuário e livro
  - Data de empréstimo e previsão de devolução
  - Verificação de disponibilidade
- [x] **RF11** - Registrar devolução de livro
  - Cálculo de multas por atraso
  - Atualização de status do livro
- [x] **RF12** - Consultar empréstimos ativos
  - Lista de empréstimos em aberto
  - Alertas de atraso
- [ ] **RF13** - Renovar empréstimo
  - Verificação de limite de renovações
  - Nova data de devolução
- [ ] **RF14** - Gerar multa por atraso
  - Cálculo automático baseado em dias de atraso
  - Registro de pagamento

### 📊 Módulo de Relatórios

**Requisitos Funcionais:**
- [ ] **RF15** - Relatório de livros mais emprestados
- [ ] **RF16** - Relatório de usuários com empréstimos ativos
- [ ] **RF17** - Relatório de empréstimos em atraso
- [ ] **RF18** - Relatório financeiro de multas

### 🔐 Módulo de Autenticação

**Requisitos Funcionais:**
- [x] **RF19** - Login de administrador/bibliotecário
- [x] **RF20** - Controle de permissões de acesso
- [ ] **RF21** - Recuperação de senha

---

## 📦 Estrutura do Projeto

```
sistema-biblioteca/
│
├── src/
│   ├── model/              # Classes de modelo (Livro, Usuario, Emprestimo)
│   ├── view/               # Interfaces gráficas (JFrames, JPanels)
│   ├── controller/         # Controladores (lógica de negócio)
│   ├── dao/                # Data Access Objects (acesso ao BD)
│   ├── util/               # Classes utilitárias
│   └── config/             # Configurações (conexão BD)
│
├── lib/                    # Bibliotecas externas (.jar)
├── database/               # Scripts SQL
│   ├── schema.sql          # Estrutura do banco de dados
│   └── data.sql            # Dados iniciais
│
├── docs/                   # Documentação
│   ├── diagramas/          # Diagramas UML
│   └── manual/             # Manual do usuário
│
├── test/                   # Testes unitários
├── .gitignore             # Arquivos ignorados pelo Git
├── README.md              # Este arquivo
└── LICENSE                # Licença do projeto
```

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java JDK 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **MySQL 8.0+** ou **PostgreSQL** - [MySQL](https://dev.mysql.com/downloads/) | [PostgreSQL](https://www.postgresql.org/download/)
- **IDE Java** (NetBeans, Eclipse ou IntelliJ)
- **Git** - [Download](https://git-scm.com/)

### Configuração do Banco de Dados

1. **Crie o banco de dados:**
```sql
CREATE DATABASE biblioteca_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. **Execute o script de criação das tabelas:**
```bash
mysql -u root -p biblioteca_db < database/schema.sql
```

3. **Execute o script de dados iniciais (opcional):**
```bash
mysql -u root -p biblioteca_db < database/data.sql
```

### Configuração da Aplicação

1. **Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/sistema-biblioteca.git
cd sistema-biblioteca
```

2. **Configure a conexão com o banco de dados:**
   - Edite o arquivo `src/config/DatabaseConfig.java`
   - Atualize: URL, usuário e senha do banco de dados

3. **Adicione as bibliotecas JDBC:**
   - Baixe o driver MySQL Connector/J
   - Adicione o arquivo `.jar` na pasta `lib/`
   - Configure o classpath no projeto

### Executando a Aplicação

**Opção 1 - Via IDE:**
- Abra o projeto na IDE
- Execute a classe principal `Main.java`

**Opção 2 - Via linha de comando:**
```bash
javac -d bin -cp "lib/*" src/**/*.java
java -cp "bin:lib/*" Main
```

---

## 🔄 Versionamento e Contribuição

### Workflow Git

Este projeto utiliza Git e GitHub para controle de versão. Siga o fluxo de trabalho abaixo:

#### 1. Clone do Repositório
```bash
git clone https://github.com/seu-usuario/sistema-biblioteca.git
cd sistema-biblioteca
```

#### 2. Criando uma Nova Feature
```bash
# Crie uma branch para sua funcionalidade
git checkout -b feature/nome-da-funcionalidade

# Faça suas alterações e commits
git add .
git commit -m "feat: adiciona funcionalidade X"
```

#### 3. Atualizando seu Branch
```bash
# Busque as atualizações do repositório remoto
git fetch origin

# Atualize sua branch local com as mudanças
git pull origin main
```

#### 4. Enviando Alterações
```bash
# Envie suas alterações para o repositório remoto
git push origin feature/nome-da-funcionalidade
```

#### 5. Mesclando com a Branch Principal
```bash
# Volte para a branch main
git checkout main

# Mescle sua feature
git merge feature/nome-da-funcionalidade

# Envie para o repositório remoto
git push origin main
```

### Padrão de Commits

Utilize commits semânticos para melhor organização:

- `feat:` - Nova funcionalidade
- `fix:` - Correção de bug
- `docs:` - Alterações na documentação
- `style:` - Formatação, espaços em branco, etc
- `refactor:` - Refatoração de código
- `test:` - Adição ou correção de testes
- `chore:` - Atualizações de build, configs, etc

**Exemplos:**
```bash
git commit -m "feat: adiciona tela de cadastro de livros"
git commit -m "fix: corrige cálculo de multa por atraso"
git commit -m "docs: atualiza README com instruções de instalação"
```

---

## 📚 Documentação Adicional

- [Manual do Usuário](docs/manual/manual-usuario.pdf)
- [Diagrama de Classes UML](docs/diagramas/diagrama-classes.png)
- [Modelo Entidade-Relacionamento](docs/diagramas/mer-banco-dados.png)
- [Especificação de Requisitos](docs/especificacao-requisitos.pdf)

---

## 🐛 Problemas Conhecidos

- [ ] Lentidão na busca com grande volume de dados
- [ ] Interface não responsiva em telas pequenas
- [ ] Falta validação em alguns campos de formulário

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.



---

## 🙏 Agradecimentos

- Professores e orientadores do curso
- Colegas de equipe
- Comunidade de desenvolvedores Java

---

**Desenvolvido com ❤️ como parte do Projeto Integrador**

*Última atualização: Janeiro de 2026*
